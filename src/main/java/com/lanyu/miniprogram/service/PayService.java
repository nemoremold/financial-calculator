package com.lanyu.miniprogram.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lanyu.miniprogram.bean.PayInfo;
import com.lanyu.miniprogram.utils.CommonUtil;
import com.lanyu.miniprogram.utils.RandomUtils;
import com.lanyu.miniprogram.utils.TimeUtils;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author i343746
 */
public class PayService {
    private final Logger logger = LoggerFactory.getLogger(PayService.class);

    @Value("weixin.mchId")
    private String mchId;
    @Value("weixin.appId")
    private String appId;
    @Value("weixin.notifyUrl")
    private String notifyUrl;
    // todo 商户号的账号密码
    @Value("weixin.appKey")
    private String appKey;

    @Autowired
    private UserLoginService userLoginService;

    OkHttpClient client = new OkHttpClient();

    private final static String URL_UNIFIED_ORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    //
    private final static String TIME_FORMAT = "yyyyMMddHHmmss";
    // 单位：天
    private final static int TIME_EXPIRE = 2;

    public String prePay(String code, HttpServletRequest request ) throws IOException {
        String content = null;
        Map map = new HashMap();
        ObjectMapper mapper = new ObjectMapper();

        boolean result = true;
        String info = "";

        logger.info("code: {}", code);

        String openId = userLoginService.getOpenId(code);

        if(openId == null || openId.length() == 0) {
            result = false;
            info = "获取到openId为空";
        } else {
            openId = openId.replace("\"", "").trim();

            String clientIP = CommonUtil.getClientIp(request);

            logger.info("openId: {}, clientIP: {}", openId, clientIP);

            String randomNonceStr = RandomUtils.generateMixString(32);
            String prepayId = unifiedOrder(openId, clientIP, randomNonceStr);

            logger.info("prepayId: {}", prepayId);

            if(StringUtils.isBlank(prepayId)) {
                result = false;
                info = "出错了，未获取到prepayId";
            } else {
                map.put("prepayId", prepayId);
                map.put("nonceStr", randomNonceStr);
            }
        }

        try {
            map.put("result", result);
            map.put("info", info);
            content = mapper.writeValueAsString(map);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return content;
    }

    /**
     * 调用统一下单接口
     * @param openId
     */
    private String unifiedOrder(String openId, String clientIP, String randomNonceStr) {

        try {

            String url = URL_UNIFIED_ORDER;

            PayInfo payInfo = createPayInfo(openId, clientIP, randomNonceStr);
            String md5 = getSign(payInfo);
            payInfo.setSign(md5);

            logger.info("md5 value: " + md5);

            String xml = CommonUtil.payInfoToXML(payInfo);
            xml = xml.replace("__", "_").replace("<![CDATA[1]]>", "1");

            logger.info(xml);

            HttpUrl.Builder httpBuider = HttpUrl.parse(URL_UNIFIED_ORDER).newBuilder();
            MediaType TEXT = MediaType.parse("text/plain; charset=utf-8");
            RequestBody body = RequestBody.create(TEXT, xml);

            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();
            String bodyRaw = response.body().string();


            Map<String, String> result = CommonUtil.parseXml(bodyRaw);

            logger.info("unifiedOrder request return body: \n{}", result);

            String return_code = result.get("return_code");
            if(StringUtils.isNotBlank(return_code) && return_code.equals("SUCCESS")) {

                String return_msg = result.get("return_msg");
                if(StringUtils.isNotBlank(return_msg) && !return_msg.equals("OK")) {
                    logger.error("统一下单，取得返回值失败.");
                    return "";
                }

                String prepay_Id = result.get("prepay_id");
                return prepay_Id;

            } else {
                return "";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    private PayInfo createPayInfo(String openId, String clientIP, String randomNonceStr) {

        Date date = new Date();
        String timeStart = TimeUtils.getFormatTime(date, TIME_FORMAT);
        String timeExpire = TimeUtils.getFormatTime(TimeUtils.addDay(date, TIME_EXPIRE), TIME_FORMAT);

        String randomOrderId = CommonUtil.getRandomOrderId();

        PayInfo payInfo = new PayInfo();
        payInfo.setAppid(appId);
        payInfo.setMch_id(mchId);
        payInfo.setDevice_info("WEB");
        payInfo.setNonce_str(randomNonceStr);
        payInfo.setSign_type("MD5");  //默认即为MD5
        payInfo.setBody("可学养老金专业版生成报告");
        payInfo.setAttach("可学养老金计算器");
        payInfo.setOut_trade_no(randomOrderId);
        payInfo.setTotal_fee(1); // todo: 单位：分
        payInfo.setSpbill_create_ip(clientIP);
        payInfo.setTime_start(timeStart);
        payInfo.setTime_expire(timeExpire);
        payInfo.setNotify_url(notifyUrl);// todo
        payInfo.setTrade_type("JSAPI");
        payInfo.setLimit_pay("no_credit");
        payInfo.setOpenid(openId);

        return payInfo;
    }

    private String getSign(PayInfo payInfo) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append("appid=" + payInfo.getAppid())
                .append("&attach=" + payInfo.getAttach())
                .append("&body=" + payInfo.getBody())
                .append("&device_info=" + payInfo.getDevice_info())
                .append("&limit_pay=" + payInfo.getLimit_pay())
                .append("&mch_id=" + payInfo.getMch_id())
                .append("&nonce_str=" + payInfo.getNonce_str())
                .append("&notify_url=" + payInfo.getNotify_url())
                .append("&openid=" + payInfo.getOpenid())
                .append("&out_trade_no=" + payInfo.getOut_trade_no())
                .append("&sign_type=" + payInfo.getSign_type())
                .append("&spbill_create_ip=" + payInfo.getSpbill_create_ip())
                .append("&time_expire=" + payInfo.getTime_expire())
                .append("&time_start=" + payInfo.getTime_start())
                .append("&total_fee=" + payInfo.getTotal_fee())
                .append("&trade_type=" + payInfo.getTrade_type())
                .append("&key=" + appKey);

        logger.info("params will be sent to Unified Order: \n {}", sb.toString());

        return CommonUtil.getMD5(sb.toString().trim()).toUpperCase();
    }

}

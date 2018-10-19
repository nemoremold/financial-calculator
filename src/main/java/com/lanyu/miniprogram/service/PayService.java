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
import org.springframework.stereotype.Service;

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
@Service
public class PayService {
    private final Logger logger = LoggerFactory.getLogger(PayService.class);

    @Value("${weixin.mchId}")
    private String mchId;
    @Value("${weixin.appId}")
    private String appId;
    @Value("${weixin.notifyUrl}")
    private String notifyUrl;
    @Value("${weixin.appKey}")
    private String appKey;
    @Value("${weixin.fee}")
    private String fee;

    private final int FEE = 1;

    @Autowired
    private UserLoginService userLoginService;

    OkHttpClient client = new OkHttpClient();

    private final static String URL_UNIFIED_ORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    //
    private final static String TIME_FORMAT = "yyyyMMddHHmmss";
    // 单位：天
    private final static int TIME_EXPIRE = 2;

    public class PrepayResult{
        private String status;
        private String prepayId;
        private String sign;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPrepayId() {
            return prepayId;
        }

        public void setPrepayId(String prepayId) {
            this.prepayId = prepayId;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }
    }

    /**
     * todo 是否需要传回sign
     * need return sign
     * @param openId
     * @param request
     * @return
     * @throws IOException
     */
    public PrepayResult prePay(String openId, HttpServletRequest request ) throws Exception {
        PrepayResult prepayResult = new PrepayResult();
        prepayResult.setPrepayId("");
        prepayResult.setSign("");
        prepayResult.setStatus("FAILED");

        logger.info("openId: {}", openId);

        if(openId == null || openId.length() == 0) {

            logger.error("openid: openId is empty");
            return prepayResult;
        }

        String clientIP = CommonUtil.getClientIp(request);
        logger.info("openId: {}, clientIP: {}", openId, clientIP);
        String randomNonceStr = RandomUtils.generateMixString(32);

        Map<String, String> unifiedOrderResult = unifiedOrder(openId, clientIP, randomNonceStr);

        String return_code = unifiedOrderResult.get("return_code");

        if(StringUtils.isNotBlank(return_code) && return_code.equals("SUCCESS")) {

            String return_msg = unifiedOrderResult.get("return_msg");
            if(StringUtils.isNotBlank(return_msg) && !return_msg.equals("OK")) {
                logger.error("cannot order: the result is empty");
                return prepayResult;
            }

            String prepayId = unifiedOrderResult.get("prepay_id");
            logger.info("prepayId: {}", prepayId);
            String sign = unifiedOrderResult.get("sign");
            logger.info("sign: {}", sign);

            if(StringUtils.isBlank(prepayId)) {
                logger.error("prepayId is empty");
                return prepayResult;
            }

            prepayResult.setPrepayId(prepayId);
            prepayResult.setSign(sign);
            prepayResult.setStatus("SUCCESS");

            ObjectMapper objectMapper = new ObjectMapper();
            logger.info("prepay result: {}", objectMapper.writeValueAsString(prepayResult));

            return prepayResult;

        } else {
            return prepayResult;
        }

    }

    /**
     * 调用统一下单接口
     * @param openId
     */
    private Map unifiedOrder(String openId, String clientIP, String randomNonceStr) throws Exception {

        String url = URL_UNIFIED_ORDER;

        PayInfo payInfo = createPayInfo(openId, clientIP, randomNonceStr);
        String md5 = getSign(payInfo);
        payInfo.setSign(md5);

        logger.info("md5 value: " + md5);

        String xml = CommonUtil.payInfoToXML(payInfo);
        xml = xml.replace("__", "_");

        logger.info("request body: {}", xml);

        MediaType TEXT = MediaType.parse("text/plain; charset=utf-8");
        RequestBody body = RequestBody.create(TEXT, xml);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String bodyRaw = response.body().string();

        Map<String, String> result = CommonUtil.parseXml(bodyRaw);

        result.put("sign", md5);

        ObjectMapper objectMapper = new ObjectMapper();

        logger.info("unifiedOrder request return body: \n{}", objectMapper.writeValueAsString(result));

        return result;
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
        payInfo.setTotal_fee(fee);
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

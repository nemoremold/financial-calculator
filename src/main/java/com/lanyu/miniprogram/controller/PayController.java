package com.lanyu.miniprogram.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.lanyu.miniprogram.bean.CallbackData;
import com.lanyu.miniprogram.dto.SingleResultResponse;
import com.lanyu.miniprogram.repository.CallbackDataRepository;
import com.lanyu.miniprogram.service.PayService;
import com.lanyu.miniprogram.utils.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author i343746
 */
@RestController
@RequestMapping("/pay")
public class PayController {
    @Autowired
    PayService payService;
    @Autowired
    CallbackDataRepository callbackDataRepository;

    private final Logger logger = LoggerFactory.getLogger(PayController.class);
    @Value("${redis.host}")
    private String redisHost;

    @Value("${redis.port}")
    private int redisPort;

    @ResponseBody
    @RequestMapping(path = "/prepay", method = RequestMethod.GET)
    public PayService.PrepayResult getPrepayId(
            @RequestParam(required = true) String wechatId, HttpServletRequest request
    ) throws Exception {
        return payService.prePay(wechatId, request);
    }

    @ResponseBody
    @RequestMapping(path = "/callback", method = RequestMethod.POST)
    public String getCallback(
            @RequestBody String body
    ) throws Exception {
        Pattern p = Pattern.compile("\\s*|\t|\r|\n");
        Matcher m = p.matcher(body);
        String str = m.replaceAll("");
        logger.info("the weixinApi callback is: {}", str);
        Jedis jedis = new Jedis(redisHost, redisPort);
        jedis.set("callback", str);

        Map<String, String> map = CommonUtil.parseXml(str);
        Gson gson = new Gson();
        String json = gson.toJson(map);
        logger.info("parse json: {}", json);
        CallbackData data = gson.fromJson(json, CallbackData.class);
        logger.info("CallbackData : {}", new ObjectMapper().writeValueAsString(data));

        if(data != null)
            callbackDataRepository.save(data);

        return "<xml>\n" +
                "  <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                "  <return_msg><![CDATA[OK]]></return_msg>\n" +
                "</xml>";
    }

    @ResponseBody
    @RequestMapping(path = "/getCallbackData", method = RequestMethod.GET)
    public SingleResultResponse getCallbackData() throws Exception {
        Jedis jedis = new Jedis(redisHost, redisPort);
        String data = jedis.get("callback");
        logger.info("getCallbackData: {}", data);

        return new SingleResultResponse(data);
    }
}

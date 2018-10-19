package com.lanyu.miniprogram.controller;

import com.lanyu.miniprogram.dto.SingleResultResponse;
import com.lanyu.miniprogram.service.PayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;

/**
 * @author i343746
 */
@RestController
@RequestMapping("/pay")
public class PayController {
    @Autowired
    PayService payService = new PayService();

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
        logger.info("the weixinApi callback is: {}", body);
        Jedis jedis = new Jedis(redisHost, redisPort);
        jedis.set("callback", body);

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

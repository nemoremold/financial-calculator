package com.lanyu.miniprogram.controller;

import com.lanyu.miniprogram.dto.SingleResultResponse;
import com.lanyu.miniprogram.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author i343746
 */
@RestController
@RequestMapping("/pay")
public class PayController {
    @Autowired
    PayService payService = new PayService();

    @ResponseBody
    @RequestMapping(path = "/prepay", method = RequestMethod.GET)
    public SingleResultResponse getPrepayId(
            @RequestParam(required = true) String wechatId, HttpServletRequest request
    ) throws Exception {
        return new SingleResultResponse(payService.prePay(wechatId, request));
    }
}

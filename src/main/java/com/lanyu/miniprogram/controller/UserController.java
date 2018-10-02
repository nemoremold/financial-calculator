package com.lanyu.miniprogram.controller;

import com.lanyu.miniprogram.bean.User;
import com.lanyu.miniprogram.dto.ResultWithDataResponse;
import com.lanyu.miniprogram.dto.SingleResultResponse;
import com.lanyu.miniprogram.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author i343746
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @ResponseBody
    @RequestMapping(path = "/checkRegister", method = RequestMethod.GET)
    public SingleResultResponse checkRegister(@RequestParam(required = true) String wechatId) {
        User user = userRepository.findByWechatId(wechatId);

        return new SingleResultResponse(user == null ? false: true);
    }

    @ResponseBody
    @RequestMapping(path = "/updateUserInfo", method = RequestMethod.POST)
    public SingleResultResponse register(@RequestBody User u){
        ResultWithDataResponse response = new ResultWithDataResponse();
        User user = userRepository.findByWechatId(u.getWechatId());
        response.setResult(user == null? "register": "update");
        User result = userRepository.save(u);
        response.setData(result);

        return new SingleResultResponse(response);
    }

    @ResponseBody
    @RequestMapping(path = "/getUserInfo", method = RequestMethod.GET)
    public SingleResultResponse register(@RequestParam(required = true) String wechatId){
        User user = userRepository.findByWechatId(wechatId);

        return new SingleResultResponse(user);
    }
}

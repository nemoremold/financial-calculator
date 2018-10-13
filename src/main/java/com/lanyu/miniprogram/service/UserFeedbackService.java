package com.lanyu.miniprogram.service;

import com.lanyu.miniprogram.bean.UserFeedback;
import com.lanyu.miniprogram.repository.UserFeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * @Author yansong
 * @Date 2018/10/13 11:32
 */
@Service
public class UserFeedbackService {
    @Autowired
    private UserFeedbackRepository userFeedbackRepository;

    public UserFeedback feedback(String wechatId, String feedback) {
        String ts = new Timestamp(System.currentTimeMillis()).toString();
        UserFeedback userFeedback = userFeedbackRepository.getUserFeedbackByWechatIdAndTimestamp(wechatId, ts);
        if (userFeedback == null) {
            userFeedback = new UserFeedback();
            userFeedback.setWechatId(wechatId);
            userFeedback.setTimestamp(ts);
            userFeedback.setFeedback(feedback);
            userFeedbackRepository.save(userFeedback);
        }
        return userFeedback;
    }
}

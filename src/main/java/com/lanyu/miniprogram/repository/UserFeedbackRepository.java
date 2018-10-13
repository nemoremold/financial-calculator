package com.lanyu.miniprogram.repository;

import com.lanyu.miniprogram.bean.UserFeedback;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author yansong
 * @Date 2018/10/13 11:28
 */
public interface UserFeedbackRepository extends JpaRepository<UserFeedback, String> {
    UserFeedback getUserFeedbackByWechatIdAndTimestamp(String wechatId, String timestamp);
}

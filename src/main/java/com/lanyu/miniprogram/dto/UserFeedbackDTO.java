package com.lanyu.miniprogram.dto;

/**
 * @Author yansong
 * @Date 2018/10/13 11:16
 */
public class UserFeedbackDTO {
    private String wechatId;

    private String feedback;

    public String getWechatId() {
        return wechatId;
    }

    public void setWechatId(String wechatId) {
        this.wechatId = wechatId;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}

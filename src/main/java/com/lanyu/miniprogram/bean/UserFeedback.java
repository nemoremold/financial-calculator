package com.lanyu.miniprogram.bean;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author yansong
 * @Date 2018/10/13 11:17
 */
@Entity
@Table(name="feedback")
@IdClass(IdTimeKeys.class)
public class UserFeedback implements Serializable {
    @Id
    @NotNull
    @Column(name = "user_id")
    private String wechatId;

    @Id
    @NotNull
    @Column(name = "timestamp")
    private String timestamp;

    @NotNull
    @Column(name = "feedback")
    private String feedback;

    public String getWechatId() {
        return wechatId;
    }

    public void setWechatId(String wechatId) {
        this.wechatId = wechatId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}

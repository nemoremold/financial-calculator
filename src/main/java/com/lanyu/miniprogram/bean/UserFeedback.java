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
public class UserFeedback implements Serializable {
    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "id")
    private int id;

    @NotNull
    @Column(name = "user_id")
    private String wechatId;

    @NotNull
    @Column(name = "timestamp")
    private String timestamp;

    @NotNull
    @Column(name = "feedback")
    private String feedback;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

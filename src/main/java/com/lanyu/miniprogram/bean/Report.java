package com.lanyu.miniprogram.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @Author yansong
 * @Date 2018/10/13 12:29
 */
@Entity
@Table(name="report")
public class Report {
    @Id
    @NotNull
    @Column(name = "user_id")
    private String wechatId;

    @Id
    @NotNull
    @Column(name = "timestamp")
    private String timestamp;

    @Column(name = "picture")
    private byte[] picture;

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

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }
}

package com.lanyu.miniprogram.bean;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author yansong
 * @Date 2018/10/13 12:29
 */
@Entity
@Table(name="report")
public class Report implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    @Column(name = "id")
    private int id;

    @NotNull
    @Column(name = "user_id")
    private String wechatId;

    @NotNull
    @Column(name = "timestamp")
    private String timestamp;

    @Column(name = "picture")
    private byte[] picture;

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

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }
}

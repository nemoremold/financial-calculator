package com.lanyu.miniprogram.bean;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author yansong
 * @Date 2018/10/13 12:20
 */
@Entity
@Table(name="data_item")
@IdClass(IdTimeKeys.class)
public class ReportData implements Serializable {
    @Id
    @NotNull
    @Column(name = "user_id")
    private String wechatId;

    @Id
    @NotNull
    @Column(name = "timestamp")
    private String timestamp;

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
}

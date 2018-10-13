package com.lanyu.miniprogram.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
/**
 * @Author yansong
 * @Date 2018/10/13 12:20
 */
@Entity
@Table(name="data_item")
public class ReportData {
    @Id
    @NotNull
    @Column(name = "user_id")
    private String wechatId;

    @Id
    @NotNull
    @Column(name = "timestamp")
    private String timestamp;
}

package com.lanyu.miniprogram.bean;

import javax.persistence.*;

/**
 * @author i343746
 */
@Entity
@Table(name="user")
public class User {
    @Id
    @Column(name = "user_id")
    private String wechatId;

    // 姓名 用户姓名
    @Column(name = "user_name")
    private String name;

    // 手机号
    @Column(name = "phone")
    private String phone;

    // 服务区域, 用户所在的市级单位，根据GPS定位选择默认值，用户可更改
    @Column(name = "serve_region")
    private String serveRegion;

    // 所在机构, 用户所在的单位、企业 如：XX人寿、XX银行
    @Column(name = "enterprise")
    private String enterprise;

    // 分支机构 用户所在的单位、企业的分支部门 如：XX分公司、XX分行
    @Column(name = "enterprise_branch")
    private String enterpriseBranch;

    // 职位名称 | 用户的职位、职称等 如：客户经理、理财经理
    @Column(name = "title")
    private String title;

    public String getWechatId() {
        return wechatId;
    }

    public void setWechatId(String wechatId) {
        this.wechatId = wechatId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getServeRegion() {
        return serveRegion;
    }

    public void setServeRegion(String serveRegion) {
        this.serveRegion = serveRegion;
    }

    public String getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(String enterprise) {
        this.enterprise = enterprise;
    }

    public String getEnterpriseBranch() {
        return enterpriseBranch;
    }

    public void setEnterpriseBranch(String enterpriseBranch) {
        this.enterpriseBranch = enterpriseBranch;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

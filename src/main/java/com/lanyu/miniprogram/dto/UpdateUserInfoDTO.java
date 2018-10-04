package com.lanyu.miniprogram.dto;

import com.lanyu.miniprogram.bean.User;

/**
 * @author i343746
 */
public class UpdateUserInfoDTO {
    private User user;

    // 验证码
    private String code;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}

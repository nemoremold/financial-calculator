package com.lanyu.miniprogram.bean;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author i343746
 */
public class IdTimeKeys implements Serializable {
    private String wechatId;
    private String timestamp;

    public IdTimeKeys(String wechatId, String timestamp) {
        this.wechatId = wechatId;
        this.timestamp = timestamp;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IdTimeKeys that = (IdTimeKeys) o;
        return Objects.equals(wechatId, that.wechatId) &&
                Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {

        return Objects.hash(wechatId, timestamp);
    }
}

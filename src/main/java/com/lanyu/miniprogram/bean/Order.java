package com.lanyu.miniprogram.bean;

import com.google.gson.annotations.SerializedName;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author i343746
 */
@Entity
@Table(name="order")
public class Order {
    @Column(name = "order_id")
    private String orderId;

    @Column(name = "has_paid")
    private boolean hasPaid;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public boolean isHasPaid() {
        return hasPaid;
    }

    public void setHasPaid(boolean hasPaid) {
        this.hasPaid = hasPaid;
    }
}

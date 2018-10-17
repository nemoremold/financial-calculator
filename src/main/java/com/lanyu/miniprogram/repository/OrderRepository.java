package com.lanyu.miniprogram.repository;

import com.lanyu.miniprogram.bean.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, String> {
    Order findByOrderId(String orderId);
}

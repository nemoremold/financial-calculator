package com.lanyu.miniprogram.repository;

import com.lanyu.miniprogram.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author i343746
 */
public interface UserRepository extends JpaRepository<User, String> {
    User findByWechatId(String id);
}

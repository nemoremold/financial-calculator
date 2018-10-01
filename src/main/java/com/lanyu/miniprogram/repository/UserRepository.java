package com.lanyu.miniprogram.repository;

import com.lanyu.miniprogram.bean.User;
import org.springframework.stereotype.Repository;

/**
 * @author i343746
 */
@Repository
public class UserRepository extends JpaRepository<User, Long> {
}

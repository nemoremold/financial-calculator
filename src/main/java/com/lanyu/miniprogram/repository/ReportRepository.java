package com.lanyu.miniprogram.repository;

import com.lanyu.miniprogram.bean.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author yansong
 * @Date 2018/10/13 12:32
 */
public interface ReportRepository extends JpaRepository<Report, String> {
    Report getReportByWechatIdAndTimestamp(String wechatId, String timestamp);

    int countAllByWechatId(String wechatId);

    void deleteReportByWechatIdAndTimestamp(String wechatId, String timestamp);
}

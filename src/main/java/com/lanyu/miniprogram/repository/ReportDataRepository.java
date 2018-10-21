package com.lanyu.miniprogram.repository;

import com.lanyu.miniprogram.bean.ReportData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author yansong
 * @Date 2018/10/13 12:20
 */
public interface ReportDataRepository extends JpaRepository<ReportData, String> {
    ReportData getReportDataByWechatIdAndTimestamp(String wechatId, String timestamp);

    List<ReportData> getAllByWechatIdOrderByTimestamp(String wechatId);

    int countByWechatId(String wechatId);

    void deleteReportDataByWechatIdAndTimestamp(String wechatId, String timestamp);
}

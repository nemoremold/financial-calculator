package com.lanyu.miniprogram.service;

import com.lanyu.miniprogram.repository.ReportDataRepository;
import com.lanyu.miniprogram.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author yansong
 * @Date 2018/10/13 12:34
 */
@Service
public class ReportService {
    @Autowired
    private ReportDataRepository reportDataRepository;

    @Autowired
    private ReportRepository reportRepository;
}

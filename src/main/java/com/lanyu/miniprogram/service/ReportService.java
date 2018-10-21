package com.lanyu.miniprogram.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lanyu.miniprogram.bean.Report;
import com.lanyu.miniprogram.dto.TemplateDataDTO;
import com.lanyu.miniprogram.repository.ReportDataRepository;
import com.lanyu.miniprogram.repository.ReportRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.nio.file.Files;
import java.util.Base64;
import java.util.Date;

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

    enum Info{
        SUCCESS("success"),
        FAILED_WHEN_RENDER("failed when rendering"),
        FAILED_WHEN_EXECUTING("failed when execute shell"),
        FAILED_WITH_MISS_PIC_FILE("failed with missing pic file"),
        FAILED_WHEN_STORED_IN_DB("failed when writing in DB");


        public String msg;

        Info(String msg) {
            this.msg = msg;
        }

        @Override
        public String toString() {
            return msg;
        }
    }

    Logger logger = LoggerFactory.getLogger(ReportService.class);

    public Report storeReport(String base64String, String wechatId, String timestamp){
        Report report = null;
        report = reportRepository.getReportByWechatIdAndTimestamp(wechatId, timestamp);
        if (report == null) {
            report = new Report();
            report.setTimestamp(timestamp);
            report.setWechatId(wechatId);
        }
        report.setPicture(base64String.getBytes());

        return reportRepository.save(report);
    }

    public String authShell(String cmd){
        Process process = null;
        String total = "";
        try {
            logger.info("Begin to auth");
            process = Runtime.getRuntime().exec(cmd);
            process.waitFor();
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";
            while ((line = input.readLine()) != null) {
                total = total + line + "\n";
            }
            input.close();
            logger.info("End to auth");
        } catch (Exception e){
            logger.error(Info.FAILED_WHEN_EXECUTING.toString()+": \nexception: {}", e);
            return Info.FAILED_WHEN_EXECUTING.toString();
        }
        logger.info("output: {}", total);

        return total;
    }

    @Transactional
    public String deleteReportData(String wechatId, String timestamp) {
        if (reportDataRepository.getReportDataByWechatIdAndTimestamp(wechatId, timestamp) == null) {
            return "not exist";
        }
        if (reportRepository.getReportByWechatIdAndTimestamp(wechatId, timestamp) != null) {
            return "report exists, deletion is not allowed";
        }
        reportDataRepository.deleteReportDataByWechatIdAndTimestamp(wechatId, timestamp);
        if (reportDataRepository.getReportDataByWechatIdAndTimestamp(wechatId, timestamp) == null) {
            return "deleted";
        }
        return "deletion failed";
    }

    @Transactional
    public String deleteReport(String wechatId, String timestamp) {
        if (reportRepository.getReportByWechatIdAndTimestamp(wechatId, timestamp) == null) {
            return "not exist";
        }
        reportRepository.deleteReportByWechatIdAndTimestamp(wechatId, timestamp);
        if (reportDataRepository.getReportDataByWechatIdAndTimestamp(wechatId, timestamp) != null) {
            reportDataRepository.deleteReportDataByWechatIdAndTimestamp(wechatId, timestamp);
        }
        if (reportRepository.getReportByWechatIdAndTimestamp(wechatId, timestamp) != null && reportRepository.getReportByWechatIdAndTimestamp(wechatId, timestamp) != null) {
            return "report and report data deletion failed";
        }
        if (reportRepository.getReportByWechatIdAndTimestamp(wechatId, timestamp) != null) {
            return "report deletion failed";
        }
        if (reportDataRepository.getReportDataByWechatIdAndTimestamp(wechatId, timestamp) != null) {
            return "report data deletion failed";
        }
        return "deleted";
    }

    /**
     * 执行脚本，获得图片的base64转码
     * @param data render的数据
     * @return 成功信息
     * @throws IOException
     */
    public String getReport(TemplateDataDTO data) throws IOException {
        Process process = null;
        String total = "";
        String timestamp = data.getTimestamp();
        String filename = data.getWechatId() + timestamp;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            logger.info("Begin to render pic, id is {}, Time is {}", data.getWechatId(), timestamp);
            String dataJson = objectMapper.writeValueAsString(data);
            String base64JsonData = Base64.getEncoder().encodeToString(dataJson.getBytes("UTF-8"));
            process = Runtime.getRuntime().exec("./template/generate.sh " + base64JsonData + " " + filename);
            process.waitFor();
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";
            while ((line = input.readLine()) != null) {
                total = total + line + "\n";
            }
            input.close();
            logger.info("End to render pic, id is {}, Time is {}", data.getWechatId(), timestamp);
        } catch (Exception e){
            logger.error(Info.FAILED_WHEN_EXECUTING.toString()+": {}", e);
            return Info.FAILED_WHEN_EXECUTING.toString();
        }
        logger.info("output: {}", total);

        if(total.contains("Error")) {
            logger.error("When rendering pic, something wrong: {}", total);
            return Info.FAILED_WHEN_RENDER.toString();
        }

        File file = new File("./template/" + filename + ".jpg");
        byte[] fileContent = null;
        if(file.isFile() && file.exists()){
            fileContent = Files.readAllBytes(file.toPath());
        }

        if(fileContent == null){
            logger.error("When convert file to string, bytes array is null");
            return Info.FAILED_WITH_MISS_PIC_FILE.toString();
        }

        String encoded = Base64.getEncoder().encodeToString(fileContent);

        if(file.delete()) {
            logger.info( "{} is deleted", file.getName());
        }else {
            logger.error("{}: Delete operation is failed", file.getName());
        }

        return this.storeReport(encoded, data.getWechatId(), data.getTimestamp()) == null?
                Info.FAILED_WHEN_STORED_IN_DB.toString():
                Info.SUCCESS.toString();
    }
}

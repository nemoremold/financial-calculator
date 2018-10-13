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
        Report report = new Report();
        report.setPicture(base64String.getBytes());
        report.setTimestamp(timestamp);
        report.setWechatId(wechatId);

        return reportRepository.save(report);
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
            process = Runtime.getRuntime().exec("./template/generate.sh " + dataJson + " " + filename);
            process.waitFor();
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";
            while ((line = input.readLine()) != null) {
                total = total + line + "\n";
            }
            input.close();
            logger.info("End to render pic, id is {}, Time is {}", data.getWechatId(), timestamp);
        } catch (Exception e){
            return Info.FAILED_WHEN_EXECUTING.toString();
        }

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

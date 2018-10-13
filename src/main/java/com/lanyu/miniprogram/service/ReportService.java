package com.lanyu.miniprogram.service;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    Logger logger = LoggerFactory.getLogger(ReportService.class);

    /**
     * 执行脚本，获得图片的base64转码
     * @param data render的数据
     * @return base64字符串
     * @throws IOException
     */
    public String getReport(TemplateDataDTO data) throws IOException {
        Process process = null;
        String total = "";
        String timestamp = Long.toString(new Date().getTime());
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
        }
        catch (Exception e){}

        if(total.contains("Error"))
            logger.error("When rendering pic, something wrong: {}", total);

        File file = new File("./template/" + filename + ".jpg");
        byte[] fileContent = null;
        if(file.isFile() && file.exists()){
            fileContent = Files.readAllBytes(file.toPath());
        }

        if(fileContent == null){
            logger.error("When convert file to string, bytes array is null");
            return null;
        }

        String encoded = Base64.getEncoder().encodeToString(fileContent);

        if(file.delete()) {
            logger.info( "{} is deleted", file.getName());
        }else {
            logger.error("{}: Delete operation is failed", file.getName());
        }

        return encoded;
    }
}

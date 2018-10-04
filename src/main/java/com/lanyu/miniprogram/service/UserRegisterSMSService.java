package com.lanyu.miniprogram.service;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import com.lanyu.miniprogram.bean.User;
import com.lanyu.miniprogram.repository.UserRepository;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.Random;

/**
 * @author i343746
 */
@Service
public class UserRegisterSMSService {
    enum ServiceInfo{
        SUCCESS("Send successfully"),
        REDIS_ERROR("Redis error"),
        DATABASE_ERROR("Database error"),
        SMS_SENDER_ERROR("Tcloud SMS service error"),
        ERROR("Some errors happened"),
        PHONE_HAS_BEEN_USED("This phone number has been used");

        private String msg;

        ServiceInfo(String msg) {
            this.msg = msg;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

    enum ServiceStatus{
        SUCCESS("success"),
        ERROR("error"),
        PHONE_HAS_BEEN_USED("duplicate");

        private String msg;
        ServiceStatus(String msg) {
            this.msg = msg;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

    class RedisSetResponse{
        String status;
        String phone;
        String code;

        public RedisSetResponse(String status, String phone, String code) {
            this.status = status;
            this.phone = phone;
            this.code = code;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        @Override
        public String toString() {
            return "RedisSetResponse{" +
                    "status='" + status + '\'' +
                    ", phone='" + phone + '\'' +
                    ", code='" + code + '\'' +
                    '}';
        }
    }

    public class ServiceResponse{
        String status;
        String msg;
        Object result;

        public ServiceResponse(String status, String msg, Object result) {
            this.status = status;
            this.msg = msg;
            this.result = result;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public Object getResult() {
            return result;
        }

        public void setResult(Object result) {
            this.result = result;
        }

        @Override
        public String toString() {
            return "ServiceResponse{" +
                    "status='" + status + '\'' +
                    ", msg='" + msg + '\'' +
                    ", result=" + result +
                    '}';
        }
    }

    Logger logger = LoggerFactory.getLogger(UserRegisterSMSService.class);

    // 短信应用SDK AppID
    // 写在application.properties里
    @Value("${tcloud.sms.appid}")
    private int appid;

    // 短信应用SDK AppKey
    // 写在application.properties里
    @Value("${tcloud.sms.appkey}")
    private String appkey;

    // 短信模板ID，需要在短信应用中申请
    @Value("${tcloud.sms.templateid}")
    private int templateId;

    @Value("${tcloud.sms.sign}")
    private String smsSign;

    @Value("${redis.host}")
    private String redisHost;

    @Value("${redis.port}")
    private int redisPort;

    @Autowired
    private UserRepository userRepository;

    // 过期时间 2mins 单位秒
    final static int CODE_INVALID_TIME = 120;

    // 过期时间的显示，单位 分
    final static String CODE_INVALID_TIME_DISPLAY = ""+CODE_INVALID_TIME/(60);

    //templateId7839对应的内容是"您的验证码是: {1}"
    // 签名
     // NOTE: 这里的签名"腾讯云"只是一个示例，真实的签名需要在短信控制台中申请，另外签名参数使用的是`签名内容`，而不是`签名ID`

    public String setRedis(){
        Jedis jedis = new Jedis(redisHost, redisPort);
        return jedis.set("foo", "bar");
    }

    public String getRedis(){
        Jedis jedis = new Jedis(redisHost, redisPort);
        return jedis.get("foo");
    }

    private String generateCode(){
        String code = "";

        Random generator = new Random();
        for(int i =0; i < 4; i++){
            code += generator.nextInt(10);
        }

        return code;
    }

    private RedisSetResponse setRedisForPhone(String phone){
        Jedis jedis = new Jedis(redisHost, redisPort);
        String code = generateCode();
        String msg = jedis.setex(phone, CODE_INVALID_TIME, code);
        jedis.close();
        return new RedisSetResponse(msg, phone, code);
    }

    public boolean hasPhoneStored(String phone){
        User user = userRepository.findByPhone(phone);
        return user == null? false: true;
    }

    public SmsSingleSenderResult sendSMS(String phone, String code){
        SmsSingleSenderResult result = null;
        String[] params = {code, CODE_INVALID_TIME_DISPLAY};
        try {
            SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
            result = ssender.sendWithParam("86", phone, this.templateId, params, "", "", "");
        } catch (HTTPException e) {
            // HTTP响应码错误
            e.printStackTrace();
        } catch (JSONException e) {
            // json解析错误
            e.printStackTrace();
        } catch (IOException e) {
            // 网络IO错误
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 1. 验证手机号是否注册
     * 2. 验证是否redis写入code成功
     * 3. 1,2验证通过发短信
     * @param phone
     */
    public ServiceResponse verifyThePhoneBySMS(String phone) {
        if(hasPhoneStored(phone)){
            // 返回手机号已注册
           return new ServiceResponse(ServiceStatus.PHONE_HAS_BEEN_USED.msg, ServiceInfo.PHONE_HAS_BEEN_USED.msg, "");
        }

        RedisSetResponse redisSetResponse = setRedisForPhone(phone);
        if(!redisSetResponse.status.equals("OK")){
            // 写入失败
            logger.error("Error: `{}`,  Details: `{}`", ServiceInfo.REDIS_ERROR.msg, redisSetResponse);
            return new ServiceResponse(ServiceStatus.ERROR.msg, ServiceInfo.REDIS_ERROR.msg, redisSetResponse.status);
        }

        SmsSingleSenderResult smsResult = sendSMS(redisSetResponse.phone, redisSetResponse.code);
        if(smsResult.result != 0){
            // 发送短信失败
            logger.error("Error: `{}`,  Details: `{}`", ServiceInfo.SMS_SENDER_ERROR.msg, smsResult.getResponse().body);
            return new ServiceResponse(ServiceStatus.ERROR.msg, ServiceInfo.SMS_SENDER_ERROR.msg, smsResult.errMsg);
        }

        // 返回发送算成功信息
        return new ServiceResponse(ServiceStatus.SUCCESS.msg, ServiceInfo.SUCCESS.msg, redisSetResponse.code);
    }

    public boolean verifyCode(String code, String phone){
        Jedis jedis = new Jedis(redisHost, redisPort);
        String redisCode = jedis.get(phone);
        if(code == null || redisCode == null)
            return false;

        return code.equals(redisCode);
    }
}

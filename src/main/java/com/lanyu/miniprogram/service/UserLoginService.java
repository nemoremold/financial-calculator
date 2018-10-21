package com.lanyu.miniprogram.service;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author i343746
 */
@Service
public class UserLoginService {
    @Value("${weixin.appId}") // spring配置文件配置了appID
    private String appId;

    @Value("${weixin.appSecret}") // spring配置文件配置了secret
    private String secret;

    final private static String URL = "https://api.weixin.qq.com/sns/jscode2session";

    OkHttpClient client = new OkHttpClient();

    /**
     * 小程序端获取的CODE
     * @param code 小程序端获取的CODE
     * @return
     */
    public String getOpenId(String code) throws IOException {
        HttpUrl.Builder httpBuider = HttpUrl.parse(URL).newBuilder()
                .addQueryParameter("appid", appId)
                .addQueryParameter("secret", secret)
                .addQueryParameter("js_code", code)
                .addQueryParameter("grant_type", "authorization_code");

        Request request = new Request.Builder()
                .url(httpBuider.build())
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }


}

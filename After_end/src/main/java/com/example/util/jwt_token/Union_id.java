package com.example.util.jwt_token;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class Union_id {
    private static final String appid = "wx6ee0d4d6edcfbc7c";
    private static final String secret = "90fd7a9771d72240b6bb3d72f774248a";

    public static String getUnionId(String js_code){
        // 创建RestTemplate实例
        RestTemplate restTemplate = new RestTemplate();

        // 设置请求参数
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appid +
                "&secret=" + secret +
                "&js_code=" + js_code +
                "&grant_type=authorization_code";

        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        // 创建HttpEntity对象并设置请求头
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // 发送GET请求
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        // 处理响应结果
        return response.getBody();
    }
}

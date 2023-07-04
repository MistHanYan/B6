package com.example.discard;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Data
public class Token {
    private String access_token;
    private String expires_in;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final HttpClient httpClient = HttpClient.newHttpClient();
    public Token getToken() {
        String url = "https://api.weixin.qq.com/cgi-bin/token";
        String grant_type = "client_credential";
        String appid = null;
        String secret = null;
        String reUrl = url + "?" + grant_type + "&" + appid + "&" + secret;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(reUrl))
                .GET()
                .build();

        Token token = new Token();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // 处理响应
            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                System.out.println("请求成功！");
                String responseBody = response.body();

                // 解析 JSON 响应体为 JSON 对象
                JsonNode jsonNode = objectMapper.readTree(responseBody);

                // 或者将 JSON 响应体映射到自定义的 Java 类
                token.setAccess_token(String.valueOf(jsonNode.get("access_token")));
                token.setExpires_in(String.valueOf(jsonNode.get("expires_in")));
                return token;

            } else {
                System.out.println("请求失败！");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getPhoneNum(Token token , String code){
        String url = "https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=ACCESS_TOKEN ";

        HashMap<String,String> params = new HashMap<>();
        params.put("access_token",token.getAccess_token());
        params.put("code",code);
        String requestBody = mapToFormData(params);
        // 发起post请求
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .POST(BodyPublishers.ofString(requestBody))
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // 处理响应
            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                System.out.println("请求成功！");
                String responseBody = response.body();

                // 解析 JSON 响应体为 JSON 对象
                JsonNode jsonNode = objectMapper.readTree(responseBody);

                // 或者将 JSON 响应体映射到自定义String手机号
                return String.valueOf(jsonNode.get("purePhoneNumber"));

            } else {
                System.out.println("请求失败！");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String mapToFormData(Map<String, String> params) {
        StringBuilder formData = new StringBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (formData.length() > 0) {
                formData.append("&");
            }
            formData.append(entry.getKey());
            formData.append("=");
            formData.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
        }

        return formData.toString();
    }
}

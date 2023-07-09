package com.example.util.recognition;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class RecognitionRep {
    private static final String AiUrl = "http://127.0.0.1:8000/recognition";

    public static String discernOutcome(String imgUrl){

        // 创建restTemplate实例
        RestTemplate restTemplate = new RestTemplate();

        // 构造请求参数
        MultiValueMap<String , String > imgData = new LinkedMultiValueMap<>();
        imgData.add("img_url",imgUrl);
        // 传入构造请求参数
        HttpEntity<MultiValueMap<String ,String >> requestEntity = new HttpEntity<>(imgData);

        // 发起post请求
        ResponseEntity<String> responseEntity = restTemplate.exchange(AiUrl, HttpMethod.POST,requestEntity,String.class);

        if(responseEntity.getStatusCode().is2xxSuccessful()){
            return responseEntity.getBody();
        }else {
            return "识别失败";
        }
    }
}

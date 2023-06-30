package com.example.interceptor;

import lombok.Data;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;


@RestController
public class UserController {

    private static final String appId = "YOUR_APPID";
    private static final String appSecret = "YOUR_APP_SECRET";

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        String code = request.getCode();

        // 调用微信登录凭证校验接口获取 session_key
        // 省略请求发送和解析JSON的代码，示例中假设获取到了 sessionKey
        String sessionKey = "YOUR_SESSION_KEY";

        // 将 session_key、encryptedData、iv 返回给前端
        return new LoginResponse(sessionKey, request.getEncryptedData(), request.getIv());
    }

    @PostMapping("/decodePhoneNumber")
    public DecodePhoneNumberResponse decodePhoneNumber(@RequestBody DecodePhoneNumberRequest request) {
        String sessionKey = request.getSessionKey();
        String encryptedData = request.getEncryptedData();
        String iv = request.getIv();

        try {
            byte[] sessionKeyBytes = Base64.getDecoder().decode(sessionKey);
            byte[] encryptedDataBytes = Base64.getDecoder().decode(encryptedData);
            byte[] ivBytes = Base64.getDecoder().decode(iv);

            SecretKeySpec secretKeySpec = new SecretKeySpec(sessionKeyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] decryptedBytes = cipher.doFinal(encryptedDataBytes);

            String decryptedData = new String(decryptedBytes, StandardCharsets.UTF_8);

            // 解析 decryptedData 获取手机号
            String phoneNumber = extractPhoneNumber(decryptedData);

            // 将解密后的手机号返回给前端
            return new DecodePhoneNumberResponse(phoneNumber);
        } catch (Exception e) {
            e.printStackTrace();
            return new DecodePhoneNumberResponse("Decryption failed");
        }
    }

    private String extractPhoneNumber(String decryptedData) {
        // 解析 decryptedData 获取手机号的逻辑
        // 省略具体实现，请根据解密后的数据格式进行相应的解析操作
        return "PHONE_NUMBER";
    }
    @Data
    static class LoginRequest {

        public LoginRequest(String code, String encryptedData, String iv) {
            this.code = code;
            this.encryptedData = encryptedData;
            this.iv = iv;
        }

        private String code;
        private String encryptedData;
        private String iv;

        // Getter和Setter方法省略

        // 注意：根据实际情况添加必要的构造函数
    }

    @Data
    static class LoginResponse {
        public LoginResponse(String sessionKey, String encryptedData, String iv) {
            this.sessionKey = sessionKey;
            this.encryptedData = encryptedData;
            this.iv = iv;
        }

        private String sessionKey;
        private String encryptedData;
        private String iv;

        // Getter和Setter方法省略
    }

    @Data
    static class DecodePhoneNumberRequest {
        public DecodePhoneNumberRequest(String sessionKey, String encryptedData, String iv) {
            this.sessionKey = sessionKey;
            this.encryptedData = encryptedData;
            this.iv = iv;
        }

        private String sessionKey;
        private String encryptedData;
        private String iv;

        // Getter和Setter方法省略
    }

    @Data
    static class DecodePhoneNumberResponse {
        public DecodePhoneNumberResponse(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        private String phoneNumber;

        // Getter和Setter方法省略
    }
}




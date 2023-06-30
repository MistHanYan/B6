package com.example.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Slf4j
@Component
public class LogInCheckTest implements HandlerInterceptor {
    @Test
    public void preHandle_shouldAllowSignIn() throws Exception {
        // 创建 LogInCheck 实例
        LogInCheck interceptor = new LogInCheck();

        // 创建 MockHttpServletRequest 和 MockHttpServletResponse
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        // 设置请求的 URL 为包含 "SignIn"
        request.setRequestURI("/yourapp/SignIn");

        // 调用 preHandle 方法
        boolean result = interceptor.preHandle(request, response, null);

        // 验证是否放行
        assert result;
        // 验证日志输出
        // ... 添加相应的断言语句
    }

    @Test
    public void preHandle_shouldReturnNotLoginError() throws Exception {
        // 创建 LogInCheck 实例
        LogInCheck interceptor = new LogInCheck();

        // 创建 MockHttpServletRequest 和 MockHttpServletResponse
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        // 设置请求的 URL 为不包含 "SignIn"
        request.setRequestURI("/yourapp/someotherurl");

        // 调用 preHandle 方法
        boolean result = interceptor.preHandle(request, response, null);

        // 验证是否返回未登录错误信息
        assert !result;
        // 验证响应内容
        // ... 添加相应的断言语句
        // 验证日志输出
        // ... 添加相应的断言语句
    }
}
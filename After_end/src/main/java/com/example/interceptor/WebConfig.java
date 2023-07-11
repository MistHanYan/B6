package com.example.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration //配置类
public class WebConfig implements WebMvcConfigurer {

    private final AdminCheck adminCheck;
    private final UserCheck userCheck;
    @Autowired
    public WebConfig(AdminCheck adminCheck, UserCheck userCheck) {
        this.adminCheck = adminCheck;
        this.userCheck = userCheck;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminCheck).addPathPatterns("/LogIn","/AllUser/**","/AdminSeek/**","/AdminDelete/**",
                "/AdminUpdate/**","/AdminToCollect/**","/AdminToHistory/**","/Check");
        registry.addInterceptor(userCheck).addPathPatterns("/UserLogIn","/history/**","/collect/**" , "/UserSignIn",
                "/recognition","/upload");
    }
}

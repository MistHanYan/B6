package com.example.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.example.entity.Jwt;
import com.example.entity.Result;
import com.example.entity.User;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class LogInCheck implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURI();
        log.info("登录url：{}", url);
        if (url.contains(("SignIn"))) {
            log.info("注册操作，放行。。。");
            return true;
        }
        if (url.contains(("LogIn"))) {
            log.info("注册操作，放行。。。");
            return true;
        }

        String jwt = request.getHeader("jwt");
        if (!StringUtils.hasLength(jwt)) {
            log.info("请求头token为空,返回未登录的信息");
            Result error = Result.error("NOT_LOGIN");
            //手动转换 对象--json --------> 阿里巴巴fastJSON
            String notLogin = JSONObject.toJSONString(error);
            response.getWriter().write(notLogin);
            return false;
        }

        //5.解析token，如果解析失败，返回错误结果（未登录）。
        try {
            Claims claims = Jwt.parseJwt(jwt);
            new User().setPhoneNumber(claims.get("phoneNum").toString());
        } catch (Exception e) {//jwt解析失败
            e.printStackTrace();
            log.info("解析令牌失败, 返回未登录错误信息");
            Result error = Result.error("NOT_LOGIN");
            //手动转换 对象--json --------> 阿里巴巴fastJSON
            String notLogin = JSONObject.toJSONString(error);
            response.getWriter().write(notLogin);
            return false;
        }
        //6.放行。
        log.info("ok");
        return true;
    }
}

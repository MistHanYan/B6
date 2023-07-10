package com.example.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.example.entity.Admin;
import com.example.entity.Result;
import com.example.redis.JwtRedis;
import com.example.service.AdminService;
import com.example.util.jwt_token.Jwt;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.WebUtils;

@Slf4j
@Component
public class AdminCheck implements HandlerInterceptor {

    @Resource
    AdminService adminService;

    @Resource
    JwtRedis jwtRedis;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Cookie jwt = WebUtils.getCookie(request, "jwt");
        // 对登录请求中账号存在性判断，存在返回生成的jwt
        if(request.getRequestURI().contains("LogIn")){
            if(request.getParameter("phoneNum") == null){
                Result error = Result.error("账号为null");
                response.getWriter().write(JSONObject.toJSONString(error));
                return false;
            }else {
                if(adminService.adminIsEmpty(request.getParameter("phoneNum"))){
                    return true;
                }else {
                    response.getWriter().write(JSONObject.toJSONString(Result.errorIsEmpty("admin isEmpty")));
                    return false;
                }
            }
        }
        if (jwt == null) {
            log.info("请求Cookie中jwt为空,返回未登录的信息");
            Result error = Result.error("jwt isEmpty");
            //手动转换 对象--json --------> 阿里巴巴fastJSON
            response.getWriter().write(JSONObject.toJSONString(error));
            return false;
        }

        //5.解析token，如果解析失败，返回错误结果（未登录）。


            // 判断用户密码
        if(request.getRequestURI().contains("Check")){
            try {
                Claims claims = Jwt.parseJwt(jwt.getValue());
                String phoneNum = claims.get("phoneNum").toString();
                String passWd = claims.get("passWd").toString();
                if(adminService.checkAdmin(phoneNum,passWd)){
                    Admin admin = new Admin();
                    admin.setJwt(jwt.getValue());
                    admin.setPassWd(passWd);
                    admin.setPhoneNum(phoneNum);
                    jwtRedis.save(jwt.getValue(),admin);
                    return true;
                }else {
                    response.getWriter().write(JSONObject.toJSONString(Result.error("密码错误")));
                    return false;
                }
            } catch (Exception e) {//jwt解析失败
                e.printStackTrace();
                log.info("解析令牌失败, 返回未登录错误信息");
                Result error = Result.error("NOT_LOGIN");
                //手动转换 对象--json --------> 阿里巴巴fastJSON
                response.getWriter().write(JSONObject.toJSONString(error));
                return false;
            }
        }
            // 令牌有效放行

        if(jwtRedis.get(jwt.getValue()) == null){
            response.getWriter().write(JSONObject.toJSONString(Result.error("令牌失效")));
            return false;
        }else {
            return true;
        }
    }
}

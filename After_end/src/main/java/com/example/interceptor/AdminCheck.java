package com.example.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.example.entity.Result;
import com.example.service.AdminService;
import com.example.util.jwt_token.Jwt;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class AdminCheck implements HandlerInterceptor {

    @Resource
    AdminService adminService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

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

        String jwt = request.getHeader("jwt");
        if (jwt == null) {
            log.info("请求头jwt为空,返回未登录的信息");
            Result error = Result.error("NOT_LOGIN");
            //手动转换 对象--json --------> 阿里巴巴fastJSON
            response.getWriter().write(JSONObject.toJSONString(error));
            return false;
        }



        //5.解析token，如果解析失败，返回错误结果（未登录）。
        try {
            Claims claims = Jwt.parseJwt(jwt);
            if(request.getRequestURI().contains("check")){
                if(adminService.checkAdmin(claims.get("phoneNum").toString(),claims.get("passWd").toString())){
                    response.getWriter().write(JSONObject.toJSONString(Result.success()));
                    return true;
                }
            }
            return true;
        } catch (Exception e) {//jwt解析失败
            e.printStackTrace();
            log.info("解析令牌失败, 返回未登录错误信息");
            Result error = Result.error("NOT_LOGIN");
            //手动转换 对象--json --------> 阿里巴巴fastJSON
            response.getWriter().write(JSONObject.toJSONString(error));
            return false;
        }
    }
}

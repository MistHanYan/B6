package com.example.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.example.entity.Result;
import com.example.redis.JwtRedis;
import com.example.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.WebUtils;

@Slf4j
@Component
public class UserCheck implements HandlerInterceptor {

    @Resource
    UserService userService;
    @Resource
    JwtRedis jwtRedis;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(request.getRequestURI().contains("UserSignIn")){
            return true;
        }
        String union_id = String.valueOf(WebUtils.getCookie(request,"union_id"));
        if(request.getRequestURI().contains("UserLogIn")){
            if (union_id.equals("")) {
                log.info("请求头union_id为空,返回未登录的信息");
                //手动转换 对象--json --------> 阿里巴巴fastJSON
                response.getWriter().write(JSONObject.toJSONString(Result.error("请求头为空")));
            } else {
                if (userService.checkUnionId(union_id)) {
                    jwtRedis.save(union_id,union_id);
                    return true;
                }
                response.getWriter().write(JSONObject.toJSONString(Result.errorIsEmpty("user isEmpty")));
            }
        }

        return jwtRedis.get(union_id) != null;
    }
}

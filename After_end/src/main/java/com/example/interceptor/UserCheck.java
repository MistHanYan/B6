package com.example.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.example.entity.Result;
import com.example.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class UserCheck implements HandlerInterceptor {

    @Resource
    UserService userService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(request.getRequestURI().contains("UserSignIn")){
            return true;
        }
        String union_id = request.getHeader("union_id");

        if (union_id.equals("")) {
            log.info("请求头union_id为空,返回未登录的信息");
            //手动转换 对象--json --------> 阿里巴巴fastJSON
            response.getWriter().write(JSONObject.toJSONString(Result.error("请求头为空")));
        } else {
            if (userService.checkUnionId(union_id)) {
                return true;
            }
            response.getWriter().write(JSONObject.toJSONString(Result.errorIsEmpty("user isEmpty")));
        }
        return false;
    }
}

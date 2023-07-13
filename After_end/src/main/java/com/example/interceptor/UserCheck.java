package com.example.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.example.entity.Result;
import com.example.entity.User;
import com.example.redis.JwtRedis;
import com.example.service.UserService;
import com.example.util.time.GetSystemTime;
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
    @Resource
    JwtRedis jwtRedis;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 注册请求直接放行
        if(request.getRequestURI().contains("UserSignIn")){
            if(userService.addUser(request.getHeader("union_id"))){
                jwtRedis.save(request.getHeader("union_id"),request.getHeader("union_id"));
                return true;
            }else {
                response.getWriter().write(JSONObject.toJSONString(Result.error("注册失败")));
                return false;
            }
        }

        // 登录拦截
        if(request.getRequestURI().contains("UserLogIn")){
            // 使用微信接口发送js_code
            JSONObject jsonObject = JSONObject.parseObject(userService.getResponse(request.getParameter("js_code")));

            System.out.println(jsonObject.toJSONString());
            int errorCode = jsonObject.getIntValue("errcode");

            // 错误代码拦截
            if(errorCode == 40029 || errorCode == 45011 || errorCode == 40226 || errorCode == -1){
                response.getWriter().write(JSONObject.toJSONString(Result.error("js_code 无效")));
                return false;
            }else {
                String union_id = jsonObject.getString("openid");

                // 空union_id 拦截
                if (union_id.equals("")) {
                    log.info("请求头union_id为空,返回未登录的信息");
                    //手动转换 对象--json --------> 阿里巴巴fastJSON
                    response.getWriter().write(JSONObject.toJSONString(Result.error("请求头为空")));
                } else {

                    // 检查用户是否在数据库，在则保持到redis
                    if (userService.checkUnionId(union_id)) {

                        // 更新用户时间
                        if(userService.updateLogInOfTime(union_id)){
                            log.info("用户{}，在{}时登录",union_id,GetSystemTime.getTime());
                        }
                        User user = new User();
                        user.setUnion_id(union_id);
                        user.setActivity(GetSystemTime.getTime());
                        jwtRedis.save(union_id,user);
                        response.getWriter().write(JSONObject.toJSONString(Result.success(user)));
                        return true;
                    }else {
                        response.getWriter().write(JSONObject.toJSONString(Result.userErrorIsEmpty(union_id)));
                        return false;
                    }
                }
            }
        }

        // 其他请求则拿响应头中union_id对服务器中已经鉴别的union_id，进行对比
        String union_id = request.getHeader("union_id");
        // 如果令牌不在redis，则判定失效
        if(jwtRedis.get(union_id) != null){
            return true;
        }else {
            response.getWriter().write(JSONObject.toJSONString(Result.error("令牌失效")));
            return false;
        }
    }
}

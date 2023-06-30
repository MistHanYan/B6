package com.example.interceptor;

import com.example.entity.User;
import com.example.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

import static com.example.interceptor.Token.getJwt;

@RestController
public class RequestLogIn {

    @Resource
    UserService userService;

    // 登录成功下发jwt令牌
    @GetMapping("/LogIn")
    public User LogInJwt(@RequestParam("phoneNum") String phoneNum){
        HashMap<String, Object> phone = new HashMap<>();
        User user = userService.getUserByPhoneNum(phoneNum);

        // 判断是否在数据库中存在用户，不存在返回空体
        if(user != null){
            phone.put("phoneNum",user.getPhoneNumber());
            String jwt =  getJwt(phone);
            user.setJwt(jwt);
            return user;
        }else {
            return null;
        }
    }

    // 注册成功下发jwt令牌
    @GetMapping("/SignIn")
    public User SignInJwt(@RequestParam("phoneNum") String phoneNum ,@RequestParam("userName") String userName){
        User user = userService.setUserBySql(phoneNum,userName,"false");
        HashMap<String, Object> phone = new HashMap<>();
        phone.put("phoneNum",user.getPhoneNumber());
        String jwt =  getJwt(phone);
        user.setJwt(jwt);
        return user;
    }
}

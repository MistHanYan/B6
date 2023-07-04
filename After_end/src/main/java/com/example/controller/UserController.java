package com.example.controller;

import com.example.entity.Admin;
import com.example.entity.Result;
import com.example.entity.User;
import com.example.service.AdminService;
import com.example.service.UserService;
import com.example.util.db.AdminSQLUtil;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

import static com.example.entity.Jwt.getJwt;


@RestController
public class UserController {
    @Resource
    AdminService adminService;

    // 登录成功下发jwt令牌
    @GetMapping("/LogIn")
    public Result LogInJwt(@RequestParam String phoneNum ,String passWd){
        HashMap<String, Object> phone = new HashMap<>();
        Admin admin = adminService.getAdminByPhoneNum(phoneNum);

        // 判断是否在数据库中存在管理员，不存在返回错误信息
        // 存在下发jwt
        if(admin != null){
            System.out.println(passWd);
            if(AdminSQLUtil.checkPassWd(admin,passWd)){
                phone.put("phoneNum",admin.getPhoneNum());
                String jwt = getJwt(phone);
                admin.setJwt(jwt);
                return Result.success(admin);
            }else {
                return Result.error("admin is error for passWd ");
            }

        }else {
            return Result.error("admin isEmpty in sql");
        }
    }

    @Resource
    UserService userService;

    // 小程序登录，注册
    @GetMapping("/UserLogIn")
    public Result userLogIn(@RequestParam String union_id ,String type){
        User user = userService.getUserByUnionId(union_id,type);
        if(user != null && user.getUnion_id().equals(union_id)){
            return Result.success(user);
        }else{
            return Result.error("注册失败，请联系管理员");
        }
    }
}

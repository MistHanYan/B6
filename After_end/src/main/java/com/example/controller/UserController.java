package com.example.controller;

import com.example.entity.*;
import com.example.entity.request.Collect;
import com.example.entity.request.HistoryRe;
import com.example.service.AdminService;
import com.example.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.entity.Jwt.getJwt;

@Slf4j
@RestController
public class UserController {
    @Resource
    AdminService adminService;

    // 登录成功下发jwt令牌
    @GetMapping("/LogIn")
    public Result LogInJwt(@RequestParam String phoneNum ,String passWd){
        HashMap<String, Object> phone = new HashMap<>();
        Admin admin = adminService.getAdminByPhoneNum(phoneNum,passWd);

        // 判断是否在数据库中存在管理员，不存在返回错误信息
        // 存在下发jwt
        if(admin != null){
                phone.put("phoneNum",admin.getPhoneNum());
                String jwt = getJwt(phone);
                admin.setJwt(jwt);
                return Result.success(admin);
        }else {
            return Result.error("admin isEmpty in sql");
        }
    }

    @Resource
    UserService userService;

    // 小程序登录，注册
    @GetMapping("/UserLogIn")
    public Result userLogIn(@RequestParam String union_id){
        User user = userService.getUserByUnionId(union_id);
        if(user != null && user.getUnion_id().equals(union_id)){
            return Result.success(user);
        }else{
            return Result.error("注册,登录失败，请联系管理员");
        }
    }
    @PostMapping("/collect")
    public Result userCollect(@RequestBody Collect collect){
        if(userService.checkUnionId(collect.getUnion_id())){
            ArrayList<Collection> collections= userService.getUserCollectByUnionId(collect.getUnion_id(),collect.getNum());
            return Result.success(collections);
        }
        return Result.checkError("错误union_id,操作失败");
    }

    @PostMapping("/history")
    public Result userHistory(@RequestBody HistoryRe historyRe){
        if(userService.checkUnionId(historyRe.getUnion_id())){
            ArrayList<History> history = userService.getUserHistoryByUnionId(historyRe.getUnion_id(), historyRe.getNum());
            return Result.success(history);
        }
        return Result.checkError("错误union_id,操作失败");
    }
    @PostMapping("/history/add")
    public Result userAddHistory(@RequestBody History history){
        System.out.println(history);
        if(userService.checkUnionId(history.getUnion_id()) && userService.addUserHistory(history)){
            return Result.success();
        }
        return Result.checkError("错误union_id,操作失败");
    }

    @PostMapping("/collect/add")
    public Result userAddCollect(@RequestBody Collection collection){
        // TODO 收藏时，项目名冲突
        if(userService.checkUnionId(collection.getUnion_id()) && userService.addUserCollect(collection)){
            return Result.success();
        }
        return Result.checkError("错误union_id,操作失败");
    }

    @GetMapping("/collect/seek")
    public Result userSeek(@RequestParam String union_id , String seekStatement){
        if(userService.checkUnionId(union_id)){
            return Result.success(userService.seekCollect(seekStatement,union_id));
        }
        return Result.checkError("错误union_id,操作失败");
    }
}

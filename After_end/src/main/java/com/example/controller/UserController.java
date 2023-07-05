package com.example.controller;

import com.example.entity.*;
import com.example.service.AdminService;
import com.example.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.entity.Jwt.getJwt;

@Slf4j
@RestController
public class UserController {
    @Resource
    AdminService adminService;

    @Autowired
    private HttpServletRequest request;

    // 管理员登录成功下发jwt令牌
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

    //
    @Resource
    UserService userService;

    // 小程序登录，注册
    @GetMapping("/UserLogIn")
    public Result userLogIn(){
        String union_id = request.getHeader("union_id");
        User user = userService.getUserByUnionId(union_id);
        if(user != null && userService.checkUnionId(union_id)){
            return Result.success(user);
        }else{
            return Result.error("注册,登录失败，请联系管理员");
        }
    }

    // 收藏数据加载
    @PostMapping("/collect/{num}")
    public Result userCollect(@PathVariable String num){
        String union_id = request.getHeader("union_id");
        if(userService.checkUnionId(union_id)){
            ArrayList<Collection> collections= userService.getUserCollectByUnionId(union_id, Integer.parseInt(num));
            return Result.success(collections);
        }
        return Result.checkError("错误union_id,操作失败");
    }
    // 历史记录加载
    @PostMapping("/history/{num}")
    public Result userHistory(@PathVariable String num){
        String union_id = request.getHeader("union_id");
        if(userService.checkUnionId(union_id)){
            ArrayList<History> history = userService.getUserHistoryByUnionId(union_id, Integer.parseInt(num));
            return Result.success(history);
        }
        return Result.checkError("错误union_id,操作失败");
    }

    // 添加历史记录
    @PostMapping("/history/add")
    public Result userAddHistory(@RequestBody History history){
        String union_id = request.getHeader("union_id");
        history.setUnion_id(union_id);
        if(userService.checkUnionId(union_id) && userService.addUserHistory(history)){
            return Result.success();
        }
        return Result.checkError("错误union_id,操作失败");
    }

    // 添加收藏记录
    @PostMapping("/collect/add")
    public Result userAddCollect(@RequestBody Collection collection){
        String union_id = request.getHeader("union_id");
        collection.setUnion_id(union_id);
        // TODO 收藏时，项目名冲突 / 以解决
        if(userService.checkUnionId(union_id)){
            if(userService.addUserCollect(collection)){
                return Result.success();
            }
            return Result.error("收藏失败");
        }
        return Result.checkError("错误union_id,操作失败");
    }

    // 收藏项目搜索
    @GetMapping("/collect/seek")
    public Result userCollectSeek(@RequestParam String seekStatement){
        String union_id = request.getHeader("union_id");
        if(userService.checkUnionId(union_id)){
            return Result.success(userService.seekCollect(seekStatement,union_id));
        }
        return Result.checkError("错误union_id,操作失败");
    }

    // 收藏记录删除
    @DeleteMapping("/collect/delete/{projectID}")
    public Result userCollectDelete(@PathVariable String projectID){
        String union_id = request.getHeader("union_id");
        if(userService.checkUnionId(union_id) && userService.deleteUserProject(union_id,projectID)){
            return Result.success();
        }
        return Result.error("操作失败");
    }

}

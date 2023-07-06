package com.example.controller;

import com.example.entity.Collection;
import com.example.entity.History;
import com.example.entity.Result;
import com.example.service.UserService;
import com.example.util.time.GetSystemTime;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@Slf4j
@RestController
public class UserController {

    @Autowired
    private HttpServletRequest request;

    @Resource
    UserService userService;

    @GetMapping("/UserLogIn")
    public Result logIn(HttpServletRequest request) throws SQLException {
        if(userService.updateLogInOfTime(request.getHeader("union_id"))){
            log.info("用户{}，在{}时，登录成功",request.getHeader("union_id"), GetSystemTime.getTime());
        }
        return Result.success();
    }

    @GetMapping("/UserSignIn")
    public Result signIn(HttpServletRequest request) throws SQLException {
        if(userService.addUser(request.getHeader("union_id"))){
            return Result.success();
        }else {
            return Result.error("注册失败");
        }
    }

    // 收藏数据加载
    @GetMapping("/collect/{num}/{end}")
    public Result userCollect(@PathVariable String num ,@PathVariable String end){
        return Result.success(userService.getUserCollectByUnionId(request.getHeader("union_id"),num,end));
    }
    // 历史记录加载
    @GetMapping("/history/{num}/{end}")
    public Result userHistory(@PathVariable String num,@PathVariable String end){
        return Result.success(userService
                .getUserHistoryByUnionId(request.getHeader("union_id"), num,end));
    }

    // 添加历史记录
    @PostMapping("/history/add")
    public Result userAddHistory(@RequestBody History history){
        history.setUnion_id(request.getHeader("union_id"));
        if(userService.addUserHistory(history)){
            return Result.success();
        }
        return Result.checkError("添加历史操作，失败");
    }

    // 添加收藏记录
    @PostMapping("/collect/add")
    public Result userAddCollect(@RequestBody Collection collection){
        collection.setUnion_id(request.getHeader("union_id"));
            if(userService.addUserCollect(collection)){
                return Result.success();
            }
            return Result.error("收藏失败");
    }

    // 收藏项目搜索
    @GetMapping("/collect/seek")
    public Result userCollectSeek(@RequestParam String seekStatement){
        return Result.success(userService.seekCollect(seekStatement,request.getHeader("union_id")));
    }

    // 收藏记录删除
    @DeleteMapping("/collect/delete/{projectID}")
    public Result userCollectDelete(@PathVariable String projectID){
        if(userService.deleteUserProject(request.getHeader("union_id"),projectID)){
            return Result.success();
        }
        return Result.error("操作失败");
    }

}

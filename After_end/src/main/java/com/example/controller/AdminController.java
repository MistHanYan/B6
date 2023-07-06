package com.example.controller;

import com.example.entity.Result;
import com.example.service.AdminService;
import com.example.util.jwt_token.Jwt;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;

@Slf4j
@RestController
public class AdminController {

    @Resource
    AdminService adminService;

    @GetMapping("/LogIn")
    public Result logeIn(@RequestParam String phoneNum , String passWd){
        HashMap<String, Object> admin = new HashMap<>();
        admin.put("phoneNum",phoneNum);
        admin.put("passWd",passWd);
        return Result.success(Jwt.getJwt(admin));
    }

    @GetMapping("/Check")
    public Result check(HttpServletRequest request) throws SQLException {
        Claims claims = Jwt.parseJwt(request.getHeader("jwt"));
        if(adminService.checkAdmin(claims.get("phoneNum").toString(),claims.get("passWd").toString())){
            if(adminService.upDateAdminLogInTime(claims.get("phoneNum").toString())){
                log.error("用户{}，时间更新失败",claims.get("phoneNum"));
            }
            return Result.success();
        }else {
            return Result.checkError("账号/密码错误");
        }
    }

    @GetMapping("/AllUser/{num}")
    public Result getUser(@PathVariable String num , HttpServletRequest request) throws SQLException {
        Claims claims = Jwt.parseJwt(request.getHeader("jwt"));
        if(adminService.checkAdmin(claims.get("phoneNum").toString(),claims.get("passWd").toString())){
            return Result.success(adminService.getAllUser(num));
        }else {
            return Result.checkError("账号/密码错误");
        }
    }

    @GetMapping("/AdminToHistory/{num}/{end}")
    public Result getHistory(@PathVariable String num ,@PathVariable String end, @RequestParam String union_id , HttpServletRequest request)
            throws SQLException {
        Claims claims = Jwt.parseJwt(request.getHeader("jwt"));
        if(adminService.checkAdmin(claims.get("phoneNum").toString(),claims.get("passWd").toString())){
            return Result.success(adminService.getUserHistoryByUnionID(union_id,num,end));
        }else {
            return Result.checkError("账号/密码错误");
        }
    }

    @GetMapping("/AdminToCollect/{num}/{end}")
    public Result getCollect(@PathVariable String num,@PathVariable String end,@RequestParam String union_id , HttpServletRequest request) throws SQLException {
        Claims claims = Jwt.parseJwt(request.getHeader("jwt"));
        if(adminService.checkAdmin(claims.get("phoneNum").toString(),claims.get("passWd").toString())){
            return Result.success(adminService.getUserCollectByUnionID(union_id,num,end));
        }else {
            return Result.checkError("账号/密码错误");
        }
    }

    @GetMapping("/AdminSeek/history/seek")
    public Result getSeekHistoryToOutcome(@RequestParam String seek , HttpServletRequest request) throws SQLException {
        Claims claims = Jwt.parseJwt(request.getHeader("jwt"));
        if(adminService.checkAdmin(claims.get("phoneNum").toString(),claims.get("passWd").toString())){
            return Result.success(adminService.getLikeHistoryBySeekStatement(seek));
        }else {
            return Result.checkError("账号/密码错误");
        }
    }
    @GetMapping("/AdminSeek/collect/seek")
    public Result getSeekCollectToOutcome(@RequestParam String seek , HttpServletRequest request) throws SQLException {
        Claims claims = Jwt.parseJwt(request.getHeader("jwt"));
        if(adminService.checkAdmin(claims.get("phoneNum").toString(),claims.get("passWd").toString())){
            return Result.success(adminService.getLikeCollectBySeekStatement(seek));
        }else {
            return Result.checkError("账号/密码错误");
        }
    }

    @GetMapping("/AdminSeek/User/{seek}")
    public Result getSeekUserToOutcome(@PathVariable String seek , HttpServletRequest request) throws SQLException {
        Claims claims = Jwt.parseJwt(request.getHeader("jwt"));
        if(adminService.checkAdmin(claims.get("phoneNum").toString(),claims.get("passWd").toString())){
            System.out.println(seek);
            return Result.success(adminService.getLikeUserSeekStatement(seek));
        }else {
            return Result.checkError("账号/密码错误");
        }
    }

    @GetMapping("/AdminUpdate/{union_id}/{new_union_id}")
    public Result updateUser(@PathVariable String union_id , @PathVariable String new_union_id ,
                             HttpServletRequest request) throws SQLException {
        Claims claims = Jwt.parseJwt(request.getHeader("jwt"));
        if(adminService.checkAdmin(claims.get("phoneNum").toString(),claims.get("passWd").toString())){
            if(adminService.updateUserByUnionId(union_id,new_union_id)){
                return Result.success();
            }
            return Result.error("更新失败");
        }else {
            return Result.checkError("账号/密码错误");
        }

    }

    @DeleteMapping("/AdminDelete/{union_id}")
    public Result deleteUser(@PathVariable String union_id , HttpServletRequest request) throws SQLException {
        Claims claims = Jwt.parseJwt(request.getHeader("jwt"));
        if(adminService.checkAdmin(claims.get("phoneNum").toString(),claims.get("passWd").toString())){
            if(adminService.deleteUserByUnionId(union_id)){
                return Result.success();
            }
            return Result.error("删除失败");
        }else {
            return Result.checkError("账号/密码错误");
        }

    }
}

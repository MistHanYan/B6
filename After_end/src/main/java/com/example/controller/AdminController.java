package com.example.controller;

import com.example.entity.Admin;
import com.example.entity.Result;
import com.example.redis.JwtRedis;
import com.example.service.AdminService;
import com.example.util.jwt_token.Jwt;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;

@CrossOrigin(origins = "http://127.0.0.1", allowCredentials = "true",allowedHeaders = {"*"})
@Slf4j
@RestController
public class AdminController {

    @Resource
    AdminService adminService;

    @Resource
    JwtRedis jwtRedis;
    @GetMapping("/LogIn")
    public Result logeIn(@RequestParam String phoneNum , String passWd){
        HashMap<String, Object> admin = new HashMap<>();
        admin.put("phoneNum",phoneNum);
        admin.put("passWd",passWd);
        Admin admin1 = new Admin();
        admin1.setJwt(Jwt.getJwt(admin));
        return Result.success(admin1);
    }

    @GetMapping("/Check")
    public Result check(@CookieValue("jwt") String jwt) throws SQLException {
        Admin admin = (Admin) jwtRedis.get(jwt);
        if(adminService.upDateAdminLogInTime(admin.getPhoneNum())){
            log.info("用户{}，时间更新成功",admin.getPhoneNum());
        }
        return Result.success();
    }

    @GetMapping("/AllUser/{num}")
    public Result getUser(@PathVariable String num) throws SQLException {
        return Result.success(adminService.getAllUser(num));

    }

    @GetMapping("/AdminToHistory/{num}/{end}")
    public Result getHistory(@PathVariable String num ,@PathVariable String end, @RequestParam String union_id)
            throws SQLException {
        return Result.success(adminService.getUserHistoryByUnionID(union_id,num,end));

    }

    @GetMapping("/AdminToCollect/{num}/{end}")
    public Result getCollect(@PathVariable String num,@PathVariable String end,@RequestParam String union_id) throws SQLException {
        return Result.success(adminService.getUserCollectByUnionID(union_id,num,end));
    }

    @GetMapping("/AdminSeek/history/seek")
    public Result getSeekHistoryToOutcome(@RequestParam String seek) throws SQLException {
        return Result.success(adminService.getLikeHistoryBySeekStatement(seek));
    }
    @GetMapping("/AdminSeek/collect/seek")
    public Result getSeekCollectToOutcome(@RequestParam String seek) throws SQLException {
        return Result.success(adminService.getLikeCollectBySeekStatement(seek));
    }

    @GetMapping("/AdminSeek/User/{seek}")
    public Result getSeekUserToOutcome(@PathVariable String seek) throws SQLException {
        return Result.success(adminService.getLikeUserSeekStatement(seek));
    }

    @GetMapping("/AdminUpdate/{union_id}/{new_union_id}")
    public Result updateUser(@PathVariable String union_id , @PathVariable String new_union_id) throws SQLException {
        if(adminService.updateUserByUnionId(union_id,new_union_id)) {
            return Result.success();
        }return Result.error("更新失败");
    }

    @DeleteMapping("/AdminDelete/{union_id}")
    public Result deleteUser(@PathVariable String union_id) throws SQLException {
        if(adminService.deleteUserByUnionId(union_id)){
            return Result.success();
        }
        return Result.error("删除失败");
    }
}

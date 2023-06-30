package com.example.controller;

import com.example.entity.User;
import com.example.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {
    @Resource
    UserService userService;

    @GetMapping("/LogI")
    public User getUser(@RequestParam("phoneNum") String phoneNum) {
        //String id = request.getParameter("id");
//        TODO
        //System.out.println(phone_num);

        return userService.getUserByPhoneNum(phoneNum);
    }
}

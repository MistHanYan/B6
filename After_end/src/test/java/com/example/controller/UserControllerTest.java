package com.example.controller;

import com.example.entity.User;
import com.example.service.UserService;
import jakarta.annotation.Resource;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserControllerTest {

    @Resource
    UserService userService;
    @Test
    public void show(){
        User user = userService.getUserByPhoneNum("17726639096");
        if(user != null){
            System.out.println(true);
        }else {
            System.out.println(false);
        }
    }

}
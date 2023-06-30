package com.example.controller;

import com.example.entity.User;
import com.example.service.UserService;
import jakarta.annotation.Resource;
import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {
    @Resource
    UserService userService;

    @GetMapping("/get-user/{id}")
    public User getUser(@PathVariable String id) {
//        If the user-id is not the integer, log the error
//        TODO

        return userService.getUserById(Integer.parseInt(id));
    }
}

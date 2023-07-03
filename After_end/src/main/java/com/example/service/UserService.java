package com.example.service;

import com.example.entity.User;

public interface UserService {
    //User getUserById(Integer id);
    User getUserByPhoneNum(String phoneNum);
    User setUserBySql(String phoneNum , String userName , String type);
}

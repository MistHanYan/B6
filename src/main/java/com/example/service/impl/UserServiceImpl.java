package com.example.service.impl;

import com.example.entity.User;
import com.example.service.UserService;
import com.example.util.db.MySQLUtil;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public User getUserById(Integer id) {
        try {
            return MySQLUtil.queryUserById(id.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getUserByPhoneNum(String phoneNum) {
        try {
            return MySQLUtil.queryUserByPhoneNum(phoneNum);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User setUserBySql(String phoneNum, String userName, String type) {
        try {
            return MySQLUtil.insertQueryUserByPhone(phoneNum,userName,type);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

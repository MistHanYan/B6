package com.example.service.impl;

import com.example.entity.User;
import com.example.service.UserService;
import com.example.util.db.UserSQLUtil;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public User getUserByUnionId(String union_id , String type) {
        try {
            return UserSQLUtil.queryUserByUnionId(union_id ,type);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

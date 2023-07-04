package com.example.service;

import com.example.entity.User;

public interface UserService {
    //User getUserById(Integer id);
    User getUserByUnionId(String union_id);
}

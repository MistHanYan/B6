package com.example.service.impl;

import com.example.entity.Admin;
import com.example.service.AdminService;
import com.example.util.db.AdminSQLUtil;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class AdminServiceImpl implements AdminService {

    @Override
    public Admin getAdminByPhoneNum(String phoneNum) {
        try {
            return AdminSQLUtil.queryAdminByPhoneNum(phoneNum);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

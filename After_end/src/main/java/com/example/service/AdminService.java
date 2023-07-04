package com.example.service;

import com.example.entity.Admin;

public interface AdminService {
    Admin getAdminByPhoneNum(String phoneNum);
}

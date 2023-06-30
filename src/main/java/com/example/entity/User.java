package com.example.entity;


import lombok.Data;

import java.sql.Timestamp;

@Data
public class User {
    private String username;
    private String phoneNumber;
    private Integer userId;
    private Timestamp activity;
    private String type;
    private String jwt;
}

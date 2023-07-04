package com.example.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Admin {
    private Integer id;
    private String phoneNum;
    private String passWd;
    private Timestamp activity_time;
    private String type;
    private String jwt;
}

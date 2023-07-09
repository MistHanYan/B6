package com.example.entity;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class Admin implements Serializable {
    private Integer id;
    private String phoneNum;
    private String passWd;
    private Timestamp activity_time;
    private String type;
    private String jwt;
}

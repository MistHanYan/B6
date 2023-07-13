package com.example.entity;


import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class User implements Serializable {
    private String union_id;
    private Integer userId;
    private Timestamp activity;
    private String type;
}

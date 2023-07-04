package com.example.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class History {
    private String union_id;
    private Timestamp date;
    private String url;
    private String Case;
}

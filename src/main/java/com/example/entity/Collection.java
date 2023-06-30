package com.example.entity;


import lombok.Data;

@Data
public class Collection {
    private Integer userId;
    private String url;

    // primary key
    private String collectName;

    private String date;
}

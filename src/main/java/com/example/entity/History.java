package com.example.entity;

import lombok.Data;

@Data
public class History {
    private Integer userId;
    private String date;
    private String imageUrl;

    private String diagnoseName;

}

package com.example.entity;


import lombok.Data;

import java.sql.Timestamp;

@Data
public class Collection {
    private String union_id;
    private String url;
    private String collectName;
    private String caseName;
    private Timestamp date;
}

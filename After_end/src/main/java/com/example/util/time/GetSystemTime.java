package com.example.util.time;

import java.sql.Timestamp;
import java.util.Date;

public class GetSystemTime {
    // 获取当前时间
    public static Timestamp getTime(){
        return new Timestamp(new Date().getTime());
    }
}

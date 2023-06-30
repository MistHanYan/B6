package com.example.util.db;

import org.junit.Test;

public class MySQLUtilTest {
    @Test
    public void queryUserById() throws Exception {
        System.out.println(MySQLUtil.queryUserById("1"));
    }
}
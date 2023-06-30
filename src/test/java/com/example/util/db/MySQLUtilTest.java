package com.example.util.db;

import jakarta.annotation.Resource;
import org.junit.Test;

import static org.junit.Assert.*;

public class MySQLUtilTest {
    @Test
    public void queryUserById() throws Exception {
        System.out.println(MySQLUtil.queryUserById("1"));
    }
}
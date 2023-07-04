package com.example.util.db;

import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.Date;

@Slf4j
public final class MySQLUtil {

    // 创建接口类
    private static final Connection connection;

    private static final String upDatedTimeSql = "UPDATE tb_user SET activity_time = ? WHERE union_id = ?";

    // 静态加载驱动
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/miaow_b6", "root", "077617");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 组织占位符语句
    static PreparedStatement getStatement(String sql, String... parameters) {
        PreparedStatement statement;
        try {
           statement = connection.prepareStatement(sql);

            for (int i = 0; i < parameters.length; i++) {
                statement.setString(i + 1, parameters[i]);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return statement;
    }

    // 获取当前时间
    public static Timestamp getTime(){
        return new Timestamp(new Date().getTime());
    }

    // 更新用户登录时间
    public static void setActivity(String union_id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(upDatedTimeSql);
        Timestamp time = getTime();
        preparedStatement.setTimestamp(1,time);
        preparedStatement.setString(2,union_id);
        int row = preparedStatement.executeUpdate();
        if(row > 0){
            log.info("用户:{},在{}时，时间更新成功",union_id,time);
        }else{
            log.info("用户：{}，在{}时，时间更新失败",union_id,time);
        }
    }
}

package com.example.util.db;

import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.Date;

@Slf4j
public final class MySQLUtil {

    // 创建接口类
    private static final Connection connection;

    private static final String upDatedTimeSql = "UPDATE tb_user SET activity_time = ? WHERE union_id = ? AND type = ?";

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
    public static void setActivity(String union_id , String type) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(upDatedTimeSql);
        Timestamp time = getTime();
        preparedStatement.setTimestamp(1,time);
        preparedStatement.setString(2,union_id);
        preparedStatement.setString(3,type);
        int row = preparedStatement.executeUpdate();
        if(row > 0){
            log.info("用户:{},在{}时，时间更新成功",union_id,time);
        }else{
            log.info("用户：{}，在{}时，时间更新失败",union_id,time);
        }
    }

    // 组合含有int类型的Sql查询语句（用于收藏，历史查询）
    public static PreparedStatement showDataInInt(String sql , String union_id , int num){
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, union_id);
            statement.setInt(2,10);
            statement.setInt(3,num-10);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return statement;
    }

    // 组合含有TimesTemp类型的Sql 插入语句
    public static PreparedStatement getInsertSql(String sql, Timestamp timestamp,String... parameters){
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(sql);
            statement.setTimestamp(1,timestamp);
            for (int i = 0; i < parameters.length; i++) {
                statement.setString(i + 2, parameters[i]);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return statement;
    }
}

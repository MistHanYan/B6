package com.example.util.db;

import com.example.entity.User;

import java.sql.*;

public final class MySQLUtil {

    // 创建接口类
    private static final Connection connection;

    // 电话查询语句
    private static final String userQuerySql = "SELECT * FROM tb_user WHERE phone_num = ?";

    private static final String userInsertQuerySql = "INSERT INTO tb_user (phone_num , user_name , type) VALUES (?,?,?)";
    // id 查询语句
    private static final String userIDQuerySql = "SELECT * FROM tb_user WHERE id = ?";

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
    private static PreparedStatement getStatement(String sql, String... parameters) {
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

    // 插入id查询语句，并返回封装的User对象
    public static User queryUserById(String id) throws SQLException {
        return userDataConverter(getStatement(userIDQuerySql, id).executeQuery());
    }

    public static User insertQueryUserByPhone(String phoneNum , String userName , String type) throws SQLException {
        int rows = getStatement(userInsertQuerySql, phoneNum , userName, type).executeUpdate();
        if(rows == 0){
            return null;
        }else{
            return userDataConverter(getStatement(userQuerySql, phoneNum).executeQuery());
        }

    }

    // 插入电话查询语句，并返回封装的User对象
    public static User queryUserByPhoneNum(String phoneNum) throws SQLException {
        if(isEmp(getStatement(userQuerySql,phoneNum).executeQuery())){
            return null;
        }
        return userDataConverter(getStatement(userQuerySql, phoneNum).executeQuery());
    }

    // 封装User对象
    private static User userDataConverter(ResultSet set) {
        User user = new User();

        try {
            while (set.next()) {
                user.setUserId(set.getInt("user_id"));
                user.setPhoneNumber(set.getString("phone_num"));
                user.setUsername(set.getString("user_name"));
                user.setActivity(set.getTimestamp("activity_time"));
                user.setType(set.getString("type"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return user;
    }

    // 判断用户是否在数据库里面
    public static boolean isEmp(ResultSet set) throws SQLException {
        return !set.next();
    }
}

package com.example.util.db;

import java.sql.*;

public final class MySQLUtil {
    // 通过union_id查找用户是否在数据库中
    private static final String userIDQuerySql = "SELECT * FROM tb_user WHERE union_id = ?";
    // 创建接口类
    private static final Connection connection;

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

    // 判断用户是否在数据库里面
    public boolean unionIdIsEmpty(String union_id) throws SQLException {
        return getStatement(userIDQuerySql, union_id).executeQuery() == null;
    }
}

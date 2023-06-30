package com.example.util.db;

import com.example.entity.User;

import java.sql.*;

public final class MySQLUtil {
    private static final Connection connection;
    private static final String userQuerySql = "SELECT * FROM tb_user WHERE user_id = ?";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/miaow_b6", "root", "077617");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

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

    public static User queryUserById(String id) throws SQLException {
        return userDataConverter(getStatement(userQuerySql, id).executeQuery());
    }

    private static User userDataConverter(ResultSet set) {
        User user = new User();

        try {
            while (set.next()) {
                user.setUserId(set.getInt("user_id"));
                user.setPhoneNumber(set.getString("phone_number"));
                user.setUsername(set.getString("username"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return user;
    }
}

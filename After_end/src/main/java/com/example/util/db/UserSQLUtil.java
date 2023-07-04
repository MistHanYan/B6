package com.example.util.db;

import com.example.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.util.db.MySQLUtil.getStatement;

public class UserSQLUtil {
    // 电话查询语句
    private static final String userIDQuerySql = "SELECT * FROM tb_user WHERE union_id = ?";

    // 插入数据库数据
    private static final String userInsertQuerySql = "INSERT INTO tb_user (union_id, type) VALUES (?,?)";

    // 插入id查询语句，并返回封装的User对象
    public static User queryUserById(String union_id) throws SQLException {
        return userDataConverter(getStatement(userIDQuerySql, union_id).executeQuery());
    }

    // 向数据库插入数据
    public static User insertQueryUserByUnionId(String union_id , String type) throws SQLException {
        int rows = getStatement(userInsertQuerySql, union_id , type).executeUpdate();
        if(rows == 0){
            return null;
        }else{
            return queryUserById(union_id);
        }
    }

    // 插入union_id查询语句，查看用户是否在数据库，存在则查找并包装
    // 如果不纯在完成插入数据或注册操作
    public static User queryUserByUnionId(String union_id) throws SQLException {
        if(new MySQLUtil().unionIdIsEmpty(union_id)){
            return insertQueryUserByUnionId(union_id,"false");
        }
        return queryUserById(union_id);
    }

    // 封装User对象
    private static User userDataConverter(ResultSet set) {
        User user = new User();

        try {
            while (set.next()) {
                user.setUserId(set.getInt("user_id"));
                user.setUnion_id(set.getString("phone_num"));
                user.setActivity(set.getTimestamp("activity_time"));
                user.setType(set.getString("type"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return user;
    }
}

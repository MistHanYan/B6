package com.example.util.db;

import com.example.entity.Admin;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.util.db.MySQLUtil.getStatement;

public class AdminSQLUtil {

    private static final String AdminIDQuerySql = "SELECT * FROM tb_user WHERE union_id = ?";


    // 插入id查询语句，并返回封装的Admin对象
    public static Admin queryAdminById(String phoneNum) throws SQLException {
        return adminDataConverter(getStatement(AdminIDQuerySql, phoneNum).executeQuery());
    }

    // 插入union_id查询语句，查看用户是否在数据库，存在则查找并包装
    public static Admin queryAdminByPhoneNum(String phoneNum) throws SQLException {
        if(new MySQLUtil().unionIdIsEmpty(phoneNum)){
            return null;
        }
        return queryAdminById(phoneNum);
    }

    // 检查管理员密码是否正确
    public static boolean checkPassWd(Admin admin , String passWd){
        return admin.getPassWd().equals(passWd);
    }

    // 封装管理员数据
    private static Admin adminDataConverter(ResultSet set) {
        Admin admin = new Admin();

        try {
            while (set.next()) {
                admin.setId(set.getInt("user_id"));
                admin.setPhoneNum(set.getString("union_id"));
                admin.setActivity_time(set.getTimestamp("activity_time"));
                admin.setType(set.getString("type"));
                admin.setPassWd(set.getString("passWd"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return admin;
    }
}

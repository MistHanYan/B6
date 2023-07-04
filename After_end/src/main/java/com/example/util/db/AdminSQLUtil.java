package com.example.util.db;

import com.example.entity.Admin;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import static com.example.util.db.MySQLUtil.getStatement;

@Slf4j
public class AdminSQLUtil {
    // 通过union_id查找用户是否在数据库中
    private static final String AdminIDQuerySql = "SELECT * FROM tb_user WHERE union_id = ? AND type = ?";


    // 插入id查询语句，并返回封装的Admin对象
    public static Admin queryAdminCheck(String phoneNum , String passWd) throws SQLException {

        if(checkAdminPassWd(getAdminSqlPassWd(phoneNum),passWd)){
            Timestamp timestamp = MySQLUtil.getTime();
            log.info("admin用户：{}，在{}，登录成功",phoneNum,timestamp);
            updateAdminTime(phoneNum);
            return adminDataConverter(getStatement(AdminIDQuerySql, phoneNum , "1").executeQuery());
        }else {
            return null;
        }
    }

    // 登录时更新admin时间,写入日志
    public static void updateAdminTime(String phoneNum) throws SQLException {
        MySQLUtil.setActivity(phoneNum, "1");
    }

    // 判断admin密码
    public static boolean checkAdminPassWd(String sqlPassWd, String inputPassWd){
        return sqlPassWd.equals(inputPassWd);
    }

    // 获取数据库中admin密码
    public static String getAdminSqlPassWd(String phoneNum) throws SQLException {
        ResultSet set = getStatement(AdminIDQuerySql, phoneNum , "1").executeQuery();
        set.next();
        return set.getString("union_id");
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

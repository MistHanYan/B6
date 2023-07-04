package com.example.util.db;

import com.example.entity.User;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import static com.example.util.db.MySQLUtil.getStatement;
@Slf4j
public class UserSQLUtil {
    // union_id查询语句
    private static final String userIDQuerySql = "SELECT * FROM tb_user WHERE union_id = ? AND type = ?";

    // 插入数据库数据
    private static final String userInsertQuerySql = "INSERT INTO tb_user (union_id, type , activity_time) VALUES (?,?,?)";

    // 插入id查询语句，并返回封装的User对象
    public static User queryUserById(String union_id) throws SQLException {
        return userDataConverter(getStatement(userIDQuerySql, union_id , "0").executeQuery());
    }

    // 向数据库插入数据
    public static User insertQueryUserByUnionId(String union_id , String type , Timestamp time) throws SQLException {
        int rows = getStatement(userInsertQuerySql, union_id , type , time.toString()).executeUpdate();
        if(rows == 0){
            log.info("用户：{}，在{}时，注册失败",union_id,time);
            return null;
        }else{
            log.info("用户：{}，在{}时，注册成功",union_id,time);
            return queryUserById(union_id);
        }
    }

    // 插入union_id查询语句，查看用户是否在数据库，存在则查找并包装
    // 如果不存在完成插入数据或注册操作
    public static User queryUserByUnionId(String union_id) throws SQLException {
        if(unionIdIsEmpty(union_id)){
            return insertQueryUserByUnionId(union_id,"0" , MySQLUtil.getTime());
        }
        Timestamp timestamp = MySQLUtil.getTime();
        log.info("用户：{}，在{}，登录成功",union_id,timestamp);
        MySQLUtil.setActivity(union_id , "0");
        return queryUserById(union_id);
    }

    // 封装User对象
    private static User userDataConverter(ResultSet set) {
        User user = new User();

        try {
            while (set.next()) {
                user.setUserId(set.getInt("user_id"));
                user.setUnion_id(set.getString("union_id"));
                user.setActivity(set.getTimestamp("activity_time"));
                user.setType(set.getString("type"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }
    public static boolean unionIdIsEmpty(String union_id) throws SQLException {
        return !getStatement(userIDQuerySql, union_id ,"0").executeQuery().next();
    }
}

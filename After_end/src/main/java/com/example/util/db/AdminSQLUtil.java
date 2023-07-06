package com.example.util.db;

import com.example.entity.Collection;
import com.example.entity.History;
import com.example.entity.User;
import lombok.extern.slf4j.Slf4j;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import static com.example.util.db.MySQLUtil.getStatement;
import static com.example.util.time.GetSystemTime.getTime;

@Slf4j
public class AdminSQLUtil {
    // 通过union_id查找用户是否在数据库中
    private static final String AdminIDQuerySql = "SELECT * FROM tb_user WHERE union_id = ? AND type = 1";

    // 查看是否存在该用户
    private static final String AdminPhoneNumQuerySql = "SELECT * FROM tb_user WHERE union_id = ? AND type = 1";

    // 获取n->n+15行user表
    private static final String AdminGetUserSql = "SELECT * FROM tb_user WHERE type = 0 LIMIT ? OFFSET ?";

    // 更改union_id语句
    private static final String AdminUpdateUserByUnionID = "UPDATE tb_user SET union_id = ? WHERE union_id = ? AND type = 0";

    // 删除用户
    private static final String AdminDeleteUserByUnionID = "DELETE FROM tb_user WHERE union_id = ? AND type = 0";

    // 搜索相似病例历史数据
    private static final String AdminLikeHistoryBySeekSql = "SELECT * FROM tb_history WHERE case_name LIKE ?";

    // 搜索相似病例名或收藏名称的收藏数据
    private static final String AdminLikeCollectBySeekSql = "SELECT * FROM tb_collect WHERE case_name LIKE ? OR collect_name = ?";

    // 搜索相似用户
    private static final String AdminLikeUserBySeekSql = "SELECT * FROM tb_user WHERE union_id LIKE ?";


    // 检查admin是否在数据库
    public static boolean isEmptyAdminByPhoneNum(String phoneNum) throws SQLException {
        return getStatement(AdminPhoneNumQuerySql,phoneNum).executeQuery().next();
    }

    // 插验证密码
    public static boolean checkAdminCheck(String phoneNum , String passWd) throws SQLException {

        return checkAdminPassWd(getAdminSqlPassWd(phoneNum), passWd);
    }

    // 登录时更新admin时间,写入日志
    public static boolean updateAdminTime(String phoneNum) throws SQLException {
        Timestamp timestamp = getTime();
        if(MySQLUtil.setActivity(phoneNum, "1")){
            log.info("admin用户：{}，在{}，登录成功",phoneNum,timestamp);
            return true;
        } else {
            log.info("admin用户：{}，在{}，登录失败",phoneNum,timestamp);
            return false;
        }
    }

    // 判断admin密码
    private static boolean checkAdminPassWd(String sqlPassWd, String inputPassWd){
        return sqlPassWd.equals(inputPassWd);
    }

    // 获取数据库中admin密码
    private static String getAdminSqlPassWd(String phoneNum) throws SQLException {
        ResultSet set = getStatement(AdminIDQuerySql, phoneNum).executeQuery();
        set.next();
        return set.getString("passWd");
    }

    // 封装用户对象集合
    public static ArrayList<User> getUserData(String num) throws SQLException {
        return MySQLUtil.userDataConverter(MySQLUtil.getUserData(AdminGetUserSql,num).executeQuery());
    }

    // 更改原有union_id
    public static boolean toUpdateByUnionID(String union_id , String newID) throws SQLException {
        if(getStatement(AdminUpdateUserByUnionID,newID,union_id).executeUpdate() > 0){
            log.info("管理员在{}时，将用户union_id，由{}改为{}",getTime(),union_id,newID);
            return true;
        }
        return false;
    }

    // 删除用户
    public static boolean toDeleteByUnionID(String union_id) throws SQLException {
        if(getStatement(AdminDeleteUserByUnionID,union_id).executeUpdate() > 0){
            log.info("管理员在{}时，删除用户{}",getTime(),union_id);
            return true;
        }
        return false;
    }

    // 搜索历史表相似病例
    public static ArrayList<History> likeHistoryBySeekStatement(String seekStatement) throws SQLException {
        return MySQLUtil.getHistory(getStatement(AdminLikeHistoryBySeekSql,"%"+seekStatement+"%").executeQuery());
    }

    // 搜索收藏表与字段相似数据
    public static ArrayList<Collection> likeCollectBySeekStatement(String seekStatement) throws SQLException {
        return MySQLUtil.getCollection(getStatement(AdminLikeCollectBySeekSql,getSeek(seekStatement),getSeek(seekStatement))
                .executeQuery());
    }

    // 搜索相似用户
    public static ArrayList<User> likeUserBySeekStatement(String seekStatement) throws SQLException {
        System.out.println(getSeek(seekStatement));
        return MySQLUtil.userDataConverter(getStatement(AdminLikeUserBySeekSql,getSeek(seekStatement))
                .executeQuery());
    }

    private static String getSeek(String seekStatement){
        return "%"+seekStatement+"%";
    }

    /* 弃用函数
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
     */
}

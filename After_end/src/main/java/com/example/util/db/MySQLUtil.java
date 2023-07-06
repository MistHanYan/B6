package com.example.util.db;

import com.example.entity.Collection;
import com.example.entity.History;
import com.example.entity.User;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;

import static com.example.util.time.GetSystemTime.getTime;

@Slf4j
public final class MySQLUtil{

    // 创建接口类
    private static final Connection connection;

    // 时间更新语句
    private static final String upDatedTimeSql = "UPDATE miaow_b6.tb_user SET activity_time = ? WHERE union_id = ? AND type = ?";

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

    // 更新用户登录时间
    public static boolean setActivity(String union_id , String type) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(upDatedTimeSql);
        Timestamp time = getTime();
        preparedStatement.setTimestamp(1,time);
        preparedStatement.setString(2,union_id);
        preparedStatement.setString(3,type);
        int row = preparedStatement.executeUpdate();
        if(row > 0){
            log.info("用户:{},在{}时，时间更新成功",union_id,time);
            return true;
        }else{
            log.error("用户：{}，在{}时，时间更新失败",union_id,time);
            return false;
        }
    }

    // 组合含有int类型的Sql查询语句（用于收藏，历史查询）
    public static PreparedStatement showDataInInt(String sql , String union_id , int num , int end){
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, union_id);
            statement.setInt(2,end);
            statement.setInt(3,end-num);
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

    // 封装删除收藏语句
    public static PreparedStatement getDeleteCollectSql(String sql, String union_id , String projectID){
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1,union_id);
            statement.setInt(2, Integer.parseInt(projectID));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return statement;
    }

    // 删除时间超过3天的历史数据
    public static int timedDelete(String timedDeleteSql){
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(timedDeleteSql);
            return statement.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    // 封装查询用户集合语句，限定行数
    public static PreparedStatement getUserData(String sql, String num){
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, 15);
            statement.setInt(2, Integer.parseInt(num)-15);
            return statement;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    // 封装查找到的10位历史对象，并添加到ArrayList数组返回
    public static ArrayList<History> getHistory(ResultSet set) throws SQLException {

        ArrayList<History> histories = new ArrayList<>();

        while (set.next()){
            History history = new History();
            history.setUnion_id("union_id");
            history.setUrl(set.getString("img_url"));
            history.setCase(set.getString("case_name"));
            history.setDate(set.getTimestamp("time"));
            histories.add(history);
        }
        return histories;
    }

    // 添加历史记录
    public static boolean addHistory(History history,String sql) throws SQLException {
        return outcome(getInsertSql(sql,
                getTime(),
                history.getUnion_id(),
                history.getCase(),
                history.getUrl()).executeUpdate());
    }

    // 判断供用户收藏名是否以存在数据库
    public static boolean collectIsEmpty(String sql , String union_id,String collectName) throws SQLException {
        return getStatement(sql,union_id,collectName).executeQuery().next();
    }

    // 添加收藏
    public static boolean addCollect(String sql , Collection collection) throws SQLException {
        return outcome(MySQLUtil.getInsertSql(sql,
                getTime(),
                collection.getCollectName(),
                collection.getUnion_id(),
                collection.getCaseName(),
                collection.getUrl()).executeUpdate());
    }

    // 插入数据后结果（历史，收藏）
    public static boolean outcome(int row){
        return row > 0;
    }

    // 封装查找出来的收藏对象到列表
    public static ArrayList<Collection> getCollection(ResultSet set) throws SQLException {

        ArrayList<Collection> collections = new ArrayList<>();

        while (set.next()){
            Collection collection = new Collection();
            collection.setCollectName(set.getString("collect_name"));
            collection.setProjectID(String.valueOf(set.getInt("indexID")));
            collection.setUrl(set.getString("img_url"));
            collection.setCaseName(set.getString("case_name"));
            collection.setDate(set.getTimestamp("time"));
            collections.add(collection);
        }
        return collections;
    }

    // 封装User对象
    public static ArrayList<User> userDataConverter(ResultSet set) {
        ArrayList<User> users = new ArrayList<>();
        try {
            while (set.next()) {
                User user = new User();
                user.setUserId(set.getInt("user_id"));
                user.setUnion_id(set.getString("union_id"));
                user.setActivity(set.getTimestamp("activity_time"));
                user.setType(set.getString("type"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }
}

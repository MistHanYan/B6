package com.example.util.db;

import com.example.entity.Collection;
import com.example.entity.History;
import com.example.entity.User;
import lombok.extern.slf4j.Slf4j;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import static com.example.util.db.MySQLUtil.*;

@Slf4j
public class UserSQLUtil {
    // union_id查询语句,查找用户
    private static final String userIDQuerySql = "SELECT * FROM tb_user WHERE union_id = ? AND type = ?";

    // 插入数据库数据
    private static final String userInsertQuerySql = "INSERT INTO tb_user (union_id, type , activity_time) VALUES (?,?,?)";

    // 以union_id查找收藏表n列后10行数据
    private static final String userCollectQuerySql = "SELECT * FROM tb_collect WHERE union_id = ? LIMIT ? OFFSET ?";

    // 以union_id查找历史表n列后10行数据
    private static final String userHistoryQuerySql = "SELECT * FROM tb_history WHERE union_id = ? LIMIT ? OFFSET ?";

    // 插入历史识别数据
    private static final String userAddHistorySql = "INSERT INTO tb_history (time,union_id,case_name,img_url) VALUES (?,?,?,?)";

    // 插入收藏数据
    private static final String userAddCollectSql = "INSERT INTO tb_collect (time,collect_name,union_id,case_name,img_url) VALUES (?,?,?,?,?)";

    // 使用模糊字段查询收藏数据
    private static final String userSeekCollectSql = "SELECT * FROM tb_collect WHERE union_id = ? AND (collect_name LIKE ? OR case_name LIKE ?)";

    // 删除收藏语句
    private static final String userDeleteCollect = "DELETE FROM tb_collect WHERE union_id = ? AND indexID = ?";

    // 查找用户收藏，查该用户添加收藏时，其设置的项目名是否存在；
    private static final String userCollectName = "SELECT * FROM tb_collect WHERE union_id = ? AND collect_name = ?";

    // 插入id查询语句，并返回封装的User对象
    public static User queryUserById(String union_id) throws SQLException {
        return userDataConverter(getStatement(userIDQuerySql, union_id , "0").executeQuery());
    }

    // 向数据库插入数据
    public static User insertQueryUserByUnionId(String union_id , String type , Timestamp time) throws SQLException {
        int rows = getStatement(userInsertQuerySql, union_id , type , time.toString()).executeUpdate();
        if(rows > 0){
            log.info("用户：{}，在{}时，注册成功",union_id,time);
            return queryUserById(union_id);
        }else{
            log.info("用户：{}，在{}时，注册失败",union_id,time);
            return null;
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

    // 通过union_id判断用户是否存在与数据库
    public static boolean unionIdIsEmpty(String union_id) throws SQLException {
        return !getStatement(userIDQuerySql, union_id ,"0").executeQuery().next();
    }

    // 插入从n行开始的数据
    public static ArrayList<Collection> queryUserCollectById(String union_id , int num) throws SQLException {
        return getCollection(MySQLUtil.showDataInInt(userCollectQuerySql,union_id , num).executeQuery());
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

    // 返回10行从n行开始的后十位历史对象
    public static ArrayList<History> queryUserHistoryById(String union_id , int num) throws SQLException {
        return getHistory(MySQLUtil.showDataInInt(userHistoryQuerySql,union_id , num).executeQuery());
    }

    // 封装查找到的10位历史对象，并添加到ArrayList数组返回
    public static ArrayList<History> getHistory(ResultSet set) throws SQLException {

        ArrayList<History> histories = new ArrayList<>();

        while (set.next()){
            History history = new History();
            history.setUrl(set.getString("img_url"));
            history.setCase(set.getString("case_name"));
            history.setDate(set.getTimestamp("time"));
            histories.add(history);
        }
        return histories;
    }

    // 添加历史记录
    public static boolean addHistory(History history) throws SQLException {
        // 执行插入操作
        if(outcome(MySQLUtil.getInsertSql(userAddHistorySql,
                getTime(),
                history.getUnion_id(),
                history.getCase(),
                history.getUrl()).executeUpdate())){
            setLog(history.getUnion_id(),getTime().toString(),"历史记录","");
            return true;
        }
        return false;
    }

    // 在数据库中，没有出现，该用户收藏项目名称与本次插入时项目名称冲突时，插入收藏对象
    public static boolean addCollect(Collection collection) throws SQLException {
        if(!getStatement(userCollectName,collection.getUnion_id(),collection.getCollectName()).executeQuery().next()){
            // 执行插入操作
            if(outcome(MySQLUtil.getInsertSql(userAddCollectSql,
                    getTime(),
                    collection.getCollectName(),
                    collection.getUnion_id(),
                    collection.getCaseName(),
                    collection.getUrl()).executeUpdate())){
                setLog(collection.getUnion_id(), getTime().toString(),"收藏", collection.getCollectName());
                return true;
            }else {
                return false;
            }
        }
        return false;
    }

    // 插入数据后结果（历史，收藏）
    public static boolean outcome(int row){
        return row > 0;
    }

    // 收藏，历史记录添加后生成日志
    public static void setLog(String... statement){
        log.info("用户：{}，在{}时，{}成功，{}",
                statement[0],
                statement[1],
                statement[2],
                statement[3]);
    }

    // 执行搜索语句后结构封装
    public static ArrayList<Collection> seekCollectOutCome(String union_id , String seekStatement) throws SQLException {
        String seek = "%"+seekStatement+"%";
        log.info("用户：{}，在{}时，进行了一次收藏查找, 查找字段{} ",union_id,getTime(),seekStatement);
        return getCollection(getStatement(userSeekCollectSql,union_id,seek,seek).executeQuery());
    }

    // 删除某个收藏
    public static boolean deleteProject(String union_id,String projectID) throws SQLException {
        if(MySQLUtil.getDeleteCollectSql(userDeleteCollect,union_id,projectID).executeUpdate() > 0){
            log.info("用户：{}，在{}时，成功进行了删除收藏操作，收藏项目{}",union_id,getTime(),projectID);
            return true;
        }else {
            log.info("用户：{}，在{}时，进行删除收藏操作失败，收藏项目{}",union_id,getTime(),projectID);
            return false;
        }
    }
}

package com.example.service;

import com.example.entity.Collection;
import com.example.entity.History;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserService {
    //User getUserById(Integer id);

    //添加用户
    boolean addUser(String union_id) throws SQLException;

    // 登陆时更新时间
    boolean updateLogInOfTime(String union_id) throws SQLException;

    // 返回指定数量收藏集合
    ArrayList<Collection> getUserCollectByUnionId(String union_id , String num ,String end);

    // 检查用户是否存在
    boolean checkUnionId(String union_id);

    // 返回指定数量历史集合
    ArrayList<History> getUserHistoryByUnionId(String union_id , String num ,String end);

    // 添加历史记录
    boolean addUserHistory(History history);

    // 添加收藏记录
    boolean addUserCollect(Collection collection);

    // 返回指定数量收藏记录
    ArrayList<Collection> seekCollect(String seekStatement , String union_id);

    // 删除用户
    boolean deleteUserProject(String union_id, String projectID);

    String recognition(String imgUrl);
}

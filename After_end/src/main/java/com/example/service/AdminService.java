package com.example.service;

import com.example.entity.Collection;
import com.example.entity.History;
import com.example.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;

public interface AdminService {
    // 返回所有用户
    ArrayList<User> getAllUser(String num) throws SQLException;

    // 检查登录账号密码
    boolean checkAdmin(String phoneNum, String passWd) throws SQLException;

    boolean upDateAdminLogInTime(String phoneNum) throws SQLException;
    // 检查用户是否存在
    boolean adminIsEmpty(String phoneNum) throws SQLException;


    // 查找某一用户的历史记录
    ArrayList<History> getUserHistoryByUnionID(String union_id , String num , String end) throws SQLException;

    // 查找某一用户收藏记录
    ArrayList<Collection> getUserCollectByUnionID(String union_id , String num , String end) throws SQLException;

    // 根据搜索关键子查询相似字段用户
    ArrayList<User> getLikeUserSeekStatement(String seekStatement) throws SQLException;

    // 根据搜索关键子查询相似字段收藏记录
    ArrayList<Collection> getLikeCollectBySeekStatement(String seekStatement) throws SQLException;

    // 根据搜索关键子查询相似字段历史记录
    ArrayList<History> getLikeHistoryBySeekStatement(String seekStatement) throws SQLException;

    // 更改某一用户union_id
    boolean updateUserByUnionId(String union_id,String newID) throws SQLException;

    // 删除某一用户
    boolean deleteUserByUnionId(String union_id) throws SQLException;
}

package com.example.service.impl;

import com.example.entity.Collection;
import com.example.entity.History;
import com.example.entity.User;
import com.example.service.AdminService;
import com.example.util.db.AdminSQLUtil;
import com.example.util.db.UserSQLUtil;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;

@Service
public class AdminServiceImpl implements AdminService {

    @Override
    public ArrayList<User> getAllUser(String num) throws SQLException {
        return AdminSQLUtil.getUserData(num);
    }

    @Override
    public boolean checkAdmin(String phoneNum, String passWd) throws SQLException {
        return AdminSQLUtil.checkAdminCheck(phoneNum,passWd);
    }

    @Override
    public boolean upDateAdminLogInTime(String phoneNum) throws SQLException {
        return AdminSQLUtil.updateAdminTime(phoneNum);
    }

    @Override
    public boolean adminIsEmpty(String phoneNum) throws SQLException {
        return AdminSQLUtil.isEmptyAdminByPhoneNum(phoneNum);
    }

    @Override
    public ArrayList<History> getUserHistoryByUnionID(String union_id, String num,String end) throws SQLException {
        return UserSQLUtil.queryUserHistoryById(union_id, Integer.parseInt(num),Integer.parseInt(end));
    }

    @Override
    public ArrayList<Collection> getUserCollectByUnionID(String union_id , String num,String end) throws SQLException {
        return UserSQLUtil.queryUserCollectById(union_id, Integer.parseInt(num),Integer.parseInt(end));
    }

    @Override
    public ArrayList<User> getLikeUserSeekStatement(String seekStatement) throws SQLException {
        return AdminSQLUtil.likeUserBySeekStatement(seekStatement);
    }

    @Override
    public ArrayList<Collection> getLikeCollectBySeekStatement(String seekStatement) throws SQLException {
        return AdminSQLUtil.likeCollectBySeekStatement(seekStatement);
    }

    @Override
    public ArrayList<History> getLikeHistoryBySeekStatement(String seekStatement) throws SQLException {
        return AdminSQLUtil.likeHistoryBySeekStatement(seekStatement);
    }

    @Override
    public boolean updateUserByUnionId(String union_id ,String newID) throws SQLException {
        return AdminSQLUtil.toUpdateByUnionID(union_id,newID);
    }

    @Override
    public boolean deleteUserByUnionId(String union_id) throws SQLException {
        return AdminSQLUtil.toDeleteByUnionID(union_id);
    }
}

package com.example.service.impl;

import com.example.entity.Collection;
import com.example.entity.History;
import com.example.entity.User;
import com.example.service.UserService;
import com.example.util.db.UserSQLUtil;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public User getUserByUnionId(String union_id) {
        try {
            return UserSQLUtil.queryUserByUnionId(union_id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<Collection> getUserCollectByUnionId(String union_id, int num) {
        try {
            return UserSQLUtil.queryUserCollectById(union_id,num);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean checkUnionId(String union_id) {
        try {
            return !UserSQLUtil.unionIdIsEmpty(union_id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<History> getUserHistoryByUnionId(String union_id, int num) {
        try {
            return UserSQLUtil.queryUserHistoryById(union_id,num);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean addUserHistory(History history) {
        try {
            return UserSQLUtil.addHistory(history);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean addUserCollect(Collection collection) {
        try {
            return UserSQLUtil.addCollect(collection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<Collection> seekCollect(String seekStatement , String union_id) {
        try {
            return UserSQLUtil.seekCollectOutCome(union_id,seekStatement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteUserProject(String union_id, String projectID) {
        try {
            return UserSQLUtil.deleteProject(union_id,projectID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

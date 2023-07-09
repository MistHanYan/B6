package com.example.service.impl;

import com.example.entity.Collection;
import com.example.entity.History;
import com.example.service.UserService;
import com.example.util.db.MySQLUtil;
import com.example.util.db.UserSQLUtil;
import com.example.util.jwt_token.Union_id;
import com.example.util.recognition.RecognitionRep;
import com.example.util.save.SaveImg;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public String getResponse(String js_code) {
        return Union_id.getUnionId(js_code);
    }

    @Override
    public boolean addUser(String union_id) throws SQLException {
        return UserSQLUtil.queryUserByUnionId(union_id);
    }

    @Override
    public boolean updateLogInOfTime(String union_id) throws SQLException {
        return MySQLUtil.setActivity(union_id,"0");
    }

    @Override
    public ArrayList<Collection> getUserCollectByUnionId(String union_id, String num ,String end) {
        try {
            return UserSQLUtil.queryUserCollectById(union_id,Integer.parseInt(num),Integer.parseInt(end));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public boolean checkUnionId(String union_id) {
        try {
            return UserSQLUtil.unionIdIsEmpty(union_id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<History> getUserHistoryByUnionId(String union_id, String num ,String end) {
        try {
            return UserSQLUtil.queryUserHistoryById(union_id,Integer.parseInt(num),Integer.parseInt(end));
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

    @Override
    public String recognition(String imgUrl) {
        return RecognitionRep.discernOutcome(imgUrl);
    }

    @Override
    public String getImgSavedPath(String imgName, MultipartFile img) {
        return SaveImg.saveImg(imgName,img);
    }
}

package com.example.service;

import com.example.entity.Collection;
import com.example.entity.History;
import com.example.entity.User;

import java.util.ArrayList;

public interface UserService {
    //User getUserById(Integer id);
    User getUserByUnionId(String union_id);
    ArrayList<Collection> getUserCollectByUnionId(String union_id , int num);

    boolean checkUnionId(String union_id);

    ArrayList<History> getUserHistoryByUnionId(String union_id , int num);

    boolean addUserHistory(History history);

    boolean addUserCollect(Collection collection);

    ArrayList<Collection> seekCollect(String seekStatement , String union_id);
}

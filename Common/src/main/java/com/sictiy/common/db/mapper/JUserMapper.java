package com.sictiy.common.db.mapper;

import java.util.List;
import com.sictiy.common.db.pojo.JUserInfo;

public interface JUserMapper
{
    /**
    * select JUser by userId
    * @param userId
    */
    JUserInfo queryJUserByUserId(long userId);

    /**
    * select JUser by userName
    * @param userName
    */
    JUserInfo queryJUserByUserName(String userName);

    /**
    * select all from table
    */
    List<JUserInfo> queryJUserAll();

    /**
    * update JUser
    * @param juserInfo
    */
    void updateJUser(JUserInfo juserInfo);

    /**
    * insert 
    * @param juserInfo
    */
    void insertJUser(JUserInfo juserInfo);
}
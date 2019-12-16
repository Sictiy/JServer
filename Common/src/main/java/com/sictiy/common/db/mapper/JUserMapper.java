package com.sictiy.common.db.mapper;

import java.util.List;
import com.sictiy.common.db.pojo.JUserInfo;
import com.sictiy.common.db.MapperInterface;

public interface JUserMapper extends MapperInterface<JUserInfo>
{
    /**
    * select JUser by userId
    * @param userId ""
    */
    JUserInfo queryByUserId(long userId);

    /**
    * select JUser by userName
    * @param userName ""
    */
    JUserInfo queryByUserName(String userName);

    /**
    * select all from table
    */
    List<JUserInfo> queryAll();

    /**
    * update JUser
    * @param juserInfo ""
    */
    void update(JUserInfo juserInfo);

    /**
    * insert 
    * @param juserInfo ""
    */
    void insert(JUserInfo juserInfo);
}
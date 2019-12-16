package com.sictiy.common.db.mapper;

import java.util.List;
import com.sictiy.common.db.pojo.JLevelInfo;
import com.sictiy.common.db.MapperInterface;

public interface JLevelMapper extends MapperInterface<JLevelInfo>
{
    /**
    * select JLevel by id
    * @param id ""
    */
    JLevelInfo queryById(long id);

    /**
    * select JLevel list by userId
    * @param userId ""
    */
    List<JLevelInfo> queryListByUserId(long userId);

    /**
    * select all from table
    */
    List<JLevelInfo> queryAll();

    /**
    * update JLevel
    * @param jlevelInfo ""
    */
    void update(JLevelInfo jlevelInfo);

    /**
    * insert 
    * @param jlevelInfo ""
    */
    void insert(JLevelInfo jlevelInfo);
}
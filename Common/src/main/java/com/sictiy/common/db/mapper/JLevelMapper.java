package com.sictiy.common.db.mapper;

import java.util.List;
import com.sictiy.common.db.pojo.JLevelInfo;

public interface JLevelMapper
{
    /**
    * select JLevel by id
    * @param id
    */
    JLevelInfo queryJLevelById(long id);

    /**
    * select all from table
    */
    List<JLevelInfo> queryJLevelAll();

    /**
    * update JLevel
    * @param jlevelInfo
    */
    void updateJLevel(JLevelInfo jlevelInfo);

    /**
    * insert 
    * @param jlevelInfo
    */
    void insertJLevel(JLevelInfo jlevelInfo);
}
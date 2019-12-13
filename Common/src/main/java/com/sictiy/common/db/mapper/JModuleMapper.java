package com.sictiy.common.db.mapper;

import java.util.List;
import com.sictiy.common.db.pojo.JModuleInfo;

public interface JModuleMapper
{
    /**
    * select JModule by id
    * @param id
    */
    JModuleInfo queryJModuleById(long id);

    /**
    * select JModule list by userId
    * @param userId
    */
    List<JModuleInfo> queryJModuleListByUserId(long userId);

    /**
    * select all from table
    */
    List<JModuleInfo> queryJModuleAll();

    /**
    * update JModule
    * @param jmoduleInfo
    */
    void updateJModule(JModuleInfo jmoduleInfo);

    /**
    * insert 
    * @param jmoduleInfo
    */
    void insertJModule(JModuleInfo jmoduleInfo);
}
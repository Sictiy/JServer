package com.sictiy.common.db.mapper;

import java.util.List;
import com.sictiy.common.db.pojo.JModuleInfo;
import com.sictiy.common.db.MapperInterface;

public interface JModuleMapper extends MapperInterface<JModuleInfo>
{
    /**
    * select JModule by id
    * @param id ""
    */
    JModuleInfo queryById(long id);

    /**
    * select JModule list by userId
    * @param userId ""
    */
    List<JModuleInfo> queryListByUserId(long userId);

    /**
    * select all from table
    */
    List<JModuleInfo> queryAll();

    /**
    * update JModule
    * @param jmoduleInfo ""
    */
    void update(JModuleInfo jmoduleInfo);

    /**
    * insert 
    * @param jmoduleInfo ""
    */
    void insert(JModuleInfo jmoduleInfo);
}
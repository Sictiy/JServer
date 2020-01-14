package com.sictiy.common.db.mapper;

import java.util.List;
import com.sictiy.common.db.pojo.JItemInfo;
import com.sictiy.common.db.MapperInterface;

public interface JItemMapper extends MapperInterface<JItemInfo>
{
    /**
    * select JItem by id
    * @param id ""
    */
    JItemInfo queryById(long id);

    /**
    * select JItem list by userId
    * @param userId ""
    */
    List<JItemInfo> queryListByUserId(long userId);

    /**
    * select all from table
    */
    List<JItemInfo> queryAll();

    /**
    * update JItem
    * @param jitemInfo ""
    */
    void update(JItemInfo jitemInfo);

    /**
    * insert 
    * @param jitemInfo ""
    */
    void insert(JItemInfo jitemInfo);
}
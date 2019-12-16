package com.sictiy.common.db.mapper;

import java.util.List;
import com.sictiy.common.db.pojo.JUniqueInfo;
import com.sictiy.common.db.MapperInterface;

public interface JUniqueMapper extends MapperInterface<JUniqueInfo>
{
    /**
    * select JUnique by id
    * @param id ""
    */
    JUniqueInfo queryById(int id);

    /**
    * select all from table
    */
    List<JUniqueInfo> queryAll();

    /**
    * update JUnique
    * @param juniqueInfo ""
    */
    void update(JUniqueInfo juniqueInfo);

    /**
    * insert 
    * @param juniqueInfo ""
    */
    void insert(JUniqueInfo juniqueInfo);
}
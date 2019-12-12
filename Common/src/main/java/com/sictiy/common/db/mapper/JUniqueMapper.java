package com.sictiy.common.db.mapper;

import java.util.List;
import com.sictiy.common.db.pojo.JUniqueInfo;

public interface JUniqueMapper
{
    /**
    * select JUnique by id
    * @param id
    */
    JUniqueInfo queryJUniqueById(int id);

    /**
    * select all from table
    */
    List<JUniqueInfo> queryJUniqueAll();

    /**
    * update JUnique
    * @param juniqueInfo
    */
    void updateJUnique(JUniqueInfo juniqueInfo);

    /**
    * insert 
    * @param juniqueInfo
    */
    void insertJUnique(JUniqueInfo juniqueInfo);
}
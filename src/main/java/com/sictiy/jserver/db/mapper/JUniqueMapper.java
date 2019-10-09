package com.sictiy.jserver.db.mapper;

import java.util.List;
import com.sictiy.jserver.db.pojo.JUniqueInfo;

public interface JUniqueMapper
{
    /**
    * select JUnique by type
    * @param type
    */
    JUniqueInfo queryJUniqueByType(int type);

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
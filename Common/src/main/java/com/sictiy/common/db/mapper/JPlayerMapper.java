package com.sictiy.common.db.mapper;

import java.util.List;
import com.sictiy.common.db.pojo.JPlayerInfo;
import com.sictiy.common.db.MapperInterface;

public interface JPlayerMapper extends MapperInterface<JPlayerInfo>
{
    /**
    * select JPlayer by userId
    * @param userId ""
    */
    JPlayerInfo queryByUserId(long userId);

    /**
    * select all from table
    */
    List<JPlayerInfo> queryAll();

    /**
    * update JPlayer
    * @param jplayerInfo ""
    */
    void update(JPlayerInfo jplayerInfo);

    /**
    * insert 
    * @param jplayerInfo ""
    */
    void insert(JPlayerInfo jplayerInfo);
}
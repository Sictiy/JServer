package com.sictiy.common.db.mapper;

import java.util.List;
import com.sictiy.common.db.pojo.JPlayerInfo;

public interface JPlayerMapper
{
    /**
    * select JPlayer by userId
    * @param userId
    */
    JPlayerInfo queryJPlayerByUserId(long userId);

    /**
    * select all from table
    */
    List<JPlayerInfo> queryJPlayerAll();

    /**
    * update JPlayer
    * @param jplayerInfo
    */
    void updateJPlayer(JPlayerInfo jplayerInfo);

    /**
    * insert 
    * @param jplayerInfo
    */
    void insertJPlayer(JPlayerInfo jplayerInfo);
}
package com.sictiy.jserver.db.mapper;

import java.util.List;

import com.sictiy.jserver.db.pojo.JUserInfo;

public interface JUserMapper
{
    /**
     * 根据Id查询用户信息
     * @param userId
     */
    JUserInfo queryUserById(Long userId);

    /**
     * 根据name查用户信息
     *
     * @param name
     * @return com.sictiy.jserver.db.pojo.JUserInfo
     **/
    JUserInfo queryUserByName(String name);

    /**
     * 查询所有用户信息
     */
    List<JUserInfo> queryUserAll();

    /**
     * 根据id更新用户信息
     * @param user
     */
    void updateUser(JUserInfo user);

    /**
     * 新增用户信息
     * @param user
     */
    void insertUser(JUserInfo user);
}

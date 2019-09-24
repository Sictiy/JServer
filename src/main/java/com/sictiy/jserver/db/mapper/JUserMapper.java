package com.sictiy.jserver.db.mapper;

import java.util.List;

import com.sictiy.jserver.db.pojo.JUserInfo;

public interface JUserMapper
{
    /**
     * 根据Id查询用户信息
     * @param userId
     */
    public JUserInfo queryUserById(Long userId);

    /**
     * 查询所有用户信息
     */
    public List<JUserInfo> queryUserAll();

    /**
     * 根据id更新用户信息
     * @param user
     */
    public void updateUser(JUserInfo user);

    /**
     * 新增用户信息
     * @param user
     */
    public void insertUser(JUserInfo user);
}

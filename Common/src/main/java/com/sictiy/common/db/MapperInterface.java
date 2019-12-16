package com.sictiy.common.db;

import java.util.List;

/**
 * mapper接口
 *
 * @author sictiy.xu
 * @version 2019/12/16 20:23
 **/
public interface MapperInterface<T>
{
    // 查询所有
    List<T> queryAll();

    // 插入
    void insert(T t);

    // 更新
    void update(T t);
}

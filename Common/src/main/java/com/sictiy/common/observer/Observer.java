package com.sictiy.common.observer;

/**
 * 订阅者接口
 *
 * @author sictiy.xu
 * @version 2019/12/13 16:55
 **/
public interface Observer
{
    void update(Object... objects);
}

package com.sictiy.common.observer;

/**
 * 主题接口
 *
 * @author sictiy.xu
 * @version 2019/12/13 16:52
 **/
public interface Subject
{
    void notifyEvent(int eventType, Object... objects);
}

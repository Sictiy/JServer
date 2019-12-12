package com.sictiy.common.net;

/**
 * @author 10460
 * @version 2019/10/19 11:21
 **/
public interface ICmdHandler
{
    void handlerCmdMessage(AbstractConnect abstractConnect, JMessage message);
}

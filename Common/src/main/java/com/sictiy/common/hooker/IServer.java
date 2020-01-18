package com.sictiy.common.hooker;

import com.sictiy.common.util.LogUtil;

/**
 * @author sictiy.xu
 * @version 2019/10/11 12:28
 **/
public interface IServer
{
    void onShutDown();

    void start();

    default void doRun(Runnable runnable, String log)
    {
        runnable.run();
        LogUtil.info("{} complete", log);
    }

}

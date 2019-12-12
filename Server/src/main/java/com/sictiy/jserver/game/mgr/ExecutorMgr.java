package com.sictiy.jserver.game.mgr;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.sictiy.common.entry.executor.JExecutor;

/**
 * 线程池管理
 *
 * @author sictiy.xu
 * @version 2019/12/12 15:31
 **/
public class ExecutorMgr
{
    private static Map<String, JExecutor> allExecutor;

    public static final String CMD_EXECUTOR = "cmdTaskExecutor";
    public static final String COMMON_EXECUTOR = "commonTaskExecutor";
    public static final String PLAYER_EXECUTOR = "commonTaskExecutor";

    public static boolean init()
    {
        allExecutor = new ConcurrentHashMap<>();
        allExecutor.put(CMD_EXECUTOR, new JExecutor(CMD_EXECUTOR));
        allExecutor.put(COMMON_EXECUTOR, new JExecutor(COMMON_EXECUTOR));
        allExecutor.put(PLAYER_EXECUTOR, new JExecutor(PLAYER_EXECUTOR));
        return true;
    }

    public static JExecutor getJExecutor(String name)
    {
        if (allExecutor.containsKey(name))
        {
            return allExecutor.get(name);
        }
        return allExecutor.get(COMMON_EXECUTOR);
    }
}

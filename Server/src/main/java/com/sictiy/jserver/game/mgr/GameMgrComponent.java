package com.sictiy.jserver.game.mgr;

import com.sictiy.processor.single.SingleInstance;

/**
 * @author sictiy.xu
 * @version 2019/09/25 15:07
 **/
@SingleInstance
public class GameMgrComponent
{
    public boolean init()
    {
        if (!CmdTaskMgr.init())
        {
            return false;
        }
        if (!JPlayerMgr.init())
        {
            return false;
        }
        if (!LoginTaskMgr.init())
        {
            return false;
        }
        if (!RunnableTaskMgr.init())
        {
            return false;
        }
        if (!UniqueMgr.init())
        {
            return false;
        }
        return true;
    }

    public void stop()
    {
        JPlayerMgr.stop();
    }
}

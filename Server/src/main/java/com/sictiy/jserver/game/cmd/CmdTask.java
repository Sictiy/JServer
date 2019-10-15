package com.sictiy.jserver.game.cmd;

import lombok.Getter;
import lombok.Setter;

import com.sictiy.common.net.AbstractConnect;
import com.sictiy.common.net.JMessage;
import com.sictiy.common.net.JServerConnect;
import com.sictiy.common.util.LogUtil;

/**
 * @author sictiy.xu
 * @version 2019/09/25 15:02
 **/
@Setter
@Getter
public class CmdTask implements Runnable
{
    AbstractCmd cmd;
    AbstractConnect connect;
    JMessage message;

    @Override
    public void run()
    {
        long startTime = System.currentTimeMillis();
        try
        {
            cmd.execute((JServerConnect) connect, message);
        }
        catch (Exception e)
        {
            LogUtil.exception(e);
        }
        LogUtil.info("cmd endRun time:{}", System.currentTimeMillis() - startTime);
    }
}

package com.sictiy.jserver.game.cmd;

import lombok.Getter;
import lombok.Setter;

import com.sictiy.jserver.net.AbstractConnect;
import com.sictiy.jserver.net.JMessage;
import com.sictiy.jserver.util.LogUtil;

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
        LogUtil.info("{}", message);
    }
}

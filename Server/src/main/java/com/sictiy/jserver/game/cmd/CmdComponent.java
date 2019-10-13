package com.sictiy.jserver.game.cmd;

import java.util.HashMap;
import java.util.Map;

import com.sictiy.jserver.entry.annotation.CmdAnnotation;
import com.sictiy.jserver.util.ClassUtil;
import com.sictiy.jserver.util.LogUtil;
import com.sictiy.processor.single.SingleInstance;

/**
 * @author sictiy.xu
 * @version 2019/09/25 15:06
 **/
@SingleInstance
public class CmdComponent
{
    private Map<Short, AbstractCmd> allCmd;

    public boolean init()
    {
        try
        {
            allCmd = new HashMap<>();
            var classes = ClassUtil.getImplClassByAbstractClass(AbstractCmd.class);
            for (var clazz : classes)
            {
                var annotation = clazz.getAnnotation(CmdAnnotation.class);
                if (annotation == null)
                {
                    continue;
                }
                allCmd.put((short) annotation.code(), clazz.getDeclaredConstructor().newInstance());
            }
        }
        catch (Exception e)
        {
            LogUtil.error("", e);
        }
        return true;
    }

    public AbstractCmd getCmdByCode(short code)
    {
        return allCmd.get(code);
    }
}

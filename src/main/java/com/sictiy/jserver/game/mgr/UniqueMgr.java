package com.sictiy.jserver.game.mgr;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.sictiy.jserver.db.DbComponent;
import com.sictiy.jserver.db.mapper.JUserMapper;
import com.sictiy.jserver.entry.type.UniqueType;

/**
 * @author 10460
 * @version 2019/10/04 21:06
 **/
public class UniqueMgr
{
    private static Map<Integer, AtomicInteger> atomicIntegerMap;
    // unique format: ***(type)***(serverId)******(sequence)

    /**
     * @return boolean
     **/
    public static boolean init()
    {
        atomicIntegerMap = new HashMap<>();
        // TODO 暂时用数据库的id表示
        int allUserSize = DbComponent.getMapper(JUserMapper.class).queryUserAll().size();
        atomicIntegerMap.put(UniqueType.USER, new AtomicInteger(allUserSize));
        return true;
    }

    public static long getUnique(int type)
    {
        return getUnique(type, 0);
    }

    public static long getUnique(int type, int serverId)
    {
        int sequence = getSequence(type, serverId);
        return sequence + (long) (serverId * 1000000);
    }

    private static int getSequence(int type, int serverId)
    {
        if (!atomicIntegerMap.containsKey(type))
        {
            atomicIntegerMap.put(type, new AtomicInteger(0));
            return 0;
        }
        return atomicIntegerMap.get(type).addAndGet(1);
    }
}

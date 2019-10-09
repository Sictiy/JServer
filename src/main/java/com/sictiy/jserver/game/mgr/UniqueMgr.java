package com.sictiy.jserver.game.mgr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.sictiy.jserver.db.DbComponent;
import com.sictiy.jserver.db.mapper.JUniqueMapper;
import com.sictiy.jserver.db.pojo.JUniqueInfo;

/**
 * @author 10460
 * @version 2019/10/04 21:06
 **/
public class UniqueMgr
{
    private static final int MAX = 100;

    private static Map<Integer, Map<Integer, UniqueInfo>> atomicIntegerMap;
    // unique format: ***(type)***(serverId)******(sequence)

    /**
     * @return boolean
     **/
    public static boolean init()
    {
        atomicIntegerMap = new HashMap<>();
        List<JUniqueInfo> jUniqueInfos = DbComponent.getMapper(JUniqueMapper.class).queryJUniqueAll();
        jUniqueInfos.forEach(jUniqueInfo -> atomicIntegerMap.computeIfAbsent(jUniqueInfo.getType(),
                k -> new HashMap<>()).computeIfAbsent(jUniqueInfo.getServerId(), k -> new UniqueInfo(jUniqueInfo)));
        return true;
    }


    public static long getUnique(int type)
    {
        return getUnique(type, 101);
    }

    public static long getUnique(int type, int serverId)
    {
        int sequence = getSequence(type, serverId);
        return sequence + serverId * 1000000L + type * 1000000000L;
    }

    private static int getSequence(int type, int serverId)
    {
        return atomicIntegerMap.computeIfAbsent(type, k -> new HashMap<>()).
                computeIfAbsent(serverId, k -> new UniqueInfo(type, serverId)).addAndGet();
    }

    private static class UniqueInfo
    {
        private JUniqueInfo jUniqueInfo;

        private AtomicInteger id;

        UniqueInfo(JUniqueInfo jUniqueInfo)
        {
            this.jUniqueInfo = jUniqueInfo;
            id = new AtomicInteger(jUniqueInfo.getMax());
        }

        UniqueInfo(int type, int serverId)
        {
            jUniqueInfo = new JUniqueInfo();
            jUniqueInfo.setType(type);
            jUniqueInfo.setServerId(serverId);
            jUniqueInfo.setMax(MAX);
            id = new AtomicInteger(0);
            DbComponent.getMapper(JUniqueMapper.class).insertJUnique(jUniqueInfo);
        }

        private int addAndGet()
        {
            int current = id.addAndGet(1);
            if (current >= jUniqueInfo.getMax())
            {
                jUniqueInfo.setMax(jUniqueInfo.getMax() + MAX);
                DbComponent.getMapper(JUniqueMapper.class).updateJUnique(jUniqueInfo);
            }
            return current;
        }
    }
}

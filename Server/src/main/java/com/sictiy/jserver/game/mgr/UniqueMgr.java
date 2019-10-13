package com.sictiy.jserver.game.mgr;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
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

    private static final boolean USE_BIT = true;

    private static Map<Integer, Map<Integer, UniqueInfo>> atomicIntegerMap;
    // unique format: ******(sequence)***(type)***(serverId)

    /**
     * @return boolean
     **/
    public static boolean init()
    {
        atomicIntegerMap = new ConcurrentHashMap<>();
        List<JUniqueInfo> jUniqueInfos = DbComponent.getInstance().getMapper(JUniqueMapper.class).queryJUniqueAll();
        jUniqueInfos.forEach(jUniqueInfo -> atomicIntegerMap.computeIfAbsent(getType(jUniqueInfo.getId()),
                k -> new ConcurrentHashMap<>()).computeIfAbsent(getServerId(jUniqueInfo.getId()), k -> new UniqueInfo(jUniqueInfo)));
        return true;
    }

    private static int getType(int id)
    {
        return id >> 16;
    }

    private static int getServerId(int id)
    {
        return id & 0x00FF;
    }

    private static int getIdByTypeAndServerId(int type, int serverId)
    {
        return type << 16 | serverId & 0x00FF;
    }

    public static long getUnique(int type)
    {
        return getUnique(type, 101);
    }

    public static long getUnique(int type, int serverId)
    {
        int sequence = getSequence(type, serverId);
        if (USE_BIT)
        {
            return ((long) sequence) << 32 | getIdByTypeAndServerId(type, serverId);
        }
        else
        {
            return sequence * 1000000L + type * 1000L + serverId;
        }
    }

    private static int getSequence(int type, int serverId)
    {
        return atomicIntegerMap.computeIfAbsent(type, k -> new ConcurrentHashMap<>()).
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
            jUniqueInfo.setId(getIdByTypeAndServerId(type, serverId));
            jUniqueInfo.setMax(MAX);
            id = new AtomicInteger(0);
            DbComponent.getInstance().getMapper(JUniqueMapper.class).insertJUnique(jUniqueInfo);
        }

        private int addAndGet()
        {
            int current = id.addAndGet(1);
            if (current >= jUniqueInfo.getMax())
            {
                jUniqueInfo.setMax(jUniqueInfo.getMax() + MAX);
                DbComponent.getInstance().getMapper(JUniqueMapper.class).updateJUnique(jUniqueInfo);
            }
            return current;
        }
    }
}

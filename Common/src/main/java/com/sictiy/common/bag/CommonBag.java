package com.sictiy.common.bag;

import java.util.Map;

/**
 * 通用背包
 *
 * @author sictiy.xu
 * @version 2019/12/23 20:19
 **/
public class CommonBag
{
    private Map<Integer, BagItem> allItems;

    public boolean addItem(int itemId, int count, int source)
    {
        return true;
    }

    public int getCountByItemId(int itemId)
    {
        return 0;
    }
}

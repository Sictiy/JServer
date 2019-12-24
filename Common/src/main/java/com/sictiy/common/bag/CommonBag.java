package com.sictiy.common.bag;

import java.util.HashMap;
import java.util.Map;

import com.sictiy.common.entry.type.BagType;

/**
 * 通用背包
 *
 * @author sictiy.xu
 * @version 2019/12/23 20:19
 **/
public class CommonBag
{
    private BagType bagType;

    private Map<Integer, BagItem> allItems;

    CommonBag(BagType bagType)
    {
        this.bagType = bagType;
        allItems = new HashMap<>();
    }

    public boolean addItem(int itemId, int count, int source)
    {
        return true;
    }

    public int getCountByItemId(int itemId)
    {
        return 0;
    }
}

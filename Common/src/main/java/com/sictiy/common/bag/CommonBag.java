package com.sictiy.common.bag;

import java.util.HashMap;
import java.util.Map;

import com.sictiy.common.db.DataOwner;
import com.sictiy.common.entry.type.BagType;

/**
 * 通用背包
 *
 * @author sictiy.xu
 * @version 2019/12/23 20:19
 **/
public class CommonBag
{
    private DataOwner owner;

    private BagType bagType;

    private Map<Integer, BagItem> allItems;

    /**
     * 当前背包空格位
     **/
    private int currentIndex;

    CommonBag(BagType bagType, DataOwner owner)
    {
        this.bagType = bagType;
        this.owner = owner;
        allItems = new HashMap<>();
        currentIndex = 0;
    }

    public boolean addItem(int itemId, int count, int source)
    {

        return true;
    }

    public int findEmptyIndex()
    {
        return findEmptyIndex(1)[0];
    }

    public int[] findEmptyIndex(int n)
    {
        return new int[2];
    }

    public int getCountByItemId(int itemId)
    {
        return 0;
    }
}

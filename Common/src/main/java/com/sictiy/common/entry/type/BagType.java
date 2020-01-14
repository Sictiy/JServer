package com.sictiy.common.entry.type;

/**
 * 背包类型
 *
 * @author sictiy.xu
 * @version 2019/12/24 10:27
 **/
public enum BagType
{
    DEFAULT(0, 99, false),
    MAIN_BAG(1, 99, true);

    private final int value;

    private final int capacity;

    private final boolean show;

    BagType(int value, int capacity, boolean show)
    {
        this.value = value;
        this.capacity = capacity;
        this.show = show;
    }

    public static BagType getBagType(int type)
    {
        for (BagType bagType : BagType.values())
        {
            if (bagType.value == type)
            {
                return bagType;
            }
        }
        return null;
    }
}

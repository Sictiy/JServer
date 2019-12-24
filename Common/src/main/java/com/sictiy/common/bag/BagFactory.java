package com.sictiy.common.bag;

import com.sictiy.common.entry.type.BagType;

/**
 * 背包工厂
 *
 * @author sictiy.xu
 * @version 2019/12/24 10:32
 **/
public class BagFactory
{
    public static CommonBag newBag(int type)
    {
        return new CommonBag(BagType.getBagType(type));
    }
}

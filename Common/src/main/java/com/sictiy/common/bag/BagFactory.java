package com.sictiy.common.bag;

import com.sictiy.common.db.DataOwner;
import com.sictiy.common.entry.type.BagType;

/**
 * 背包工厂
 *
 * @author sictiy.xu
 * @version 2019/12/24 10:32
 **/
public class BagFactory
{
    public static CommonBag newBag(int type, DataOwner owner)
    {
        return new CommonBag(BagType.getBagType(type), owner);
    }
}

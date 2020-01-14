package com.sictiy.common.bag;

import com.sictiy.common.db.DataObject;
import com.sictiy.common.db.DataOwner;
import com.sictiy.common.db.pojo.JItemInfo;
import com.sictiy.common.template.TempComponent;
import com.sictiy.common.template.bean.ItemBean;
import com.sictiy.common.util.LogUtil;

/**
 * 背包物品
 *
 * @author sictiy.xu
 * @version 2019/12/23 20:19
 **/
public class BagItem
{
    private JItemInfo itemInfo;

    private ItemBean itemBean;

    /**
     * 用于已有物品
     *
     * @param itemInfo itemInfo
     **/
    public BagItem(JItemInfo itemInfo)
    {
        this.itemInfo = itemInfo;
        this.itemBean = TempComponent.getInstance().getBeanById(ItemBean.class, itemInfo.getTempId());
        if (itemBean == null)
        {
            LogUtil.error("itemBean is null, id:{}", itemInfo.getTempId());
        }
    }

    /**
     * 新增物品
     *
     * @param itemBean itemBean
     **/
    public BagItem(ItemBean itemBean, DataOwner owner)
    {
        this.itemBean = itemBean;
        this.itemInfo = DataObject.newDataObject(JItemInfo.class, owner);
        initItemInfo();
    }

    /**
     * 初始化新增物品
     **/
    public void initItemInfo()
    {
        itemInfo.setCount(0);
        itemInfo.setExist(true);
        itemInfo.setExpireTime(null);
    }
}

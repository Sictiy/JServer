package com.sictiy.common.db.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;

import com.sictiy.common.db.DataObject;
import com.sictiy.common.entry.annotation.CommomAnnotation;

/**
* 背包物品
*
* generated by tool
* @since: 2020-01-07
*/
@Setter
@Getter
@ToString
@NoArgsConstructor
@CommomAnnotation(str = "u_item")
public class JItemInfo extends DataObject
{
    /**
    * 主键
    */
    long id;

    /**
    * 
    */
    long userId;

    /**
    * 模板id
    */
    int tempId;

    /**
    * 数量
    */
    int count;

    /**
    * 位置
    */
    int index;

    /**
    * 过期时间
    */
    Date expireTime;

    /**
    * 是否存在
    */
    boolean exist;
}
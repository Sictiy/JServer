package com.sictiy.jserver.db.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;

import com.sictiy.jserver.entry.annotation.CommomAnnotation;

/**
* 账户
*
* generated by tool
* @since: 2019-10-09
*/
@Setter
@Getter
@ToString
@CommomAnnotation(str = "u_user")
public class JUserInfo
{
    /**
    * id
    */
    long userId;

    /**
    * 密码
    */
    String password;

    /**
    * 昵称
    */
    String userName;

    /**
    * 创建日期
    */
    Date createDate;
}
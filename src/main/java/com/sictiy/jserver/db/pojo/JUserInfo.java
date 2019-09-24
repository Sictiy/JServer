package com.sictiy.jserver.db.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class JUserInfo
{
    long userId;

    String userName;

    String password;

    Date createDate;
}

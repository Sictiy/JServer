package com.sictiy.jserver.game.player;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.sictiy.jserver.db.pojo.JUserInfo;

/**
 * @author sictiy.xu
 * @version 2019/09/25 14:47
 **/
@Setter
@Getter
@ToString
public class JPlayer
{
    JUserInfo userInfo;
}

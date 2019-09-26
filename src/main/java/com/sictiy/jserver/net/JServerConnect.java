package com.sictiy.jserver.net;

import lombok.Getter;
import lombok.Setter;

import com.sictiy.jserver.game.player.JPlayer;

/**
 * @author sictiy.xu
 * @version 2019/09/26 14:34
 **/
@Setter
@Getter
public class JServerConnect extends AbstractConnect
{
    private JPlayer owner;
}

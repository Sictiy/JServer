package com.sictiy.jserver.game.player.module;

import lombok.Getter;
import lombok.Setter;

import com.sictiy.jserver.game.player.JPlayer;

/**
 * @author 10460
 * @version 2019/10/05 14:23
 **/
@Setter
@Getter
public abstract class AbstractPlayerModule
{
    protected JPlayer player;

    abstract public boolean load();

    public void check()
    {
    }

    abstract public boolean save();

    abstract public void sendInfo();
}
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
    private boolean newOpen;

    protected JPlayer player;

    // 模块开启
    public void open()
    {
    }

    // 模块加载
    abstract public boolean load();

    // 模块数据检查
    public void check()
    {
    }

    // 模块数据落地
    abstract public boolean save();

    abstract public void sendInfo();
}
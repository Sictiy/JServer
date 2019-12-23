package com.sictiy.jserver.game.logic;

/**
 * 等级相关
 *
 * @author sictiy.xu
 * @version 2019/12/23 16:57
 **/
public class LevelLogic
{
    public static boolean canLevelUp(int levelType, int exp, int currentLevel)
    {
        return getLevelByExp(levelType, exp) > currentLevel;
    }

    public static int getLevelByExp(int levelType, int exp)
    {
        return exp / 100;
    }
}

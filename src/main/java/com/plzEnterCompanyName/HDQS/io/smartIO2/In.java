package com.plzEnterCompanyName.HDQS.io.smartIO2;

/**
 * 玩家输入，以便于游戏继续下去。
 */
public interface In {

    /**
     * 输什么，就返回什么，不要管格式正不正确。
     */
    String next();

    /**
     * 让游戏“卡一下”，等玩家点回车。
     */
    void blankOperate();
}

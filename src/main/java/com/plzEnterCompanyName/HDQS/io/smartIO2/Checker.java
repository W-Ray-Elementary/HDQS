package com.plzEnterCompanyName.HDQS.io.smartIO2;

/**
 * 用于对玩家的输入进行检查。
 */
public interface Checker {

    /**
     * 检测字符串是否符合要求
     *
     * @param s 受检字符串
     * @return 符合要求返回 {@code true}否则返回 {@code false}
     */
    boolean pass(String s);

    /**
     * @return 简述不通过的理由，并给出提示帮玩家输对。
     */
    String noPassMsg();
}

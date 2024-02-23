package com.plzEnterCompanyName.HDQS;

import com.plzEnterCompanyName.HDQS.text.LinePrinter;

/**
 * 当遇到类似读盘（运行需要较长时间，但整个游戏过程中只需运行一次）的
 * 场景时，就应当考虑实现该接口，程序的启动器会自动扫描、检测该接口的
 * 实现类，并于游戏实际运行前进行启动流程。
 */
public interface RequireBoot {

    /**
     * @param lp 放在进度条上显示的文字，用于给予玩家提示
     */
    void boot(LinePrinter lp);
}

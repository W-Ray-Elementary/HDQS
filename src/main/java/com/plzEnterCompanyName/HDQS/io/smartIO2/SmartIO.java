package com.plzEnterCompanyName.HDQS.io.smartIO2;

import java.util.List;

/**
 * SmartIO基于“读取-求值-输出-循环”设计。聚焦于一站式解决“读取”和“输出”环节的问题，
 * 致力于提升用户使用“读取-求值-输出-循环”架构的体验。自2022年2月W-Ray敲下frame的
 * 第一行代码以来，SmartIO的具体实现已经经过了6次重构。
 *
 * <p>SmartIO的设计目标是集中管理玩家输入的数值与输出游戏画面，由于SmartIO做的是管
 * 理工作，故其内部不存在接收、处理、输出等功能的代码。
 */
public class SmartIO {

    /**
     * 为了接收玩家的操作数据，SmartIO持有一个{@link In}对象。
     */
    private In in;

    /**
     * 用于更改In，为日后实现AutoPlay预留
     *
     * @param in 更改后的In
     */
    public void setIn(In in) {
        this.in = in;
    }

    /**
     * 为了将游戏的运算结果输出，SmartIO拥有若干个{@link Out}实例。
     */
    private final List<Out> outList;

    /**
     * 清除所有Out对象
     */
    public void clearOut() {
        outList.clear();
    }

    /**
     * 添加一种Out的途径
     *
     * @param o 要Out去的地方
     */
    public void addOutInstance(Out o) {
        outList.add(o);
    }

    public SmartIO(In in, List<Out> outList) {
        this.in = in;
        this.outList = outList;
    }

    /**
     * 在输出后返回玩家的键盘输入。
     *
     * @return 玩家输什么，它就是什么。
     */
    public String next(Message message) {
        return next(message, null);
    }


    /**
     * 在输出后返回玩家的键盘输入，如果不是Int，发警报。
     *
     * @return 符合要求才能返回。
     */
    public int nextInt(Message message) {
        return Integer.parseInt(next(message, new IntChecker()));
    }

    /**
     * 使用自定义的Checker对输入值进行检查
     *
     * @param checker 自定义的Checker
     * @return checker放行了才返回
     */
    public int nextInt(Message message, Checker checker) {
        return Integer.parseInt(next(message, checker));
    }

    /**
     * 典型的检查流程，一直warning直到输入值满足要求为止
     *
     * @param checker 自定义的Checker
     * @return checker放行了才返回
     */
    public String next(Message message, Checker checker) {
        out(message);
        String s = in.next();
        if (checker == null) return s;
        for (; ; ) {
            if (checker.pass(s)) {
                return s;
            } else {
                warn(checker.noPassMsg());
                s = in.next();
            }
        }
    }

    private void out(Message message) {
        for (Out out : outList) {
            out.out(message);
        }
    }

    private void warn(String warnMsg) {
        for (Out out : outList) {
            if (out instanceof WarnAble w) {
                w.warn(warnMsg);
            }
        }
    }
}

package com.plzEnterCompanyName.HDQS.text.frame;

import com.plzEnterCompanyName.HDQS.io.smartIO2.Message;

/**
 * 块排版器
 *
 * <p>在一个窗口中会有多个BlockTypesetter，它们的排列方式在一个矩形窗
 * 体中不断占用四周空间，每个BlockTypesetter都用掉一点，最后剩下的留给
 * 最后一个BlockTypesetter。单个BlockTypesetter在窗体中贴附在窗体四周，
 * 也只能贴附在窗体四周，所以有了{@link SupportedBT_Position}里的
 * {@code RIGHT, UP, LEFT, DOWN}。
 *
 * <p>当块排版器的空间不够用时，通常情况下其有两种选择，一是在自己占的所
 * 有空间中填充上'######'以期玩家调整配置文件，一是直接抛出异常。
 *
 * <p>一次排版中，单个{@code  List<Layer>}中的元素通常会被遍历两次，
 * 第一次遍历时，调用
 * {@link BlockTypesetter#setType(Message, int) setType(Message, int)}，
 * BT的子类们完成排版，并且输出内容到{@code String[] cache}当中。第
 * 二次遍历时，Layout适时取出每个{@code String[] cache}中的字符并按
 * 照配置文件的要求把它排列好。
 */
public abstract class BlockTypesetter {

    /**
     * BlockTypesetter们在一个Layout中的排版就像是在一个矩形窗体中不
     * 断占用四周空间。直到倒数第二个BT与Layout完成交流，Layout计算出
     * 剩下的空间，传递给Layers中的最后一个BT。
     */
    protected SupportedBT_Position position;

    /**
     * 当
     * {@link BlockTypesetter#setType(Message, int) setType(Message, int)}
     * 方法完毕后，BT的子类应当把排版好的字符输出到此cache中，并且在下一次
     * {@code setType(Message, int)}被调用之前将cache清空。
     */
    protected String[] cache;

    /**
     * 构造一个BlockTypesetter对象，使用position作为起始参数
     *
     * @param position 请{@link SupportedBT_Position 参见}
     */
    protected BlockTypesetter(SupportedBT_Position position) {
        this.position = position;
    }

    /**
     * 如其他地方说的一样，一般的BT子类排版时有两个工作模态，水平和垂直。
     * 是水平放还是垂直放由BT的构造方法传参决定。
     * 比如{@link BT_Info}，既可以水平放，也可以垂直放。但是
     * {@link BT_Tittle}就只能水平放，垂直放会抛异常。
     *
     * @param message  请{@link Message 参见}
     * @param posLimit 从结果来看，当BT在窗体中被水平放置时，posLimit
     *                 代表水平方向上的剩余空间。反之则代表垂直方向上的
     *                 剩余空间。从过程来讲，就像一个矩形一样，相同面积
     *                 （内容）的矩形要是用高度决定宽度，posLimit就代
     *                 表高度。
     * @return 沿用前文的比喻， 一个相同面积（内容）的矩形，要是用高度决
     * 定宽度，posLimit就代表高度。而使用高度决定宽度的矩形，就会把决定
     * 好的宽度返回，供Layout排版。
     */
    protected abstract int setType(Message message, final int posLimit);

    /**
     * 一次排版中，单个{@code  List<Layer>}中的元素通常会被遍历两次，
     * 第一次遍历时，调用
     * {@link BlockTypesetter#setType(Message, int) setType(Message, int)}，
     * BT的子类们完成排版，并且输出内容到{@code String[] cache}当中。
     *
     * @return 二次遍历时，Layout适时取出每个{@code String[] cache}中的字符并按
     * 照配置文件的要求把它排列好。
     */
    protected abstract String getCache();

    /**
     * 重置状态，以便下一次排版，一般的实现方法是把cacheIndex重新设置为0。
     */
    protected abstract void reset();
}

package com.plzEnterCompanyName.HDQS.text.frame;

import com.plzEnterCompanyName.HDQS.io.smartIO2.Message;
import com.plzEnterCompanyName.HDQS.text.AwtRuler;
import com.plzEnterCompanyName.HDQS.text.Ruler;
import com.plzEnterCompanyName.HDQS.text.Typography;
import com.plzEnterCompanyName.HDQS.text.zh_CN_Typography;
import com.plzEnterCompanyName.HDQS.util.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 用于按照布局排版字符串
 *
 * <p>类比像素是显卡输出图像的最小单元，{@code Layout}进行排版的最小单元是一个
 * 西文字符所占有的空间。通常情况下，英文字母、数字、ASCII中的标点符号等的宽度为
 * 1，而中文字符与中文标点的宽度为2。该值可以使用{@link Ruler}对象测量。
 *
 * <p>若干个“一个西文字符所占有的空间”组成了一个二维平面，这个二维平面在Layout中
 * 表现为一个二维{@code int}数组。此类的工作从定义这个二维数组的长和宽开始。在
 * Windows10, Windows11中，cmd的窗口大小为120x30，而Windows7是80x25。此
 * 外，还需要留下一行用于用户输入，故最常用的大小为120x29或80x25。定义好这个二维
 * 数组的长和宽之后，就可以将{@link BlockTypesetter}按序逐个放入其中，得到一个
 * 标记着不同{@code BlockTypesetter}所在位置的二维数组。
 * <blockquote><pre> 效果如下："""
 * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 * 3 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 4 2
 * 3 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 5 2
 * 3 A A A A A A A A A A A A A A A A A A A A A 7 6 6 6 6 6 6 6 6 2
 * 3 A A A A A A A A A A A A A A A A A A A A A 7 6 6 6 6 6 6 6 6 2
 * 3 A A A A A A A A A A A A A A A A A A A A A 7 6 6 6 6 6 6 6 6 2
 * 3 A A A A A A A A A A A A A A A A A A A A A 7 6 6 6 6 6 6 6 6 2
 * 3 A A A A A A A A A A A A A A A A A A A A A 7 6 6 6 6 6 6 6 6 2
 * 3 A A A A A A A A A A A A A A A A A A A A A 7 6 6 6 6 6 6 6 6 2
 * 3 A A A A A A A A A A A A A A A A A A A A A 7 6 6 6 6 6 6 6 6 2
 * 3 A A A A A A A A A A A A A A A A A A A A A 7 6 6 6 6 6 6 6 6 2
 * 3 A A A A A A A A A A A A A A A A A A A A A 7 6 6 6 6 6 6 6 6 2
 * 3 A A A A A A A A A A A A A A A A A A A A A 7 6 6 6 6 6 6 6 6 2
 * 3 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 9 7 6 6 6 6 6 6 6 6 2
 * 3 8 8 8 8 8 8 8 8 8 8 8 8 8 8 8 8 8 8 8 8 8 7 6 6 6 6 6 6 6 6 2
 * 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1
 * """</pre></blockquote>
 * 随后从左到右从上到下遍历这个二维数组，把它们所代表的字符串从对应的
 * {@code BlockTypesetter}中取出，形成一个长长的字符串。此时，这个字符串就完全按
 * 照先前的配置排列，只需直接输出，终端里就会看到排好的文字。
 */
public class Layout {

    /**
     * 该布局可用的总空间的宽度。
     */
    private final int width;
    protected int widthRemain;


    /**
     * 该布局可用的总空间的高度。
     */
    private final int height;
    protected int heightRemain;

    /**
     *
     */
    private final List<Layer> layers;

    /**
     * BlockTypesetter需要先配置，再使用。一种方法是在构造方法中传入每一个参数。
     * 另一种就是使用{@link Configuration}来快速创建。在本类的构造方法中要求的
     * {@code Configuration}格式中，会首先从{@code Layer}标签内寻找，找不到
     * 后就从此属性中加载。
     */
    protected List<Configuration> BT_GlobalConfigs;

    /**
     * {@code Ruler}用于测量字符宽度。但是第一次调用{@code new AwtRuler()}
     * 时，会消耗数百毫秒的惊人时间。
     */
    public static final Ruler RULER = new AwtRuler();

    /**
     * 使用{@code static}以避免重复创建对象。
     */
    public static final Typography TYPO = new zh_CN_Typography(RULER);

    /**
     * 有些时候，要么是开发者塞了太多，要么是玩家把窗口调得太小，总之，屏
     * 幕空间不够时就可能抛出此异常。
     */
    protected static final String spaceInsufficientMsg = "Screen space is insufficient, " +
            "please try to adjust settings";

    /**
     * {@link Layout#Layout(int, int)}后，需要一个个配置并添加
     * {@code BlockTypesetter}，这太麻烦了，所以此方法可以从
     * {@code Configuration}对象快速创建配置好的布局。
     *
     * <p><blockquote><pre>
     * 这里是格式要求：
     * Layout
     * {
     *     width = ...
     *     height = ...
     *
     *     BlockTypesetter
     *     {
     *         name = ...
     *         // 具体要求看对应的 BT_Info、BT_Text...
     *     }
     *     // BlockTypesetter配置，每种BT都要配置。
     *
     *     Layer
     *     {
     *         name = ... // 填写与先前BlockTypesetter中的name属性。
     *         position = ...
     *     }
     *     // 空间够就可以加 Layer
     *     ...
     * }
     * </pre></blockquote>
     *
     * @param config 从此配置中读取。
     */
    public Layout(Configuration config) {
        this.width = config.getInt("width");
        this.height = config.getInt("height");
        this.BT_GlobalConfigs = config.subSets("BlockTypesetter");
        this.widthRemain = this.width;
        this.heightRemain = this.height;
        this.layers = new ArrayList<>();
        for (Configuration layersCfg : config.subSets("Layer")) {
            layers.add(new Layer(layersCfg, BT_GlobalConfigs));
        }
    }

    /**
     * 创建一个只配置了宽高的{@code Layout}对象，需要逐个添加Layer.
     *
     * @param width  宽度，单位是一个西文字符。
     * @param height 高度，单位是一行。
     */
    public Layout(int width, int height) {
        this.width = width;
        this.height = height;
        this.layers = new ArrayList<>();
        this.widthRemain = this.width;
        this.heightRemain = this.height;
    }

    /**
     * 用于追加{@code layer}。
     *
     * @param bt 要追加的layer.
     */
    public void addLayer(BlockTypesetter bt) {
        layers.add(new Layer(bt));
    }

    /**
     * 使用提前配置好的布局，按照提前传入的{@code Message}，输出一个字符串，这个
     * 字符串加上换行后会非常美观。
     *
     * @param msg 从此获取要排版的内容。
     * @return 返回...
     */
    protected String setType(Message msg) {
        int[][] table = new int[height][width];
        for (int[] is : table)
            Arrays.fill(is, -1);
        int x_1 = 0;
        int y_1 = 0;
        int x_2 = width;
        int y_2 = height;
        for (int i = 0; i < layers.size() - 1; i++) {
            Layer layer = layers.get(i);
            switch (layer.typesetter.position) {
                case UP -> {
                    int usage = layer.typesetter.setType(msg, widthRemain);
                    heightRemain -= usage;
                    fillTable(table, x_1, y_1, x_2, y_1 + usage, i);
                    y_1 += usage;
                }
                case RIGHT -> {
                    int usage = layer.typesetter.setType(msg, heightRemain);
                    widthRemain -= usage;
                    fillTable(table, x_2 - usage, y_1, x_2, y_2, i);
                    x_2 -= usage;
                }
                case DOWN -> {
                    int usage = layer.typesetter.setType(msg, widthRemain);
                    heightRemain -= usage;
                    fillTable(table, x_1, y_2 - usage, x_2, y_2, i);
                    y_2 -= usage;
                }
                case LEFT -> {
                    int usage = layer.typesetter.setType(msg, heightRemain);
                    widthRemain -= usage;
                    fillTable(table, x_1, y_1, x_1 + usage, y_2, i);
                    x_1 += usage;
                }
            }
            if (x_2 - x_1 <= 1 || y_2 - y_1 <= 1)
                throw new RuntimeException(Layout.spaceInsufficientMsg);
        }
        BlockTypesetter lastTypesetter = layers.get(layers.size() - 1).typesetter;
        AdjustableBT adjustableBT;
        if (lastTypesetter instanceof AdjustableBT)
            adjustableBT = (AdjustableBT) lastTypesetter;
        else
            throw new RuntimeException(lastTypesetter.getClass().getName() + " cannot be the last element of layers");
        switch (lastTypesetter.position) {
            case UP, DOWN -> {
                adjustableBT.tellAvailSpace(heightRemain);
                adjustableBT.tellBTThisFactThatItIsTheLastOne();
                lastTypesetter.setType(msg, widthRemain);
            }
            case LEFT, RIGHT -> {
                adjustableBT.tellAvailSpace(widthRemain);
                adjustableBT.tellBTThisFactThatItIsTheLastOne();
                lastTypesetter.setType(msg, heightRemain);
            }
        }
        fillTable(table, x_1, y_1, x_2, y_2, layers.size() - 1);
        reset();
        return write(table);
    }

    /**
     * 利用坐标对二维数组进行填充。
     *
     * @param table 要填的表。
     * @param x_1   第一个点的横坐标。
     * @param y_1   第一个点的横坐标。
     * @param x_2   第一个点的横坐标。
     * @param y_2   第一个点的横坐标。
     * @param ink   坐标内的数值全部改为此值。
     */
    private static void fillTable(
            int[][] table,
            int x_1,
            int y_1,
            int x_2,
            int y_2,
            int ink) {
        for (int y = y_1; y < y_2; y++)
            for (int x = x_1; x < x_2; x++)
                table[y][x] = ink;
    }

    /**
     * 把这个对象重置到能够再次排版的状态，但不会重置已经配置好的布局。
     */
    private void reset() {
        this.widthRemain = this.width;
        this.heightRemain = this.height;
        for (Layer layer : layers)
            layer.typesetter.reset();
    }

    /**
     * 按照表输出字符串的一个工具方法。
     *
     * @param marker 按此表输出，相同值合并。
     * @return 目前需要自动换行。
     */
    private String write(int[][] marker) {
        StringBuilder sb = new StringBuilder();
        int m = -1;
        for (int[] is : marker)
            for (int anInt : is)
                if (m != anInt) {
                    m = anInt;
                    sb.append(layers.get(m).typesetter.getCache());
                }
        return sb.toString();
    }
}

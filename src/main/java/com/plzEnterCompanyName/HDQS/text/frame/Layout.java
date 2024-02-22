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
 * <p>
 * 用于按照布局排版字符串
 * </p>
 * <p>
 * 类比像素是显卡输出图像的最小单元，{@code Layout}进行排版的最小单元是一个西文
 * 字符所占有的空间。通常情况下，英文字母、数字、ASCII中的标点符号等的宽度为1，而
 * 中文字符与中文标点的宽度为2。该值可以使用{@link Ruler}对象测量。
 * </p>
 * <p>
 * 若干个“一个西文字符所占有的空间”组成了一个二维平面，这个二维平面在Layout中表现
 * 为一个二维{@code int}数组。此类的工作从定义这个二维数组的长和宽开始。在
 * Windows10, Windows11中，cmd的窗口大小为120x30，而Windows7是80x25。此外，还
 * 需要留下一行用于用户输入，故最常用的大小为120x29或80x25。定义好这个二维数组的
 * 长和宽之后，就可以将{@link BlockTypesetter}按序逐个放入其中，得到一个标记着
 * 不同{@code BlockTypesetter}所在位置的二维数组。
 * <blockquote>
 * 
 * <pre> 效果如下："""
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
 * """</pre>
 * 
 * </blockquote>
 * 随后从左到右从上到下遍历这个二维数组，把它们所代表的字符串从对应的
 * {@code BlockTypesetter}中取出，形成一个长长的字符串。此时，这个字符串就完全按
 * 照先前的配置排列，只需直接输出，终端里就会看到排好的文字。
 * </p>
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
    protected List<Configuration> BT_GlobalConfigs;
    public static final Ruler RULER = new AwtRuler();
    public static final Typography TYPO = new zh_CN_Typography(RULER);

    /**
     * 有些时候，要么是开发者塞了太多，要么是玩家把窗口调得太小，总之，屏
     * 幕空间不够时就可能抛出此异常。
     */
    protected static final String spaceInsufficientMsg = "Screen space is insufficient, " +
            "please try to adjust settings";

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

    public Layout(int width, int height) {
        this.width = width;
        this.height = height;
        this.layers = new ArrayList<>();
        this.widthRemain = this.width;
        this.heightRemain = this.height;
    }

    public void addLayer(BlockTypesetter bt) {
        layers.add(new Layer(bt));
    }

    protected String setType(Message msg) {
        int[][] table = new int[height][width];
        for (int[] ints : table)
            Arrays.fill(ints, -1);
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

    private void reset() {
        this.widthRemain = this.width;
        this.heightRemain = this.height;
        for (Layer layer : layers)
            layer.typesetter.nextPage();
    }

    private String write(int[][] marker) {
        StringBuilder sb = new StringBuilder();
        int m = -1;
        for (int[] ints : marker)
            for (int anInt : ints)
                if (m != anInt) {
                    m = anInt;
                    sb.append(layers.get(m).typesetter.getCache());
                }
        return sb.toString();
    }
}

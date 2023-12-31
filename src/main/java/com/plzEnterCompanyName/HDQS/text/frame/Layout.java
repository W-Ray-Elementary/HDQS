package com.plzEnterCompanyName.HDQS.text.frame;

import com.plzEnterCompanyName.HDQS.io.smartIO2.Message;
import com.plzEnterCompanyName.HDQS.util.Lexicon;
import com.plzEnterCompanyName.HDQS.util.Lexicons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 用于输出布局缓存文件（也许）
 */
public class Layout {
    private final int width;
    protected int widthRemain;
    private final int height;
    protected int heightRemain;
    private final List<Layer> layers;
    protected List<Lexicon> BT_GlobalConfigs;

    /**
     * 有些时候，要么是开发者塞了太多，要么是玩家把窗口调得太小，总之，屏
     * 幕空间不够时就可能抛出此异常。
     */
    protected static final String spaceInsufficientMsg = "Screen space is insufficient, " +
            "please try to adjust settings in " + Frame.DEFAULT_CONFIG__FILE_PATH;

    public Layout(Lexicon config) {
        this.width = Integer.parseInt(config.getFirst("width"));
        this.height = Integer.parseInt(config.getFirst("height"));
        this.BT_GlobalConfigs = Lexicons.listOut(config, "BlockTypesetter");
        this.widthRemain = this.width;
        this.heightRemain = this.height;
        // \begin{} 这里的代码与Lexicons中重复，但是能run就别改
        List<Object> layersCfgObjs;
        layersCfgObjs = new ArrayList<>(List.of(((Lexicon)config.getAll("Layout")[0]).getAll("Layer")));
        List<Lexicon> layersCfgs = new ArrayList<>();
        for (Object layersCfgObj : layersCfgObjs) {
            if (layersCfgObj instanceof Lexicon)
                layersCfgs.add((Lexicon) layersCfgObj);
        }
        // \end{} 这里的代码与Lexicons中重复，但是能run就别改
        this.layers = new ArrayList<>();
        for (Lexicon layersCfg : layersCfgs) {
            layers.add(new Layer(layersCfg, BT_GlobalConfigs));
        }
    }

    protected String setType(Message msg) {
        int[][] table = new int[height][width];
        for (int[] ints : table) Arrays.fill(ints, -1);
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
        BlockTypesetter lastTypesetter = layers.get(layers.size()-1).typesetter;
        AdjustableBT adjustableBT;
        if (lastTypesetter instanceof AdjustableBT)
            adjustableBT = (AdjustableBT)lastTypesetter;
        else throw new RuntimeException(lastTypesetter.getClass().getName() + " cannot be the last element of layers");
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
        fillTable(table, x_1, y_1, x_2, y_2, layers.size()-1);
        reset();
        return write(table);
    }

    private static void fillTable(
            int[][] table,
            int x_1,
            int y_1,
            int x_2,
            int y_2,
            int ink)
    {
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
    static class Layer {

        protected final String type;
        protected final String position;
        protected final BlockTypesetter typesetter;
        public Layer(Lexicon layerConfig, List<Lexicon> bTConfigs) {
            this.type = layerConfig.getFirst("type");
            this.position = layerConfig.getFirst("position");
            List<Object> contentsObjs = new ArrayList<>(List.of(layerConfig.getAll("", true)));
            List<Lexicon> ls = new ArrayList<>();
            for (Object contentObj : contentsObjs) {
                if (contentObj instanceof Lexicon l) {
                    ls.add(l);
                }
            }
            SupportedBT_Position posHandle = convertEnum();
            switch (type) {
                case "SeparateLine" -> typesetter = new BT_SeparateLine(
                        posHandle,
                        priorityForBT(ls, bTConfigs, "SeparateLine"));
                case "Tittle"        -> typesetter = new BT_Tittle(
                        posHandle,
                        priorityForBT(ls, bTConfigs, "Tittle"));
                case "Info"          -> typesetter = new BT_Info(
                        posHandle,
                        priorityForBT(ls, bTConfigs, "Info"));
                case "Operation"     -> typesetter = new BT_Operation(
                        posHandle,
                        priorityForBT(ls, bTConfigs, "Operation"));
                case "Warning"       -> typesetter = new BT_Warning(
                        posHandle,
                        priorityForBT(ls, bTConfigs, "Warning"));
                case "Text"       -> typesetter = new BT_Text(
                        posHandle,
                        priorityForBT(ls, bTConfigs, "Text"));
                default ->
                    throw new UnsupportedOperationException("Unsupported BlockComposition type: " + type);
            }
        }

        private static Lexicon priorityForBT(
                List<Lexicon> first,
                List<Lexicon> second,
                String name)
        {
            Lexicon firstContent = Lexicons.orderName(first, "BlockTypesetter", name);
            return firstContent == null ?
                    Lexicons.strictOrderName(second, "BlockTypesetter", name) : firstContent;
        }

        private SupportedBT_Position convertEnum() {
            SupportedBT_Position posHandle;
            switch (position) {
                case "RIGHT" -> posHandle = SupportedBT_Position.RIGHT;
                case "UP"    -> posHandle = SupportedBT_Position.UP;
                case "LEFT"  -> posHandle = SupportedBT_Position.LEFT;
                case "DOWN"  -> posHandle = SupportedBT_Position.DOWN;
                default ->
                    throw new UnsupportedOperationException("Unsupported BC_Position: " + position);
            }
            return posHandle;
        }

        @Override
        public String toString() {
            return typesetter.toString();
        }
    }
}

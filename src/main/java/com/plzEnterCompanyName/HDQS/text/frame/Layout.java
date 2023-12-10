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

    public Layout(Lexicon config) {
        this.width = Integer.parseInt(config.getFirst("width"));
        this.height = Integer.parseInt(config.getFirst("height"));
        this.BT_GlobalConfigs = Lexicons.listOut(config, "BlockTypesetter");
        reset();
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
        for (int i = 0; i < layers.size() - 1; i++) {
            Layer layer = layers.get(i);
            switch (layer.typesetter.position) {
                case UP, DOWN -> {
                    int usage = layer.typesetter.setType(msg, widthRemain);
                    heightRemain -= usage;
                }
                case LEFT, RIGHT -> {
                    int usage = layer.typesetter.setType(msg, heightRemain);
                    widthRemain -= usage;
                }
            }
        }
        BlockTypesetter lastTypesetter = layers.get(layers.size()-1).typesetter;
        AdjustableBT adjustableBT;
        if (lastTypesetter instanceof AdjustableBT)
            adjustableBT = (AdjustableBT)lastTypesetter;
        else throw new RuntimeException(lastTypesetter.getClass().getName() + " cannot be the last element of layers");
        switch (lastTypesetter.position) {
            case UP, DOWN -> {
                adjustableBT.tellAvailSpace(heightRemain);
                lastTypesetter.setType(msg, widthRemain);
            }
            case LEFT, RIGHT -> {
                adjustableBT.tellAvailSpace(widthRemain);
                lastTypesetter.setType(msg, heightRemain);
            }
        }
        reset();
        return write();
    }

    private void reset() {
        this.widthRemain = this.width;
        this.heightRemain = this.height;
    }

    private String write() {
        return null;
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
                        priority(ls, bTConfigs, "BlockTypesetter", "SeparateLine"));
                case "Tittle"        -> typesetter = new BT_Tittle(
                        posHandle,
                        priority(ls, bTConfigs, "BlockTypesetter", "Tittle"));
                case "Info"          -> typesetter = new BT_Info(
                        posHandle,
                        priority(ls, bTConfigs, "BlockTypesetter", "Info"));
                case "Operation"     -> typesetter = new BT_Operation(
                        posHandle,
                        priority(ls, bTConfigs, "BlockTypesetter", "Operation"));
                case "Warning"       -> typesetter = new BT_Warning(
                        posHandle,
                        priority(ls, bTConfigs, "BlockTypesetter", "Warning"));
                case "Text"       -> typesetter = new BT_Text(
                        posHandle,
                        priority(ls, bTConfigs, "BlockTypesetter", "Text"));
                default ->
                    throw new UnsupportedOperationException("Unsupported BlockComposition type: " + type);
            }
        }

        private static Lexicon priority(
                List<Lexicon> first,
                List<Lexicon> second,
                String moduleType,
                String name)
        {
            Lexicon firstContent = Lexicons.orderName(first, moduleType, name);
            return firstContent == null ?
                    Lexicons.strictOrderName(second, moduleType, name) : firstContent;
        }

        private SupportedBT_Position convertEnum() {
            SupportedBT_Position posHandle;
            switch (position) {
                case "RIGHT" -> { posHandle = SupportedBT_Position.RIGHT; }
                case "UP"    -> { posHandle = SupportedBT_Position.UP;    }
                case "LEFT"  -> { posHandle = SupportedBT_Position.LEFT;  }
                case "DOWN"  -> { posHandle = SupportedBT_Position.DOWN;  }
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

package com.plzEnterCompanyName.HDQS.text.frame;

import com.plzEnterCompanyName.HDQS.io.smartIO2.Message;
import com.plzEnterCompanyName.HDQS.util.Lexicon;

import java.util.ArrayList;
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

    public Layout(Lexicon config) {
        this.width = Integer.parseInt(config.getFirst("width"));
        this.height = Integer.parseInt(config.getFirst("height"));
        this.widthRemain = this.width;
        this.heightRemain = this.height;
        List<Object> layersCfgObjs;
        layersCfgObjs = new ArrayList<>(List.of(((Lexicon)config.getAll("Layout")[0]).getAll("Layer")));
        List<Lexicon> layersCfgs = new ArrayList<>();
        for (Object layersCfgObj : layersCfgObjs) {
            if (layersCfgObj instanceof Lexicon)
                layersCfgs.add((Lexicon) layersCfgObj);
        }
        this.layers = new ArrayList<>();
        for (Lexicon layersCfg : layersCfgs) {
            layers.add(new Layer(layersCfg));
        }
    }

    protected void setType(Message msg) {
        for (Layer layer : layers) {
            switch (layer.typesetter.position) {
                case UP, DOWN -> {
                    layer.typesetter.setType(msg, widthRemain);
                    int usage = layer.typesetter.getSecondPosLimit();
                    heightRemain -= usage;
                }
                case LEFT, RIGHT -> {
                    layer.typesetter.setType(msg, heightRemain);
                    int usage = layer.typesetter.getSecondPosLimit();
                    widthRemain -= usage;
                }
            }
        }
    }

    static class Layer {
        protected final String type;
        protected final String position;
        protected final BlockTypesetter typesetter;
        public Layer(Lexicon config) {
            this.type = config.getFirst("type");
            this.position = config.getFirst("position");
            SupportedBT_Position posHandle = convertEnum();
            switch (type) {
                case "SEPARATE_LINE" -> typesetter = new BT_SeparateLine(posHandle);
                case "TITTLE"        -> typesetter = new BT_Tittle(      posHandle);
                default ->
                    throw new UnsupportedOperationException("Unsupported BlockComposition type: " + type);
            }
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
    }
}

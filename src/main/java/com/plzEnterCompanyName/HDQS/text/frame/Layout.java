package com.plzEnterCompanyName.HDQS.text.frame;

import com.plzEnterCompanyName.HDQS.util.Lexicon;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于输出布局缓存文件（也许）
 */
public class Layout {
    private final int width;
    private final int height;
    private final List<Layer> layers;

    public Layout(Lexicon config) {
        this.width = Integer.parseInt(config.getFirst("width"));
        this.height = Integer.parseInt(config.getFirst("height"));
        List<Object> layersCfgObjs = new ArrayList<>(List.of(((Lexicon)config.getAll("Layout")[0]).getAll("Layer")));
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

    static class Layer {
        private final String type;
        private final String position;
        public Layer(Lexicon config) {
            this.type = config.getFirst("type");
            this.position = config.getFirst("position");
        }
    }
}

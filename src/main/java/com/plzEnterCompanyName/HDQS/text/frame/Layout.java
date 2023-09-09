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
    private final List layers;

    public Layout(Lexicon config) {
        this.width = Integer.parseInt(config.getFirst("width"));
        this.height = Integer.parseInt(config.getFirst("height"));
        List<Object> layersCfgObjs = new ArrayList<>(List.of(config.getAll("Layer")));
        List<Lexicon> layersCfg = new ArrayList<>();
        for (Object layersCfgObj : layersCfgObjs) {
            if (layersCfgObj instanceof Lexicon)
                layersCfg.add((Lexicon) layersCfgObj);
        }
        this.layers = new ArrayList<>();

    }

    static class Layer {
        public Layer(Lexicon config) {

        }
    }
}

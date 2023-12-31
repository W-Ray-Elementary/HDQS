package com.plzEnterCompanyName.HDQS.text.frame;

import com.plzEnterCompanyName.HDQS.io.smartIO2.Message;
import com.plzEnterCompanyName.HDQS.util.Lexicon;

import java.util.Arrays;

public class BT_SeparateLine extends BlockTypesetter {

    protected final char HORIZONTAL_STYLE;

    protected final char VERTICAL_STYLE;

    public BT_SeparateLine(SupportedBT_Position position, Lexicon config) {
        super(position);
        String horizontalStyleStr = config.getFirst("horizontalStyle");
        String verticalStyleStr   = config.getFirst("verticalStyle"  );
        HORIZONTAL_STYLE = (char) Integer.parseInt(horizontalStyleStr);
        VERTICAL_STYLE   = (char) Integer.parseInt(verticalStyleStr  );
    }

    @Override
    protected int setType(Message message, final int posLimit) {
        switch (position) {
            case UP  , DOWN  -> cache = new String[]{
                    Frame.AWT_RULER.repeatW(String.valueOf(HORIZONTAL_STYLE), posLimit)
            };
            case LEFT, RIGHT -> {
                cache = new String[posLimit];
                Arrays.fill(cache, String.valueOf(VERTICAL_STYLE));
            }
        }
        return 1;
    }

    @Override
    protected String getCache() {
        return  cache[0];
    }

    @Override
    protected void nextPage() {  }
}

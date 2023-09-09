package com.plzEnterCompanyName.HDQS.text.frame;

import com.plzEnterCompanyName.HDQS.io.smartIO2.Message;
import com.plzEnterCompanyName.HDQS.util.Lexicon;
import com.plzEnterCompanyName.HDQS.util.Lexicons;

public class BT_Tittle extends BlockTypesetter{
    protected BT_Tittle(SupportedBT_Position position) {
        super(position);
    }

    public static final int titleRetraction;
    public static final char titleRetractionChar;
    public static final boolean isDrawingGameName;
    static {
        Lexicon config = Lexicons.orderName(Frame.DEFAULT_CONFIG.read(),
                "BlockTypesetter", "Tittle");
        String titleRetractionStr = config.getFirst("titleRetraction");
        String titleRetractionCharStr = config.getFirst("titleRetractionChar");
        String isDrawingWatermarkStr = config.getFirst("isDrawingGameName");
        titleRetraction = Integer.parseInt(titleRetractionStr);
        titleRetractionChar = (char) Integer.parseInt(titleRetractionCharStr);
        isDrawingGameName = Boolean.parseBoolean(isDrawingWatermarkStr);
    }

    @Override
    protected void setType(Message message, int firstPosLimit) {
        if (position == SupportedBT_Position.UP || position == SupportedBT_Position.DOWN) {

        } else {
            throw new UnsupportedOperationException("Title can not execute vertical layout.");
        }
    }

    @Override
    protected int getSecondPosLimit() {
        return 1;
    }
}

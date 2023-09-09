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
    static {
        Lexicon config = Lexicons.orderName(Frame.DEFAULT_CONFIG.read(),
                "BlockTypesetter", "Tittle");
        String titleRetractionStr = config.getFirst("titleRetraction");
        String titleRetractionCharStr = config.getFirst("titleRetractionChar");
        titleRetraction = Integer.parseInt(titleRetractionStr);
        titleRetractionChar = (char) Integer.parseInt(titleRetractionCharStr);
    }

    @Override
    protected void setType(Message message, int firstPosLimit) {
        if (position == SupportedBT_Position.UP || position == SupportedBT_Position.DOWN) {

        } else {

        }
    }

    @Override
    protected int getSecondPosLimit() {
        return 1;
    }
}

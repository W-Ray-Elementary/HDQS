package com.plzEnterCompanyName.HDQS.text.frame;

import com.plzEnterCompanyName.HDQS.io.smartIO2.Message;
import com.plzEnterCompanyName.HDQS.util.Lexicon;
import com.plzEnterCompanyName.HDQS.util.Lexicons;

public class BT_Tittle extends BlockTypesetter{
    protected BT_Tittle(SupportedBT_Position position, Lexicon config) {
        super(position);
        String retractionStr         = config.getFirst("retraction"       );
        String retractionCharStr     = config.getFirst("retractionChar"   );
        String isDrawingWatermarkStr = config.getFirst("isDrawingGameName");
        RETRACTION           = Integer.parseInt(retractionStr            );
        RETRACTION_CHAR      = (char) Integer.parseInt(retractionCharStr );
        IS_DRAWING_GAME_NAME = Boolean.parseBoolean(isDrawingWatermarkStr);
    }

    /**
     * 要缩进几个字符，该数值会将RETRACTION_CHAR代表的字符重复RETRACTION次后
     * 加在开头的地方。
     */
    protected final int RETRACTION;
    /**
     * 缩进字符,RETRACTION_CHAR重复RETRACTION次后加在开头的地方。
     */
    protected final char RETRACTION_CHAR;
    /**
     * 是否绘制游戏名，默认状态下游戏名字右对齐
     */
    protected final boolean IS_DRAWING_GAME_NAME;

    @Override
    protected void setType(Message message, int firstPosLimit) {
        if (position == SupportedBT_Position.UP || position == SupportedBT_Position.DOWN) {
            String s = message.title.get(0);
            int available = firstPosLimit;
            StringBuilder sb = new StringBuilder();
            int rW = RETRACTION * Frame.getWidth(RETRACTION_CHAR);
            setType : {
                if (available < rW) break setType;
                available -= rW;
                sb.append(String.valueOf(RETRACTION_CHAR).repeat(RETRACTION));
                for (char c : s.toCharArray()) {
                    int cW = Frame.getWidth(c);
                    if (available < cW) break;
                    available -= cW;
                    sb.append(c);
                }
            }
            sb.append(" ".repeat(Math.max(0, available)));
            cache = new String[]{ sb.toString() };
        } else {
            throw new UnsupportedOperationException("Title can not execute vertical layout.");
        }
        isTyped = true;
    }

    @Override
    protected int getSecondPosLimit() {
        if (isTyped) {
            isTyped = false;
            return 1;
        }
        else throw new RuntimeException(untypedMsg);
    }

    @Override
    protected String getCache() {
        return cache[0];
    }
}

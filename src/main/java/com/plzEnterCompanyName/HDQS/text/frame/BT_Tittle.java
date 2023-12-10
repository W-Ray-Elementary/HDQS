package com.plzEnterCompanyName.HDQS.text.frame;

import com.plzEnterCompanyName.HDQS.io.smartIO2.Message;
import com.plzEnterCompanyName.HDQS.util.Lexicon;
import com.plzEnterCompanyName.HDQS.util.Lexicons;

/**
 * 用于标题绘制的BlockTypesetter，只占一行。
 * 标题内容左对齐，若有游戏名字则游戏名右对齐
 */
public class BT_Tittle extends BlockTypesetter {
    protected String[] cache;
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

    /**
     *
     * @return 通常情况下，标题只占1行，故返回1
     */
    @Override
    protected int setType(Message message, int posLimit) {
        if (position == SupportedBT_Position.UP || position == SupportedBT_Position.DOWN) {
            cache = new String[]{ String.valueOf('$').repeat(posLimit) };
        } else {
            throw new UnsupportedOperationException("BT_Title can not execute vertical layout.");
        }
        return 1;
    }

    @Override
    protected String getCache() {
        return cache[0];
    }
}

package com.plzEnterCompanyName.HDQS.text.frame;

import com.plzEnterCompanyName.HDQS.io.smartIO2.Message;
import com.plzEnterCompanyName.HDQS.util.Configuration;

/**
 * 用于标题绘制的BlockTypesetter，只占一行。
 * 标题内容左对齐，若有游戏名字则游戏名右对齐
 */
public class BT_Tittle extends BlockTypesetter {
    protected String[] cache;

    public BT_Tittle(SupportedBT_Position position, int indentation, char indentationChar,
                     boolean isDrawingGameName, String gameName) {
        super(position);
        cache = new String[]{""};
        INDENTATION = indentation;
        INDENTATION_CHAR = indentationChar;
        IS_DRAWING_GAME_NAME = isDrawingGameName;
        GAME_NAME = gameName;
    }

    protected BT_Tittle(SupportedBT_Position position, Configuration config) {
        super(position);
        cache = new String[]{""};
        INDENTATION = config.getInt("indentation");
        INDENTATION_CHAR = (char) config.getInt("indentationChar");
        IS_DRAWING_GAME_NAME = config.getBoolean("isDrawingGameName");
        GAME_NAME = config.get("gameName");
    }

    /**
     * 要缩进几个字符，该数值会将INDENTATION_CHAR代表的字符重复INDENTATION次后
     * 加在开头的地方。
     */
    protected final int INDENTATION;

    /**
     * 缩进字符,INDENTATION_CHAR重复INDENTATION次后加在开头的地方。
     */
    protected final char INDENTATION_CHAR;

    /**
     * 是否绘制游戏名，默认状态下游戏名字右对齐
     */
    protected final boolean IS_DRAWING_GAME_NAME;

    protected final String GAME_NAME;

    /**
     * @return 通常情况下，标题只占1行，故返回1
     */
    @Override
    protected int setType(Message message, final int posLimit) {
        if (position == SupportedBT_Position.UP || position == SupportedBT_Position.DOWN) {
            String retraction = String.valueOf(INDENTATION_CHAR).repeat(INDENTATION);
            String title = message.title.get(0);
            String gameName = "";
            if (IS_DRAWING_GAME_NAME) {
                gameName = GAME_NAME;
            }
            int usedWidth = Layout.RULER.measureWidth(retraction + title + gameName);
            if (usedWidth >= posLimit) {
                cache[0] = String.valueOf('#').repeat(posLimit);
                return 1;
            }
            int spacing = posLimit - usedWidth;
            cache[0] = retraction +
                    title +
                    String.valueOf(' ').repeat(spacing) +
                    gameName;
        } else {
            throw new UnsupportedOperationException("BT_Title can not execute vertical layout.");
        }
        return 1;
    }

    @Override
    protected String getCache() {
        return cache[0];
    }

    @Override
    protected void nextPage() {
        reset();
    }

    @Override
    protected void reset() {
    }
}

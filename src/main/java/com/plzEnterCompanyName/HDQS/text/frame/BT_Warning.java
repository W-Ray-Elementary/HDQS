package com.plzEnterCompanyName.HDQS.text.frame;

import com.plzEnterCompanyName.HDQS.io.smartIO2.Message;
import com.plzEnterCompanyName.HDQS.util.Configuration;

public class BT_Warning extends BlockTypesetter {

    /**
     * 要缩进几个字符，该数值会将INDENTATION_CHAR代表的字符重复INDENTATION次后
     * 加在开头的地方。
     */
    protected final int INDENTATION;

    /**
     * 缩进字符,INDENTATION_CHAR重复INDENTATION次后加在开头的地方。
     */
    protected final char INDENTATION_CHAR;

    public BT_Warning(SupportedBT_Position position, int indentation, char indentationChar) {
        super(position);
        INDENTATION = indentation;
        INDENTATION_CHAR = indentationChar;
    }

    protected BT_Warning(SupportedBT_Position position, Configuration config) {
        super(position);
        INDENTATION = config.getInt("indentation");
        INDENTATION_CHAR = (char) config.getInt("indentationChar");
    }

    @Override
    protected int setType(Message message, final int posLimit) {
        String warnStr = extractWarning(message);
        if (warnStr == null) return 0;
        String indentationStr = Layout.RULER.repeatW(INDENTATION_CHAR, INDENTATION);
        int widthAvail = posLimit - INDENTATION;
        int neededWidth = Layout.RULER.measureWidth(warnStr);
        if (neededWidth > widthAvail) {
            cache[0] = String.valueOf('#').repeat(widthAvail);
            return 1;
        }
        int endBlanks = widthAvail - neededWidth;
        cache[0] = indentationStr + warnStr + String.valueOf(' ').repeat(endBlanks);
        return 1;
    }

    private String extractWarning(Message message) {
        return message.advancedInfo.getProperty("FrameWarnStr");
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

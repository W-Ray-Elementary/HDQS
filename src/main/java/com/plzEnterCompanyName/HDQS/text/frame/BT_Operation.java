package com.plzEnterCompanyName.HDQS.text.frame;

import com.plzEnterCompanyName.HDQS.io.smartIO2.Message;
import com.plzEnterCompanyName.HDQS.io.smartIO2.Operation;
import com.plzEnterCompanyName.HDQS.util.Lexicon;

import java.util.ArrayList;
import java.util.List;

public class BT_Operation extends BlockTypesetter {
    
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
     * 单个底部提示之间的间距数
     */
    protected final int HORIZONTAL_SPACING;

    private int cacheIndex;

    protected BT_Operation(SupportedBT_Position position, Lexicon config) {
        super(position);
        String indentationStr       = config.getFirst("indentation"      );
        String indentationCharStr   = config.getFirst("indentationChar"  );
        String horizontalSpacingStr = config.getFirst("horizontalSpacing");
        INDENTATION        =        Integer.parseInt(indentationStr      );
        INDENTATION_CHAR   = (char) Integer.parseInt(indentationCharStr  );
        HORIZONTAL_SPACING =        Integer.parseInt(horizontalSpacingStr);
    }

    @Override
    protected int setType(Message message, int posLimit) {
        if (position == SupportedBT_Position.UP || position == SupportedBT_Position.DOWN) {
        cache = new String[]{ String.valueOf('$').repeat(posLimit) };
        } else {
            throw new UnsupportedOperationException("BT_Operation can not execute vertical layout.");
        }
        cacheIndex = 0;
        return 1;
    }

    @Override
    protected String getCache() {
        return cache[cacheIndex++];
    }

    @Override
    protected void nextPage() {
        cacheIndex = 0;
    }
}

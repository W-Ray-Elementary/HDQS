package com.plzEnterCompanyName.HDQS.text.frame;

import com.plzEnterCompanyName.HDQS.io.smartIO2.Message;
import com.plzEnterCompanyName.HDQS.util.Lexicon;

public class BT_Operation extends BlockTypesetter {

    private int cacheIndex;

    protected BT_Operation(SupportedBT_Position position, Lexicon config) {
        super(position);
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
}

package com.plzEnterCompanyName.HDQS.text.frame;

import com.plzEnterCompanyName.HDQS.io.smartIO2.Message;
import com.plzEnterCompanyName.HDQS.util.Lexicon;

import java.util.Arrays;

public class BT_SeparateLine extends BlockTypesetter {
    String[] cache;
    private int cacheIndex;

    public BT_SeparateLine(SupportedBT_Position position, Lexicon config) {
        super(position);
    }

    @Override
    protected int setType(Message message, int posLimit) {
        switch (position) {
            case UP  , DOWN  -> cache = new String[]{ String.valueOf('-').repeat(posLimit) };
            case LEFT, RIGHT -> {
                cache = new String[posLimit];
                Arrays.fill(cache, String.valueOf('|'));
            }
        }
        return 1;
    }

    @Override
    protected String getCache() {
        return  (position == SupportedBT_Position.UP || position == SupportedBT_Position.DOWN) ?
                "-" : "|";
    }
}

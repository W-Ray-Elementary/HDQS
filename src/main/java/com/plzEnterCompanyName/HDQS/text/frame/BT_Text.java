package com.plzEnterCompanyName.HDQS.text.frame;

import com.plzEnterCompanyName.HDQS.io.smartIO2.Message;
import com.plzEnterCompanyName.HDQS.util.Lexicon;

import java.util.Arrays;

public class BT_Text extends BlockTypesetter implements AdjustableBT {

    private int availSpace;
    private int cacheIndex;
    protected String[] cache;
    protected BT_Text(SupportedBT_Position position, Lexicon config) {
        super(position);
    }

    @Override
    protected int setType(Message message, int posLimit) {
        String $ = String.valueOf('$').repeat(posLimit);
        cache = new String[availSpace];
        Arrays.fill(cache, $);
        cacheIndex = 0;
        return availSpace;
    }

    @Override
    protected String getCache() {
        return cache[cacheIndex++];
    }

    @Override
    public void tellAvailSpace(int availSpace) {
        this.availSpace = availSpace;
    }

    @Override
    protected void nextPage() {
        cacheIndex = 0;
    }
}

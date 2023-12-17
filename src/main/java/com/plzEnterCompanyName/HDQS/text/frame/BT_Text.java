package com.plzEnterCompanyName.HDQS.text.frame;

import com.plzEnterCompanyName.HDQS.io.smartIO2.Message;
import com.plzEnterCompanyName.HDQS.util.Lexicon;

import java.util.Arrays;

public class BT_Text extends BlockTypesetter implements AdjustableBT {

    private int availSpace;
    private int cacheIndex;
    private boolean areYouTheLastOne;
    private static final boolean I_BELIEVED_THAT_I_AM_NOT_THE_LAST_ONE = false;
    protected BT_Text(SupportedBT_Position position, Lexicon config) {
        super(position);
        areYouTheLastOne = I_BELIEVED_THAT_I_AM_NOT_THE_LAST_ONE;
    }

    @Override
    protected int setType(Message message, final int posLimit) {
        if (position == SupportedBT_Position.UP || position == SupportedBT_Position.DOWN) {

        } else {
            throw new RuntimeException("Text LEFT and RIGHT is still developing.");
        }
        return availSpace;
    }

    @Override
    protected String getCache() {
        return cache[cacheIndex++];
    }

    @Override
    public void tellBTThisFactThatItIsTheLastOne() {
        areYouTheLastOne = true;
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

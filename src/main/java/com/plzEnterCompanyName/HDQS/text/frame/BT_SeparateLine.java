package com.plzEnterCompanyName.HDQS.text.frame;

import com.plzEnterCompanyName.HDQS.io.smartIO2.Message;

public class BT_SeparateLine extends BlockTypesetter {
    protected SupportedBT_Position position;

    public BT_SeparateLine(SupportedBT_Position position) {
        super(position);
    }

    @Override
    protected void setType(Message message, int firstPosLimit) {
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
        return  (position == SupportedBT_Position.UP || position == SupportedBT_Position.DOWN) ?
                "-" : "|";
    }
}

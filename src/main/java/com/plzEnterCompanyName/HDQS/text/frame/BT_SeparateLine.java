package com.plzEnterCompanyName.HDQS.text.frame;

import com.plzEnterCompanyName.HDQS.io.smartIO2.Message;

public class BT_SeparateLine extends BlockTypesetter {
    protected SupportedBT_Position position;

    public BT_SeparateLine(SupportedBT_Position position) {
        super(position);
    }

    @Override
    protected void setType(Message message, int firstPosLimit) {
        statusFlag = true;
    }

    @Override
    protected int getSecondPosLimit() {
        statusFlag = false;
        return 1;
    }
}

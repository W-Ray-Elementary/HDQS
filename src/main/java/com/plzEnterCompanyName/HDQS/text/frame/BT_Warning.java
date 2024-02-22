package com.plzEnterCompanyName.HDQS.text.frame;

import com.plzEnterCompanyName.HDQS.io.smartIO2.Message;
import com.plzEnterCompanyName.HDQS.util.Configuration;

public class BT_Warning extends BlockTypesetter {

    public BT_Warning(SupportedBT_Position position) {
        super(position);
    }

    protected BT_Warning(SupportedBT_Position position, Configuration config) {
        super(position);
    }

    @Override
    protected int setType(Message message, final int posLimit) {
        return 0;
    }

    @Override
    protected String getCache() {
        return null;
    }

    @Override
    protected void nextPage() {
        reset();
    }

    @Override
    protected void reset() {
    }
}

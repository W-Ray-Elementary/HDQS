package com.plzEnterCompanyName.HDQS.text.frame;

import com.plzEnterCompanyName.HDQS.io.smartIO2.Message;
import com.plzEnterCompanyName.HDQS.util.Lexicon;

public class BT_Warning extends BlockTypesetter {

    protected BT_Warning(SupportedBT_Position position, Lexicon config) {
        super(position);
    }

    @Override
    protected int setType(Message message, int posLimit) {
        return 1;
    }

    @Override
    protected String getCache() {
        return null;
    }
}

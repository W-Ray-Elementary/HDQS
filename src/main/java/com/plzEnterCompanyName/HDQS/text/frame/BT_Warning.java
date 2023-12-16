package com.plzEnterCompanyName.HDQS.text.frame;

import com.plzEnterCompanyName.HDQS.io.smartIO2.Message;
import com.plzEnterCompanyName.HDQS.util.Lexicon;

public class BT_Warning extends BlockTypesetter {

    protected BT_Warning(SupportedBT_Position position, Lexicon config) {
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
    protected void nextPage() {  }
}

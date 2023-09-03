package com.plzEnterCompanyName.HDQS.io.smartIO2;

public class BlankReceiver implements Receiver, VisibleReceiver{
    @Override
    public boolean look(String s) {
        return true;
    }

    @Override
    public String getReceived() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getTip() {
        return "";
    }
}

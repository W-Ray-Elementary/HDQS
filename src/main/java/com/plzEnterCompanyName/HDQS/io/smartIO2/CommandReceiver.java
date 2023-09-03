package com.plzEnterCompanyName.HDQS.io.smartIO2;

public class CommandReceiver implements Receiver {
    private static final String COMPLETE_PARAM = "/";
    String received;

    public CommandReceiver() {
    }

    @Override
    public boolean look(String s) {
        if (s.startsWith(COMPLETE_PARAM)) {
            received = s;
            return true;
        }
        return false;
    }

    @Override
    public String getReceived() {
        return received;
    }
}

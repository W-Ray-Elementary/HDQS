package com.plzEnterCompanyName.HDQS.io.smartIO2;

public class IntegerReceiver implements Receiver, VisibleReceiver {
    int completeVal;
    String received;

    public IntegerReceiver(int completeVal) {
        this.completeVal = completeVal;
    }

    @Override
    public boolean look(String s) {
        int receiveVal = Integer.parseInt(s);
        if (receiveVal == completeVal) {
            received = s;
            return true;
        }
        return false;
    }

    @Override
    public String getReceived() {
        return received;
    }

    @Override
    public String getTip() {
        return String.valueOf(completeVal);
    }
}

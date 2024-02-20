package com.plzEnterCompanyName.HDQS.io.smartIO2;

import com.plzEnterCompanyName.HDQS.util.FormatCheck;

import static com.plzEnterCompanyName.HDQS.util.FormatCheck.ZERO_LENGTH;
import static com.plzEnterCompanyName.HDQS.util.FormatCheck.NEW_LINE;
import static com.plzEnterCompanyName.HDQS.util.FormatCheck.NULL;

public class Operation {
    private final String name;
    private String lookedValue;
    private final Receiver receiver;


    public Operation(String name, Receiver receiver) {
        FormatCheck.specialString(name, NULL + ZERO_LENGTH + NEW_LINE);
        this.name = name;
        this.receiver = receiver;
    }

    public boolean look(String s) {
        boolean isReceived;
        isReceived = receiver.look(s);
        if (isReceived) {
            lookedValue = receiver.getReceived();
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public String getTip() {
        if (!isVisible())
            throw new UnsupportedOperationException();
        return ((VisibleReceiver)receiver).getTip();
    }

    public String getLookedValue() {
        return lookedValue;
    }

    public boolean isVisible() {
        return receiver instanceof VisibleReceiver;
    }
}

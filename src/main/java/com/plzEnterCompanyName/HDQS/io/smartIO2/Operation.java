package com.plzEnterCompanyName.HDQS.io.smartIO2;

public class Operation {
    private final String name;
    private String lookedValue;
    private final Receiver receiver;

    public Operation(String name, Receiver receiver) {
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

    public String getLookedValue() {
        return lookedValue;
    }

    public boolean isVisible() {
        return receiver instanceof VisibleReceiver;
    }
}

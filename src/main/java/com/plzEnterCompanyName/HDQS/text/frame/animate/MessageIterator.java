package com.plzEnterCompanyName.HDQS.text.frame.animate;

import com.plzEnterCompanyName.HDQS.io.smartIO2.Message;

public class MessageIterator {
    Message msg;
    char[][] chars;
    int line;
    int charNum;

    public MessageIterator(Message msg) {
        this.msg = msg;
        line = msg.texts.size();
        chars = new char[line][];
        for (int i = 0; i < msg.texts.size(); i++) {
            chars[i] = msg.texts.get(i).toCharArray();
        }
    }

    public boolean hasNext() {
        return false;
    }

    public Message next() {
        return null;
    }
}

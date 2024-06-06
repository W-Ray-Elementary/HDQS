package com.plzEnterCompanyName.HDQS.text.frame.animate;

import com.plzEnterCompanyName.HDQS.io.smartIO2.Message;
import com.plzEnterCompanyName.HDQS.io.smartIO2.MessageManager;

import java.util.ArrayList;
import java.util.List;

public class MessageIterator {
    Message msg;
    char[][] chars;
    final int line;
    int cLine;
    int charNum;

    public MessageIterator(Message msg) {
        this.msg = msg;
        line = msg.texts.size();
        cLine = 0;
        chars = new char[line][];
        for (int i = 0; i < msg.texts.size(); i++) {
            chars[i] = msg.texts.get(i).toCharArray();
        }
    }

    public boolean hasNext() {
        return cLine < line - 1 || charNum < chars[line - 1].length;
    }

    public Message next() {
        Message iteration = new MessageManager().toMessage();
        iteration.title = msg.title;
        iteration.infos = msg.infos;
        List<String> blankOperations = new ArrayList<>();
        blankOperations.add(" ");
        iteration.operations = blankOperations;
        iteration.advancedInfo = msg.advancedInfo;
        iteration.advancedInfo.remove("FrameAnimateType");
        if (charNum < chars[cLine].length) {
            charNum++;
        } else {
            if (cLine < chars.length - 1) {
                charNum = 0;
                cLine++;
            }
        }
        for (int i = 0; i < cLine; i++) {
            iteration.texts.add(msg.texts.get(i));
        }
        iteration.texts.add(new String(chars[cLine], 0, charNum));
        return iteration;
    }

    public Message getMsg() {
        return msg;
    }
}

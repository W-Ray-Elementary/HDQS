package com.plzEnterCompanyName.HDQS.io.smartIO2;

import com.plzEnterCompanyName.HDQS.util.FormatCheck;
import com.plzEnterCompanyName.HDQS.util.lexicon.Lexicon;

import java.util.ArrayList;
import java.util.List;

import static com.plzEnterCompanyName.HDQS.util.FormatCheck.NEW_LINE;
import static com.plzEnterCompanyName.HDQS.util.FormatCheck.NULL;

public class MessageManager {
    private static final String DEFAULT_BLANK_TEXT = "";
    private List<String> title;
    private List<String> texts;
    private int textIndex;
    private List<String> infos;
    private List<String> operations;
    private Lexicon advancedInfo;

    public MessageManager() {
        clean();
    }

    public void clean() {
        this.title = new ArrayList<>();
        this.title.add(DEFAULT_BLANK_TEXT);
        this.texts = new ArrayList<>();
        this.textIndex = 0;
        this.infos = new ArrayList<>();
        this.operations = new ArrayList<>();
        this.advancedInfo = new Lexicon("AdvancedInfo");
    }

    public MessageManager(Message message) {
        this.title = message.title;
        this.texts = message.texts;
        this.textIndex = message.texts.size();
        this.infos = message.infos;
        this.operations = message.operations;
        this.advancedInfo = message.advancedInfo;
    }

    public Message toMessage() {
        Message returnVal = new Message(
                this.title,
                this.texts,
                this.infos,
                this.operations,
                this.advancedInfo);
        clean();
        return returnVal;

    }

    public void title(String s) {
        title(s, false);
    }

    public void title(String s, boolean append) {
        FormatCheck.specialString(s, NULL + NEW_LINE);
        String org = this.title.get(0);
        if (append) org += s;
        else org = s;
        this.title.set(0, org);
    }

    public void text() {
        text(DEFAULT_BLANK_TEXT);
    }

    public void text(String s) {
        text(s, textIndex++);
    }

    public void text(String s, int index) {
        text(s, index, false);
    }

    public void text(String s, int index, boolean append) {
        FormatCheck.specialString(s, NULL);
        ensureTextIndex(this.texts, index);
        if (append) this.texts.set(index, this.texts.get(index) + s);
        else this.texts.set(index, s);
    }

    private void ensureTextIndex(List<String> texts, int index) {
        if (index < texts.size()) return;
        // 不小于不就是>=吗，算了不改了。
        while (!(index < texts.size()))
            texts.add(DEFAULT_BLANK_TEXT);
    }

    public void info(String str) {
        infos.add(str);
    }

    public void operation(String s) {
        this.operations.add(s);
    }
}

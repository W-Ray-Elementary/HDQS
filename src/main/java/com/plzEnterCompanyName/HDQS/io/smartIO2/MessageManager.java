package com.plzEnterCompanyName.HDQS.io.smartIO2;

import com.plzEnterCompanyName.HDQS.util.FormatCheck;
import com.plzEnterCompanyName.HDQS.util.LEXICON;

import static com.plzEnterCompanyName.HDQS.util.FormatCheck.NEW_LINE;
import static com.plzEnterCompanyName.HDQS.util.FormatCheck.NULL;

import java.util.ArrayList;
import java.util.List;

public class MessageManager {
    private static final String DEFAULT_BLANK_TEXT = "";
    private List<String> title;
    private final List<String> texts;
    private int textIndex;
    private final List<Info> infos;
    private int infoIndex;
    private final List<Operation> operations;
    private LEXICON advancedInfo;

    public MessageManager() {
        this.title = new ArrayList<>();
        title.add(DEFAULT_BLANK_TEXT);
        this.texts = new ArrayList<>();
        this.textIndex = 0;
        this.infos = new ArrayList<>();
        this.infoIndex = 0;
        this.operations = new ArrayList<>();
    }

    public Message toMessage() {
        return new Message(title,
                           texts,
                           infos,
                           operations,
                           advancedInfo);
    }

    public void title(String s) {
        title(s, false);
    }

    public void title(String s, boolean append) {
        FormatCheck.specialString(s, NULL + NEW_LINE);
        String org = title.get(0);
        if (append) org += s;
        else org = s;
        title.set(0, org);
    }

    public void text(String s) {
        text(s, textIndex++);
    }

    public void text(String s, int index) {
        text(s, index, false);
    }

    public void text(String s, int index, boolean append) {
        FormatCheck.specialString(s, NULL);
        ensureTextIndex(texts, index);
        if (append) texts.set(index, texts.get(index) + s);
        else texts.set(index, s);
    }

    private void ensureTextIndex(List<String> texts, int index) {
        if (index < texts.size()) return;
        while (!(index < texts.size()))
            texts.add(DEFAULT_BLANK_TEXT);
    }

    public void info(Info info) {
        info(info, infoIndex++);
    }

    public void info(Info info, int index) {
        infos.set(index, info);
    }

    public void addOperation(Operation ort) {
        operations.add(ort);
    }

    public void removeOperation(Operation ort) {
        operations.remove(ort);
    }
}

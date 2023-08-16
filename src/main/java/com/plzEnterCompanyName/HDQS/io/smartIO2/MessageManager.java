package com.plzEnterCompanyName.HDQS.io.smartIO2;

import com.plzEnterCompanyName.HDQS.util.LEXICON;

import java.util.ArrayList;
import java.util.List;

public class MessageManager {
    private static final String DEFAULT_BLANK_TEXT = "";
    private String title;
    private final List<String> texts;
    private int textIndex;
    private final List<Info> infos;
    private int infoIndex;
    private final List<Operation> operations;
    private LEXICON advancedInfo;

    public MessageManager() {
        this.title = DEFAULT_BLANK_TEXT;
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
        if (append) title += s;
        else title = s;
    }

    public void text(String s) {
        text(s, textIndex++);
    }

    public void text(String s, int index) {
        text(s, index, false);
    }

    public void text(String s, int index, boolean append) {
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

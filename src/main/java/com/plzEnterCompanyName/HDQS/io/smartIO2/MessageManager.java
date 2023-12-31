package com.plzEnterCompanyName.HDQS.io.smartIO2;

import com.plzEnterCompanyName.HDQS.util.FormatCheck;
import com.plzEnterCompanyName.HDQS.util.Lexicon;

import java.util.ArrayList;
import java.util.List;

import static com.plzEnterCompanyName.HDQS.util.FormatCheck.NEW_LINE;
import static com.plzEnterCompanyName.HDQS.util.FormatCheck.NULL;

public class MessageManager {
    private static final String DEFAULT_BLANK_TEXT = "";
    private static final Info DEFAULT_BLANK_INFO =
            new Info("", "", false);
    private List<String> title;
    private List<String> texts;
    private int textIndex;
    private List<Info> infos;
    private int infoIndex;
    private List<Operation> operations;
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
        this.infoIndex = 0;
        this.operations = new ArrayList<>();
        this.operationIndex = 1;
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
        while (!(index < texts.size()))
            texts.add(DEFAULT_BLANK_TEXT);
    }

    public void info(String str) {
        info(new Info(str, "", false));
    }

    public void info(Info info) {
        info(info, infoIndex++);
    }

    public void info(Info info, int index) {
        ensureInfoIndex(this.infos, index);
        this.infos.set(index, info);
    }

    private void ensureInfoIndex(List<Info> infos, int index) {
        if (index < infos.size()) return;
        while (!(index < infos.size()))
            infos.add(DEFAULT_BLANK_INFO);
    }

    private int operationIndex;

    public void operation(String s) {
        this.operations.add(new Operation(s, new IntegerReceiver(operationIndex++)));
    }

    public void addOperation(Operation ort) {
        this.operations.add(ort);
    }

    public void removeOperation(Operation ort) {
        this.operations.remove(ort);
    }
}

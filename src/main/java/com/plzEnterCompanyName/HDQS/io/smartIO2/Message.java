package com.plzEnterCompanyName.HDQS.io.smartIO2;

import com.plzEnterCompanyName.HDQS.util.lexicon.Lexicon;

import java.util.List;

public class Message {
    public List<String> title;
    public List<String> texts;
    public List<Info> infos;
    public List<Operation> operations;
    public Lexicon advancedInfo;

    protected Message(List<String> title, List<String> texts, List<Info> infos,
                   List<Operation> operations, Lexicon advancedInfo)
    {
        this.title = title;
        this.texts = texts;
        this.infos = infos;
        this.operations = operations;
        this.advancedInfo = advancedInfo;
    }
}

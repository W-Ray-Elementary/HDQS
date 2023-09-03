package com.plzEnterCompanyName.HDQS.io.smartIO2;

import com.plzEnterCompanyName.HDQS.util.LEXICON;

import java.util.List;

public class Message {
    public List<String> title;
    public List<String> texts;
    public List<Info> infos;
    public List<Operation> operations;
    public LEXICON advancedInfo;

    protected Message(List<String> title, List<String> texts, List<Info> infos,
                   List<Operation> operations, LEXICON advancedInfo)
    {
        this.title = title;
        this.texts = texts;
        this.infos = infos;
        this.operations = operations;
        this.advancedInfo = advancedInfo;
    }
}

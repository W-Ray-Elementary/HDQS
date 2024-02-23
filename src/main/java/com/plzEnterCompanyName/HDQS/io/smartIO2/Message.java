package com.plzEnterCompanyName.HDQS.io.smartIO2;

import com.plzEnterCompanyName.HDQS.util.Configuration;

import java.util.List;

/**
 * 把需要输出的内容临时存储起来，此外，还提供了一个
 */
public class Message {

    /**
     *
     */
    public List<String> title;

    /**
     *
     */
    public List<String> texts;

    /**
     *
     */
    public List<String> infos;

    /**
     *
     */
    public List<String> operations;

    /**
     *
     */
    public Configuration advancedInfo;

    protected Message(List<String> title, List<String> texts, List<String> infos, List<String> operations,
                      Configuration advancedInfo) {
        this.title = title;
        this.texts = texts;
        this.infos = infos;
        this.operations = operations;
        this.advancedInfo = advancedInfo;
    }
}

package com.plzEnterCompanyName.HDQS.io.smartIO2;

import java.util.List;
import java.util.Properties;

/**
 * 把需要输出的内容临时存储起来，此外，还提供了一个放置更多信息的属性。
 */
public class Message {

    /**
     * 虽然是一个{@code List}，但是建议只放入一个元素，因为一般情况下标题只有一行，
     * 并且非常不推荐在这里放任何换行符。
     */
    public List<String> title;

    /**
     * 一般用来存放正文内容。正文是指游戏中可能出现的用于告诉玩家当前正在发生的事件一类
     * 字符串。可以进行换行。
     */
    public List<String> texts;

    /**
     * info指的是简短的，精炼的，能够告诉玩家目前游戏的状态和玩家对象的各类数值。推荐
     * 的格式是：<br>
     * [属性名]   [属性值]<br>
     * 不建议在其中包含换行符。
     */
    public List<String> infos;

    /**
     * 这是指导玩家输入数值的文字。
     */
    public List<String> operations;

    /**
     * 为了提高{@code Message}的拓展性，可以针对不同的程序模块添加值。
     */
    public Properties advancedInfo;

    protected Message(List<String> title, List<String> texts, List<String> infos, List<String> operations,
                      Properties advancedInfo) {
        this.title = title;
        this.texts = texts;
        this.infos = infos;
        this.operations = operations;
        this.advancedInfo = advancedInfo;
    }
}

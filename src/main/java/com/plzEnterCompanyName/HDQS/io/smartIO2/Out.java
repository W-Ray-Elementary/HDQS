package com.plzEnterCompanyName.HDQS.io.smartIO2;

/**
 * 在一次“读取-求值-输出”循环中，“输出”的目的是获取“输入”
 */
public interface Out {

    /**
     * 普通输出
     *
     * @param msg 其中信息应被优美地排版
     */
    void out(Message msg);

    /**
     * 重复输出，Out的实现类理应保存好上一次输出时的Message，如果
     * 之前没有输出过的话就输出一片空白。
     */
    void repeatOut();
}

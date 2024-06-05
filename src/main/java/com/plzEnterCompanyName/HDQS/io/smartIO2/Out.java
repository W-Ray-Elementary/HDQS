package com.plzEnterCompanyName.HDQS.io.smartIO2;

public interface Out {
    void out(Message msg);

    /**
     * 重复输出，Out的实现类理应保存好上一次输出时的Message，如果
     * 之前没有输出过的话就输出一片空白。
     */
    void repeatOut();
}

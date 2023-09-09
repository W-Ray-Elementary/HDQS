package com.plzEnterCompanyName.HDQS.text.frame;

import com.plzEnterCompanyName.HDQS.io.smartIO2.Message;

/**
 * 块排版器
 *
 */
public abstract class BlockTypesetter {
    protected SupportedBT_Position position;
    protected int firstPosLimit;

    /**
     * 表示BC当前状态的一个量。
     * 在构造对象之后，{@code statusFlag}被赋值为{@code false}
     * 在排版之后，{@code statusFlag}被赋值为{@code true}
     * 获取secondPosLimit之前，判断{@code statusFlag}的值是否为{@code false}
     * 在获取secondPosLimit之后{@code statusFlag}被赋值为{@code false}
     */
    protected boolean statusFlag;

    protected abstract void setType(Message message, int firstPosLimit);

    protected BlockTypesetter(SupportedBT_Position position) {
        this.position = position;
        this.statusFlag = false;
    }

    protected abstract int getSecondPosLimit();
}

package com.plzEnterCompanyName.HDQS.text.frame;

import com.plzEnterCompanyName.HDQS.io.smartIO2.Message;

/**
 * 块排版器
 *
 */
public abstract class BlockTypesetter {
    protected SupportedBT_Position position;

    /**
     * 表示BC当前状态的一个量。
     * 在构造对象之后，{@code isTyped}被赋值为{@code false}
     * 在排版之后，{@code isTyped}被赋值为{@code true}
     * 获取secondPosLimit之前，判断{@code isTyped}的值是否为{@code false}
     * 在获取secondPosLimit之后{@code isTyped}被赋值为{@code false}
     */
    protected boolean isTyped;

    protected static final String untypedMsg = "Calling methods at the wrong time.";
    protected static final String spaceInsufficientMsg = "Screen space is insufficient, " +
            "please try to adjust settings in \"settings\\frame.cfg\"";
    protected String[] cache;

    protected BlockTypesetter(SupportedBT_Position position) {
        this.position = position;
        this.isTyped = false;
    }

    protected abstract void setType(Message message, int firstPosLimit);

    protected abstract int getSecondPosLimit();

    protected abstract String getCache();
}

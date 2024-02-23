package com.plzEnterCompanyName.HDQS.text.frame;

/**
 * 可调节输出的BlockTypesetter
 *
 * <p>实现了此接口的BlockTypesetter，通常被放在Layout里的最后一个。
 */
public interface AdjustableBT {

    /**
     * 告诉它，你是最后一个。
     */
    void tellBTThisFactThatItIsTheLastOne();

    /**
     * 通过这个方法，Layout就可以告诉这个BlockTypesetter还有多少空间可用。
     */
    void tellAvailSpace(int availSpace);
}

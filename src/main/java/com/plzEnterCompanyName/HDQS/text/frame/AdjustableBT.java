package com.plzEnterCompanyName.HDQS.text.frame;

/**
 * 可调节输出的BlockTypesetter
 *
 * <p>例如，对于{@link BT_Text}这一典型的实现了该接口的类，它有时会因为屏幕
 * 空间不足而无法显示完全，这时就需要进行类似换页的操作。
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

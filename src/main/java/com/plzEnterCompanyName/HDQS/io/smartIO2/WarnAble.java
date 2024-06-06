package com.plzEnterCompanyName.HDQS.io.smartIO2;

/**
 * 有能力对玩家进行Warn的Out，可以选择实现该接口。
 */
public interface WarnAble {

    /**
     * 发出警告信息。
     *
     * @param warnStr 警告信息。
     */
    void warn(String warnStr);
}

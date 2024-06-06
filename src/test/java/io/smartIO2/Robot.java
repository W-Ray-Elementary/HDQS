package io.smartIO2;

import com.plzEnterCompanyName.HDQS.io.smartIO2.In;
import com.plzEnterCompanyName.HDQS.io.smartIO2.Message;
import com.plzEnterCompanyName.HDQS.io.smartIO2.Out;
import com.plzEnterCompanyName.HDQS.io.smartIO2.WarnAble;

public class Robot implements In, Out, WarnAble {

    public String NEXT;
    public boolean isWarned;

    public Robot() {
        isWarned = false;
    }

    @Override
    public String next() {
        return NEXT;
    }

    @Override
    public void blankOperate() {

    }

    @Override
    public void out(Message msg) {

    }

    @Override
    public void repeatOut() {

    }

    @Override
    public void warn(String warnStr) {
        isWarned = true;
        NEXT = "1";
    }
}

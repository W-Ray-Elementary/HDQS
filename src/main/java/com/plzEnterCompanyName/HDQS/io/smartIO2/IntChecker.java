package com.plzEnterCompanyName.HDQS.io.smartIO2;

public class IntChecker implements Checker {
    @Override
    public boolean pass(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public String noPassMsg() {
        return "请正确输入数值！";
    }
}

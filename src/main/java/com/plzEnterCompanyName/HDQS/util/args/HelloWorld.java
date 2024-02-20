package com.plzEnterCompanyName.HDQS.util.args;

import java.util.Map;

import com.plzEnterCompanyName.HDQS.Start;

public class HelloWorld implements Argument {

    @Override
    public String getParam() {
        return "-helloworld";
    }

    @Override
    public Map<String, OptionType> getOptionsInfo() {
        return NO_OPTION;
    }

    @Override
    public Start process(String[] options) {
        return () -> {
            System.out.println("Hello world!");
        };
    }
}

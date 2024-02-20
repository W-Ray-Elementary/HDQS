package com.plzEnterCompanyName.HDQS.util.args;

import java.util.HashMap;
import java.util.Map;

import com.plzEnterCompanyName.HDQS.Start;

public interface Argument {
    Map<String, OptionType> NO_OPTION = new HashMap<>();
    String getParam();
    Map<String, OptionType> getOptionsInfo();
    Start process(String[] options);
}

package com.plzEnterCompanyName.HDQS.util;

import java.util.List;

public interface Configuration {
    String getName();

    int getInt(String key);

    boolean getBoolean(String key);

    String get(String key);

    List<String> getList(String key);

    List<Configuration> subSets(String key);
}

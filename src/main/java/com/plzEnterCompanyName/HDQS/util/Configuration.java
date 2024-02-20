package com.plzEnterCompanyName.HDQS.util;

import java.util.List;

/**
 * 
 */
public interface Configuration {
    int getInt(String key);
    String get(String key);
    List<String> getList(String key);
    List<Configuration> subSets(String key);
}

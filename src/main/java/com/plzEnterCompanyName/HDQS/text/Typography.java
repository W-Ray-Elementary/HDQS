package com.plzEnterCompanyName.HDQS.text;

import java.util.List;

public interface Typography {
    List<String> lineBreak(String s, int width);

    List<String> lineBreak(String s, int width, boolean isAddEndBlanks);
}

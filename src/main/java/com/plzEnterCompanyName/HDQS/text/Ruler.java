package com.plzEnterCompanyName.HDQS.text;

public interface Ruler {
    int measureWidth(char c);
    int measureWidth(String s);
    String repeatW(String s, int availWidth);
    String repeatW(char c, int availWidth);
}

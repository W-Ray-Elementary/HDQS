package com.plzEnterCompanyName.HDQS.util.lexicon;

/* 懒得写注释了 */
public class Content {

    String key;
    Object value;
    boolean isLEXICON;

    public Content(String key, String value) {
        this.key = key;
        this.value = value;
        this.isLEXICON = false;
    }

    public Content(Lexicon lexicon) {
        this.key = lexicon.name;
        this.value = lexicon;
        this.isLEXICON = true;
    }

    public int lineCount() {
        if (isLEXICON) {
            return ((Lexicon)value).lineCount();
        } else {
            return 1;
        }
    }
}

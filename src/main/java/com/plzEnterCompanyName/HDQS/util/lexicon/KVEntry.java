package com.plzEnterCompanyName.HDQS.util.lexicon;

public class KVEntry {

    protected String key;
    protected Object value;

    public KVEntry(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }

    /* 一个空KV是不成立的 */
    private KVEntry() {}
}

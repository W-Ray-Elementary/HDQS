package com.plzEnterCompanyName.HDQS.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 原版Lexicon的功能太少了，用着不方便！
 */
public class Lexicons {
    /* No Lexicons instance for you */
    private Lexicons() {
        throw new UnsupportedOperationException();
    }

    /**
     * 点名！
     *
     * <p>
     * 把指定name属性的子Lexicon从父Lexicon中返回。
     *
     * @param parent 要查找的父Lexicon
     * @param moduleType 子Lexicon大括号前面的那个东西
     * @param name 要查找的子Lexicon的里面的name属性
     * @return 如果有符合name条件的子Lexicon，第一个将会被返回
     */
    public static Lexicon orderName(Lexicon parent, String moduleType, String name) {
        Objects.requireNonNull(parent);
        FormatCheck.specialString(moduleType,
                FormatCheck.ZERO_LENGTH + FormatCheck.NULL + FormatCheck.NEW_LINE);
        FormatCheck.specialString(name,
                FormatCheck.ZERO_LENGTH + FormatCheck.NULL + FormatCheck.NEW_LINE);
        Lexicon child = null;
        List<Object> contentsObjs = new ArrayList<>(List.of(parent.getAll(moduleType)));
        for (Object contentObj : contentsObjs) {
            if (contentObj instanceof Lexicon l) {
                try {
                    if (name.equals(l.getFirst("name")))
                        child = l;
                } catch (ContentNotFoundException ignored) {  }
            }
        }
        if (child == null) {
            throw new ContentNotFoundException("Cannot fount " + name);
        }
        return child;
    }
}

package com.plzEnterCompanyName.HDQS.util.lexicon;

import com.plzEnterCompanyName.HDQS.util.FormatCheck;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 原版Lexicon的功能太少了，用着不方便！
 * </p>
 * <p>
 * 有些东西是不要测试的， <br>
 * 尤其是在java里， <br>
 * gc会为我们保驾护航的， <br>
 * 对吧？
 * </p>
 */
public class Lexicons {
    /* No Lexicons instance for you */
    private Lexicons() {
        throw new UnsupportedOperationException();
    }

    /**
     * 列出来！（累了）
     */
    public static List<Lexicon> listOut(Lexicon parent, String name) {
        Objects.requireNonNull(parent);
        FormatCheck.specialString(name,
                FormatCheck.ZERO_LENGTH + FormatCheck.NULL + FormatCheck.NEW_LINE);
        List<Object> contentsObjs = new ArrayList<>(List.of(parent.getAll(name)));
        List<Lexicon> ls = new ArrayList<>();
        for (Object contentObj : contentsObjs) {
            if (contentObj instanceof Lexicon l) {
                ls.add(l);
            }
        }
        return ls;
    }
}

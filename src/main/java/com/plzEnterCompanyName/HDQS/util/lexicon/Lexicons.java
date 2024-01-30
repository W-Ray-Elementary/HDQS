package com.plzEnterCompanyName.HDQS.util.lexicon;

import com.plzEnterCompanyName.HDQS.util.FormatCheck;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 原版Lexicon的功能太少了，用着不方便！
 * <p>有些东西是不要测试的，
 *    尤其是在java里，
 *    gc会为我们保驾护航的，
 *    对吧？
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
     * @throws ContentNotFoundException 找不到就抛异常！
     */
    public static Lexicon strictOrderName(Lexicon parent, String moduleType, String name) {
        List<Lexicon> ls = Lexicons.listOut(parent, moduleType);
        Lexicon l = orderName(ls, moduleType, name);
        if (l == null) throw new ContentNotFoundException("Cannot found " + name);
        return l;
    }

    /**
     * 点名！
     * 我知道我写的东西有冗余，但对于一个初学者来说，这没办法
     * <p>
     * 把指定name属性的子Lexicon从父Lexicon中返回。
     *
     * @param parents 要查找的元素们
     * @param moduleType 子Lexicon大括号前面的那个东西
     * @param name 要查找的子Lexicon的里面的name属性
     * @return 如果有符合name条件的子Lexicon，第一个将会被返回
     * @throws ContentNotFoundException 找不到就抛异常！
     */
    public static Lexicon strictOrderName(List<Lexicon> parents, String moduleType, String name) {
        Lexicon l = orderName(parents, moduleType, name);
        if (l == null) throw new ContentNotFoundException("Cannot found " + name);
        return l;
    }

    /**
     * 点名！
     * 我知道我写的东西有冗余，但对于一个初学者来说，这没办法
     * <p>
     * 把指定name属性的子Lexicon从父Lexicon中返回。
     *
     * @param parents 要查找的元素们
     * @param moduleType 子Lexicon大括号前面的那个东西
     * @param name 要查找的子Lexicon的里面的name属性
     * @return 如果有符合name条件的子Lexicon，第一个将会被返回
     *         如果找不到，那也没办法，返回null吧。
     */
    public static Lexicon orderName(List<Lexicon> parents, String moduleType, String name) {
        Objects.requireNonNull(parents);
        FormatCheck.specialString(moduleType,
                FormatCheck.ZERO_LENGTH + FormatCheck.NULL + FormatCheck.NEW_LINE);
        FormatCheck.specialString(name,
                FormatCheck.ZERO_LENGTH + FormatCheck.NULL + FormatCheck.NEW_LINE);
        Lexicon child = null;
        for (Lexicon l : parents) {
            try {
                if (name.equals(l.getFirst("name")))
                    child = l;
            } catch (ContentNotFoundException ignored) {  }
        }
        return child;
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

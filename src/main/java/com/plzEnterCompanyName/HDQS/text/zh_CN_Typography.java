package com.plzEnterCompanyName.HDQS.text;

import java.util.ArrayList;
import java.util.List;

public class zh_CN_Typography implements Typography {
    private final Ruler ruler;
    private static final char[] NO_FIRST_CHARS = "!%%),.:;>?]}¢¨°·ˇˉ―‖’”…‰′″›℃∶、。〃〉》」』】〕〗〞︶︺︾﹀﹄﹚﹜﹞！＂％＇），．：；？］｀｜｝～￠".toCharArray();
    private static final char[] NO_LAST_CHARS = "$([{£¥·‘“〈《「『【〔〖〝﹙﹛﹝＄（．［｛￡￥".toCharArray();
    private static final char[] NO_BREAK_CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ—…".toCharArray();
    private static final char[] IGNORED_CHARS = " ".toCharArray();
    public zh_CN_Typography(Ruler ruler) {
        this.ruler = ruler;
    }

    /**
     * 不想重复写注释，详见{@link zh_CN_Typography#lineBreak(String, int, boolean) 此处}
     */
    public List<String> lineBreak(String s, int width) {
        return lineBreak(s, width, true);
    }

    /**
     * 按照中文排版习惯进行断行，有如下原则
     * <blockquote><pre>
     *     1. 当字符串宽度超过限度时，进行断行，若断行后仍然超出限度，则继续断行；
     *     2. 断行后若出现不规范的标点符号使用，则从上一行寻找可断行处进行断行。
     * </pre></blockquote>
     *
     * @param s 要进行断行的字符串
     * @param width 进行断行时遵循的宽度，由Ruler测量值为准进行比较
     * @param isAddEndBlanks 是否要在断行完毕的字符串尾部添加空格
     * @return 完成断行的字符串
     */
    public List<String> lineBreak(String s, final int width, boolean isAddEndBlanks) {
        if (width < 2) throw new IllegalArgumentException("width: " + width + " is too short.");
        List<String> returnVal = new ArrayList<>();
        if (s.isEmpty()) {
            String emptyLine;
            if (isAddEndBlanks) emptyLine = ruler.repeatW(' ', width);
            else emptyLine = "";
            returnVal.add(emptyLine);
            return  returnVal;
        }
        char[] chars = s.toCharArray();
        int availWidth = width;
        int canPressEnter = 0;
        int enterMarker = 0;
        String currentLine;
        for (int i = 0; i < chars.length; i++) {
            Character last = null;
            if (i != 0) last = chars[i-1];
            char current = chars[i];
            int usage = last == null ? 0 : ruler.measureWidth(last);
            if (usage > availWidth || current == '\n') {
                if (current == '\n') {
                    currentLine = s.substring(enterMarker, i);
                    enterMarker = i + 1;
                    canPressEnter = i;
                } else if (enterMarker == canPressEnter) {
                    // 在这里无论如何断行，都一定会违反语法
                    // 所以这里要把语法废除（bushi）
                    currentLine = s.substring(enterMarker, i - 1);
                    enterMarker = i - 1;
                    canPressEnter = i - 1;
                } else {
                    currentLine = s.substring(enterMarker, canPressEnter);
                    enterMarker = canPressEnter;
                    String ignoreCharCriterion = s.substring(canPressEnter, i);
                    while (contains(IGNORED_CHARS, ignoreCharCriterion.charAt(0))) {
                        ignoreCharCriterion = ignoreCharCriterion.substring(1);
                        enterMarker++;
                        if (ignoreCharCriterion.isEmpty()) break;
                    }
                }
                if (isAddEndBlanks) currentLine = makeSureSameWidth(currentLine, width);
                returnVal.add(currentLine);
                availWidth = width;
                if (canPressEnter == i) {
                    if (!(i + 1 == chars.length)) {
                        String breakLine = s.substring(i + 1, i + 3);
                        int rest = ruler.measureWidth(breakLine);
                        availWidth += rest;
                    }
                } else {
                    String addonLine = s.substring(canPressEnter+1, i);
                    int future = ruler.measureWidth(addonLine);
                    availWidth -= future;
                }
            }
            availWidth -= usage;
            if (chars.length == 1) break;
            if (contains(NO_BREAK_CHARS, last) && contains(NO_BREAK_CHARS, current))
                continue;
            if (contains(NO_FIRST_CHARS, current))
                continue;
            if (contains(NO_LAST_CHARS, last))
                continue;
            canPressEnter = i;
        }
        currentLine = s.substring(enterMarker);
        if (enterMarker == canPressEnter) {
            canPressEnter = chars.length-1;
        }
        if (ruler.measureWidth(currentLine) > width) {
            String line1 = s.substring(enterMarker, canPressEnter);
            if (isAddEndBlanks) line1 = makeSureSameWidth(line1, width);
            String line2 = s.substring(canPressEnter);
            if (isAddEndBlanks) line2 = makeSureSameWidth(line2, width);
            returnVal.add(line1);
            returnVal.add(line2);
        } else {
            if (isAddEndBlanks) currentLine = makeSureSameWidth(currentLine, width);
            returnVal.add(currentLine);
        }
        return returnVal;
    }

    private static boolean contains(char[] cs, Character objC) {
        if (objC == null) {
            return false;
        }
        char c = objC;
        for (char csc : cs)
            if (csc == c) return true;
        return false;
    }

    private String makeSureSameWidth(String s, int width) {
        int usage = ruler.measureWidth(s);
        if (usage < width) return s + ruler.repeatW(' ', width - usage);
        return s;
    }
}

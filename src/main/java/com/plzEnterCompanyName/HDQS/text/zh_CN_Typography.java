package com.plzEnterCompanyName.HDQS.text;

import java.util.ArrayList;
import java.util.List;

public class zh_CN_Typography implements Typography {
    private final Ruler ruler;
    private static final char[] noFirstChars = "!%%),.:;>?]}¢¨°·ˇˉ―‖’”…‰′″›℃∶、。〃〉》」』】〕〗〞︶︺︾﹀﹄﹚﹜﹞！＂％＇），．：；？］｀｜｝～￠".toCharArray();
    private static final char[] noLastChars = "$([{£¥·‘“〈《「『【〔〖〝﹙﹛﹝＄（．［｛￡￥".toCharArray();
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
    public List<String> lineBreak(String s, int width, boolean isAddEndBlanks) {
        List<String> returnVal = new ArrayList<>();
        List<StringBuilder> lines = new ArrayList<>();
        char[] chars = s.toCharArray();
        int availWidth = width;
        lines.add(new StringBuilder());
        int lineCount = 0;
        for (int i = 0; i < chars.length; i++) {
            int neededWidth = ruler.measureWidth(chars[i]);
            if (neededWidth > availWidth) {
                if (isAddEndBlanks)
                    lines.get(lineCount).append(ruler.repeatW(" ", availWidth));
                lines.add(new StringBuilder());
                lineCount++;
                availWidth = width;
            }
            lines.get(lineCount).append(chars[i]);
            availWidth -= neededWidth;
        }
        if (isAddEndBlanks)
            lines.get(lineCount).append(ruler.repeatW(" ", availWidth));
        for (StringBuilder sb : lines) {
            returnVal.add(sb.toString());
        }
        return returnVal;
    }
}

package com.plzEnterCompanyName.HDQS.text;

import com.plzEnterCompanyName.HDQS.RequireBoot;
import com.plzEnterCompanyName.HDQS.util.FormatCheck;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * For Windows ONLY!!!
 */
public class AwtRuler implements Ruler, RequireBoot {

    @Override
    public void boot(LinePrinter lp) {
        lp.printb(getClass().getName());
        bootMetrics = getMetrics();
    }

    private static FontMetrics getMetrics() {
        Font font = new Font("新宋体", Font.PLAIN, 12);
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        return g.getFontMetrics(font);
    }

    private static FontMetrics bootMetrics;

    private final FontMetrics metrics;

    public AwtRuler() {
        if (bootMetrics == null) {
            metrics = getMetrics();
        } else {
            metrics = bootMetrics;
        }
    }

    /**
     * 获取一个字符的显示宽度
     * 
     * @return 宽度以int计，1代表单个西文字符2代表单个中文字符
     */
    @Override
    public int measureWidth(char c) {
        String text = String.valueOf(c);
        int width = metrics.stringWidth(text);
        if (width == 12)
            width = 2;
        else if (width == 6)
            width = 1;
        else if (width == 0)
            width = 1;
        else if (width == 24)
            width = 1;
        else
            throw new RuntimeException(String.valueOf(width));
        return width;
    }

    /**
     * 获取该字符串的显示宽度，使用了{@link Ruler#measureWidth(char c)}
     * 
     * @param s 要测量宽度的字符串
     * @return 宽度以int计，输出该字符串每个字符宽度的总和，1代表单个西文字符2代表单个中文字符
     * @throws IllegalArgumentException 当字符串是{@code null}时抛出此异常，当字符串包含
     *                                  换行符时抛出此异常
     */
    @Override
    public int measureWidth(String s) {
        if (s.isEmpty())
            return 0;
        FormatCheck.specialString(s,
                FormatCheck.NULL + FormatCheck.NEW_LINE);
        char[] chars = s.toCharArray();
        int returnVal = 0;
        for (char c : chars) {
            returnVal += measureWidth(c);
        }
        return returnVal;
    }

    /**
     * 不断重复字符串，直到达到指定的宽度，若遇到无法整除的中文，会自动添加空格
     * <p>
     * 例如，该方法{@code repeatW("ab", 3)}会返回{@code "aba"}，
     * 而{@code repeatW("ab稀滴", 11)}会返回{@code "ab稀滴ab稀 "}.在尾部添加空格以满足宽度要求。
     * 
     * @param s     被重复的字符串
     * @param width 要达到的宽度
     * @return 处理好的字符串
     * @throws IllegalArgumentException 当字符串长度为零时抛出此异常，
     *                                  当字符串包含换行符时抛出此异常
     */
    public String repeatW(String s, int width) {
        if (s == null)
            s = "null";
        FormatCheck.specialString(s,
                FormatCheck.NULL + FormatCheck.ZERO_LENGTH + FormatCheck.NEW_LINE);
        if (width == 0)
            return "";
        FormatCheck.intLimit(width, 1, Integer.MAX_VALUE);
        StringBuilder sb = new StringBuilder();
        char[] c = s.toCharArray();
        int index = 0;
        while (true) {
            if (width == 0)
                break;
            if (width == 1) {
                if (measureWidth(c[index]) == 1) {
                    sb.append(c[index]);
                }
                if (measureWidth(c[index]) == 2) {
                    sb.append(' ');
                }
                break;
            }
            sb.append(c[index]);
            width -= measureWidth(c[index]);
            if (index + 1 == c.length) {
                index = 0;
            } else {
                index++;
            }
        }
        return sb.toString();
    }

    @Override
    public String repeatW(char c, int availWidth) {
        return repeatW(String.valueOf(c), availWidth);
    }
}

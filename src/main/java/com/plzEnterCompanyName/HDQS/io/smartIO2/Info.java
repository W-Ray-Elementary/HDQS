package com.plzEnterCompanyName.HDQS.io.smartIO2;

import com.plzEnterCompanyName.HDQS.util.FormatCheck;

import static com.plzEnterCompanyName.HDQS.util.FormatCheck.ZERO_LENGTH;
import static com.plzEnterCompanyName.HDQS.util.FormatCheck.NEW_LINE;
import static com.plzEnterCompanyName.HDQS.util.FormatCheck.NULL;
/**
 * 如果使用本类显示数值，那么最好应该传入整数。
 * 注意，此类仅用于处理显示，不参与计算
 */
public class Info {
    private static final String SCIENTIFIC_NOTATION_PARAM = "e+";
    private static final String POINT = ".";
    private final String name;
    private String value;
    private final boolean isInteger;
    private int width;
    private static final int MIN_WIDTH = 3;

    public Info(String name, int i, int width) {
        this(name, String.valueOf(i), width);
    }

    public Info(String name, String value, boolean isInteger) {
        this.name = name;
        this.value = value;
        this.isInteger = isInteger;
    }

    public Info(String name, String value, int width) {
        FormatCheck.specialString(name, NULL + ZERO_LENGTH + NEW_LINE);
        FormatCheck.intLimit(width, MIN_WIDTH, Integer.MAX_VALUE);
        this.name = name;
        this.value = value;
        this.isInteger = true;
        this.width = width;
    }

    public String getName() {
        return name;
    }

    public boolean isInteger() {
        return isInteger;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 一个一般的数值（科学计数法）
     * 最短宽度如下：
     * <pre>
     *      0.0e+0</pre>
     * 可见，一般的最短宽度是6，但是科学计数法的指数会浮动，故减去3，设为3，
     * 3代表有效数字最小是2位，再加上一位的小数点。
     * @param width 必须大于等于3
     */
    public void setWidth(int width) {
        FormatCheck.intLimit(width, MIN_WIDTH, Integer.MAX_VALUE);
        this.width = width;
    }

    /**
     * 按设定的宽度来。
     * 数值显示宽度不计科学计数法
     * 非数值显示直接忽略宽度
     */
    public String getValueString() {
        if (!isInteger) return value;
        if (value.isEmpty()) return "";
        FormatCheck.integerString(value, 10);
        if (value.length() <= width) return value;
        // 运行到这说明是数值，而且必须转换
        StringBuilder sb = new StringBuilder();
        int currentDI = 0; // DI: DigitIndex
        sb.append(value.charAt(currentDI++));
        sb.append(POINT);
        for (int i = width - (POINT.length() + 1); i > 0; i--) {
            //                ↑ 1是小数点前一位，POINT.length()是小数点本身
            sb.append(value.charAt(currentDI++));
        }
        sb.append(SCIENTIFIC_NOTATION_PARAM);
        sb.append(value.length() - 1);
        return String.valueOf(sb);
    }
}

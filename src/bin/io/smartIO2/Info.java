package bin.io.smartIO2;

import bin.util.FormatCheck;

/**
 * 如果使用本类显示数值，那么最好应该传入整数。
 * 注意，此类仅用于处理显示，不参与计算
 */
public class Info {
    private static final String SCIENTIFIC_NOTATION_PARAM =
            String.valueOf(new char[]{101, 43});
    private final String name;
    private String value;
    private final boolean isInteger;
    private int width;

    public Info(String name, String value, boolean isInteger) {
        this.name = name;
        this.value = value;
        this.isInteger = isInteger;
    }

    public Info(String name, String value, int width) {
        FormatCheck.intLimit(width, 2, Integer.MAX_VALUE);
        this.name = name;
        this.value = value;
        this.isInteger = true;
        this.width = width;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
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
     * 可见，最短宽度是6，但是科学计数法的指数会浮动，故减去3，设为3，
     * 3代表有效数字最小是2位，再加上一位的小数点。
     * @param width 必须大于等于3
     */
    public void setWidth(int width) {
        FormatCheck.intLimit(width, 4, Integer.MAX_VALUE);
        this.width = width;
    }

    /**
     * 按设定的宽度来，数值显示宽度不计科学计数法
     */
    public String getValueString() {
        if (!isInteger) return value;
        StringBuilder sb = new StringBuilder();

        return String.valueOf(sb);
    }
}

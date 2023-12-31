package com.plzEnterCompanyName.HDQS.util;

import java.util.Objects;

public class FormatCheck {

    /**
     * 闭区间
     */
    public static void intLimit(int i, int min, int max) {
        if (min > max)
            throw new IllegalArgumentException("min is greater than max: " +
                    min + " > " + max);
        String msg = "check";
        if (i < min)
            msg = "Argument is smaller than min: " + i + " < " + min;
        if (i > max)
            msg = "Argument is greater than max: " + i + " > " + max;
        if (!msg.equals("check"))
            throw new IllegalArgumentException(msg);
    }

    private static final char[] radixParams =
            "01234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    /**
     * 检查字符串是否可以被转换为BigInteger
     * 十进制
     */
    public static void integerString(String val) {
        integerString(val, 10);
    }

    /**
     * 检查字符串是否可以被转换为BigInteger
     * @param radix 进制
     */
    public static void integerString(String val, int radix) {
        intLimit(radix, 2, 36);
        char[] check = new char[radix];
        System.arraycopy(radixParams, 0, check, 0, radix);
        char[] toBeChecked = val.toCharArray();
        syntax : for (char tbc : toBeChecked) {
            for (char c : check) {
                if (tbc == c)
                    continue syntax;
            }
            throw new IllegalArgumentException();
        }
    }

    public static final int NULL = 0x00000001;
    public static final int ZERO_LENGTH = 0x00000002;
    public static final int NEW_LINE = 0x00000004;
    public static void specialString(String val, int options) {
        if ((options   )%2 == 1) {
            Objects.requireNonNull(val);
        }
        if ((options>>1)%2 == 1) {
            if (val.isEmpty())
                throw new IllegalArgumentException("Zero length String");
        }
        if ((options>>2)%2 == 1) {
            if (val.contains("\n") && val.contains("\r")) {
                throw new IllegalArgumentException("Newline is unsupported");
            }
        }
    }
}

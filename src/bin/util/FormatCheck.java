package bin.util;

import java.math.BigInteger;

public class FormatCheck {

    /**
     * 闭区间
     */
    public static void intLimit(int i, int min, int max) {
        if (min > max)
            throw new IllegalArgumentException("min is greater than max: " +
                    min + " > " + max);
        String msg = "check";
        if (i <= min)
            msg = "Argument is smaller than min: " + i + " < " + min;
        if (i >= max)
            msg = "Argument is greater than max: " + i + " > " + max;
        if (!msg.equals("check"))
            throw new IllegalArgumentException(msg);
    }

    /**
     * 检查字符串是否可以被转换为BigInteger
     * 十进制
     */
    public static String integerString(String val, int radix) {
        BigInteger bi = new BigInteger(val, radix);
        return String.valueOf(bi);
    }
}

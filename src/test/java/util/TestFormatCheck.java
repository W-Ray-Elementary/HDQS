package util;

import com.plzEnterCompanyName.HDQS.util.FormatCheck;

public class TestFormatCheck {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        TEST_FormatCheck();
        long cost = System.currentTimeMillis() - startTime;
        System.out.println("com.plzEnterCompanyName.HDQS.util.FormatCheck 的测试成功完成于"
                + ' ' + cost + ' ' + "ms");
    }

    private static void TEST_FormatCheck() {
        String legal = "31415926535897";
        String illegal = "77ac91a'";
        FormatCheck.integerString(legal);
        boolean passInt = false;
        try {
            FormatCheck.integerString(illegal, 16);
        } catch (IllegalArgumentException e) {
            passInt = true;
        }
        if (!passInt) {
            throw new RuntimeException();
        }
        String zeroLength = "";
        String newLine = "\n \r \r\n";
        boolean pass1 = false;
        boolean pass2 = false;
        boolean pass3 = false;
        boolean pass4 = false;
        try {
            FormatCheck.specialString(null, FormatCheck.NULL);
        } catch (NullPointerException e) {
            pass1 = true;
        }
        if (!pass1) {
            throw new RuntimeException();
        }
        try {
            FormatCheck.specialString(zeroLength, FormatCheck.ZERO_LENGTH);
        } catch (IllegalArgumentException e) {
            pass2 = true;
        }
        if (!pass2) {
            throw new RuntimeException();
        }
        try {
            FormatCheck.specialString(newLine, FormatCheck.NEW_LINE);
        } catch (IllegalArgumentException e) {
            pass3 = true;
        }
        if (!pass3) {
            throw new RuntimeException();
        }
        try {
            FormatCheck.specialString(newLine, FormatCheck.NULL +
                                               FormatCheck.NEW_LINE +
                                               FormatCheck.ZERO_LENGTH);
        } catch (IllegalArgumentException e) {
            pass4 = true;
        }
        if (!pass4) {
            throw new RuntimeException();
        }
    }
}

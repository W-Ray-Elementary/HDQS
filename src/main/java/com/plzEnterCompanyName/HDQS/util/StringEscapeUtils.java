package com.plzEnterCompanyName.HDQS.util;

public class StringEscapeUtils {
    public static String escapeLexicon(final String input) {
        return input.replace("\n", "\\n");
    }

    public static String unescapeLexicon(final String input) {
        return input.replace("\\n", "\n");
    }
}

package util;

import java.util.Objects;

public class Utils {
    public static void EXPECT_TRUE(boolean flag, String msg) {
        if (!flag)
            throw new RuntimeException(msg);
    }
    public static void EXPECT_EQUALS(Object o1, Object o2, String msg) {
        if (!Objects.equals(o1, o2))
            throw new RuntimeException(msg);
    }
}

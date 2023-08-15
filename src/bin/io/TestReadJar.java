package bin.io;

import pkg.Temp;

public class TestReadJar {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        TEST_isJarWorking();
        TEST_ReadJar();
        long cost = System.currentTimeMillis() - startTime;
        System.out.println("bin.io.TestReadJar 的测试成功完成于" + ' ' + cost + ' ' + "ms");
    }

    private static void TEST_isJarWorking() {
        Temp t = new Temp();
        if (!"77a7c91a".equals(t.toString()))
            throw new RuntimeException("testRes/io下的GetCode[77a7c91a].jar不工作");
    }

    private static void TEST_ReadJar() {

    }
}

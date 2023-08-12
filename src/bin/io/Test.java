package bin.io;

import java.io.File;

public class Test {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        TEST_PATH();
        TEST_FileToString();
        TEST_ENCODE();
        long cost = System.currentTimeMillis() - startTime;
        System.out.println("bin.io.FileAndString 的测试成功完成于" + ' ' + cost + ' ' + "ms");
    }

    // 此方法用于测试 bin.io.PATH
    private static void TEST_PATH() {
        File testFile = PATH.getFile("config.properties");
        String testTxt = testFile.getAbsolutePath();
        if (!testTxt.endsWith("config.properties")) {
            throw new RuntimeException("类 bin.io.PATH 未达成预定目标，测试未完成");
        }
    }

    // 此方法用于测试 bin.io.FileToString
    private static void TEST_FileToString() {
        // 测试读取
        String testTxt = FileAndString.read(PATH.getFile("testRes\\io\\FileAndString.txt"));
        if (!testTxt.contains("你好")) {
            System.out.println(testTxt);
            Exception e = new RuntimeException("类 bin.io.FileToString 未成功读取，测试未完成");
            e.printStackTrace();
        }
        // 测试写入
        testTxt = """
                世界你好！
                World hello!""";
        String testFilePath = "testRes\\io\\FileAndString";
        File testFile = PATH.getFile(testFilePath);
        FileAndString.write(testFile, testTxt);
        String testTxtRead = FileAndString.read(PATH.getFile(testFilePath));
        if (!testTxtRead.contains("界你")) {
            System.out.println(testTxtRead);
            Exception e = new RuntimeException("类 bin.io.FileToString 未成功读写，测试未完成");
            e.printStackTrace();
        }
        testFile.delete();
    }

    // 此方法创建了一个文件，便于外部查看文件编码
    private static void TEST_ENCODE() {
        FileAndString.write(
                PATH.getFile("testRes\\io\\编码测试"),
                "编码测试。\nEncode test.");
    }
}

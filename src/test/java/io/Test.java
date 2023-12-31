package io;

import com.plzEnterCompanyName.HDQS.io.FileAndString;
import com.plzEnterCompanyName.HDQS.io.FileSearch;
import com.plzEnterCompanyName.HDQS.io.PATH;

import java.io.File;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        TEST_PATH();
        TEST_FileToString();
        TEST_ENCODE();
        TEST_FileSearch();
        long cost = System.currentTimeMillis() - startTime;
        System.out.println("com.plzEnterCompanyName.HDQS.io.FileAndString 的测试成功完成于" + ' ' + cost + ' ' + "ms");
    }

    // 此方法用于测试 com.plzEnterCompanyName.HDQS.io.PATH
    private static void TEST_PATH() {
        File testFile = PATH.getFile("config.properties");
        String testTxt = testFile.getAbsolutePath();
        if (!testTxt.endsWith("config.properties")) {
            throw new RuntimeException("类 com.plzEnterCompanyName.HDQS.io.PATH 未达成预定目标，测试未完成");
        }
    }

    // 此方法用于测试 com.plzEnterCompanyName.HDQS.io.FileToString
    private static void TEST_FileToString() {
        // 测试读取
        String testTxt = FileAndString.read(PATH.getFile("testRes\\io\\FileAndString.txt"));
        if (!testTxt.contains("你好")) {
            System.out.println(testTxt);
            Exception e = new RuntimeException("类 com.plzEnterCompanyName.HDQS.io.FileToString 未成功读取，测试未完成");
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
            Exception e = new RuntimeException("类 com.plzEnterCompanyName.HDQS.io.FileToString 未成功读写，测试未完成");
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

    private static void TEST_FileSearch() {
        List<File> cfgFiles = FileSearch.suffix(PATH.getFile("testRes"), ".cfg");
        if (!(cfgFiles.size() > 2) || !(cfgFiles.get(0).getAbsolutePath().endsWith(".cfg"))) {
            throw new RuntimeException("FileSearch 未能找到文件！");
        }
    }
}

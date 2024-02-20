package util.lexicon;

import com.plzEnterCompanyName.HDQS.util.Configuration;
import com.plzEnterCompanyName.HDQS.util.lexicon.Lexicon;
import com.plzEnterCompanyName.HDQS.util.lexicon.LexiconConfiguration;

import util.Utils;

public class TestLexiconConfiguration {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        TEST_LexiconConfigure();
        long cost = System.currentTimeMillis() - startTime;
        System.out.println("com.plzEnterCompanyName.HDQS.util.lexicon.TestLexiconConfiguration 的测试成功完成于" + ' ' + cost + ' ' + "ms");
    }

    private static void TEST_LexiconConfigure() {
        Lexicon testLexicon = Lexicon.valueOf("""
            Test
            {
                a = 100
                b = 200
                Nest
                {
                    name = m
                }
                c = 300
            }""").get(0);
        Configuration testConfigure = new LexiconConfiguration(testLexicon);
        Utils.EXPECT_EQUALS(100, testConfigure.getInt("a"), "LexiconConfiguration无法取回数据");
        Utils.EXPECT_EQUALS(200, testConfigure.getInt("b"), "LexiconConfiguration无法取回数据");
        Utils.EXPECT_EQUALS(300, testConfigure.getInt("c"), "LexiconConfiguration无法取回数据");
        Configuration subConfigure = testConfigure.subSets("Nest").get(0);
        Utils.EXPECT_EQUALS("m", subConfigure.get("name"), "LexiconConfiguration无法取回nest数据");
    }
}

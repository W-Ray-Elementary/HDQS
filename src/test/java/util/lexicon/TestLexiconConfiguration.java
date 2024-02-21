package util.lexicon;

import java.util.List;

import com.plzEnterCompanyName.HDQS.util.Configuration;
import com.plzEnterCompanyName.HDQS.util.lexicon.Lexicon;
import com.plzEnterCompanyName.HDQS.util.lexicon.LexiconConfiguration;

import util.Utils;

public class TestLexiconConfiguration {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        TEST_LexiconConfigure();
        long cost = System.currentTimeMillis() - startTime;
        System.out.println("com.plzEnterCompanyName.HDQS.util.lexicon.TestLexiconConfiguration 的测试成功完成于" + ' ' + cost
                + ' ' + "ms");
    }

    private static void TEST_LexiconConfigure() {
        // getInt()
        Lexicon testGetting = Lexicon.valueOf("""
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
        Configuration testConfigure = new LexiconConfiguration(testGetting);
        Utils.EXPECT_EQUALS(100, testConfigure.getInt("a"), "LexiconConfiguration无法取回数据");
        Utils.EXPECT_EQUALS(200, testConfigure.getInt("b"), "LexiconConfiguration无法取回数据");
        Utils.EXPECT_EQUALS(300, testConfigure.getInt("c"), "LexiconConfiguration无法取回数据");
        // Nesting
        Configuration subConfigure = testConfigure.subSets("Nest").get(0);
        Utils.EXPECT_EQUALS("m", subConfigure.get("name"), "LexiconConfiguration无法取回nest数据");
        // getList()
        Configuration testList = new LexiconConfiguration(Lexicon.valueOf("""
                Test
                {
                    key = 0
                    key = 1
                    key = 2
                    key = 3
                    key = 4
                    key = 5
                    key = 6
                    key = 7
                }""").get(0));
        List<String> aList = testList.getList("key");
        for (int i = 0; i < aList.size(); i++) {
            Utils.EXPECT_EQUALS(String.valueOf(i), aList.get(i), "LexiconConfiguration无法取回List数据");
        }
        // subSets()
        Configuration testSubs = new LexiconConfiguration(Lexicon.valueOf("""
                Test
                {
                    a = 100
                    b = 200
                    Nest
                    {
                        name = m
                    }
                    c = 300
                    Sub
                    {
                        name = nul
                        pos = w
                    }
                    Sub
                    {
                        name = nul
                        pos = s
                    }
                    Sub
                    {
                        name = nul
                        pos = a
                    }
                    Sub
                    {
                        name = nul
                        pos = d
                    }
                }""").get(0));
        List<Configuration> subs = testSubs.subSets("Sub");
        Utils.EXPECT_EQUALS("w", subs.get(0).get("pos"), "LexiconConfiguration无法子集");
        Utils.EXPECT_EQUALS("s", subs.get(1).get("pos"), "LexiconConfiguration无法子集");
        Utils.EXPECT_EQUALS("a", subs.get(2).get("pos"), "LexiconConfiguration无法子集");
        Utils.EXPECT_EQUALS("d", subs.get(3).get("pos"), "LexiconConfiguration无法子集");
    }
}

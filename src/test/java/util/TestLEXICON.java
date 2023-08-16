package util;

import com.plzEnterCompanyName.HDQS.io.PATH;
import com.plzEnterCompanyName.HDQS.util.IllegalContentException;
import com.plzEnterCompanyName.HDQS.util.LEXICON;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class TestLEXICON {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        TEST_LEXICON();
        long cost = System.currentTimeMillis() - startTime;
        System.out.println("com.plzEnterCompanyName.HDQS.util.LEXICON 的测试成功完成于" + ' ' + cost + ' ' + "ms");
    }

    public static void BenchMark() {
        LEXICON benchmark = new LEXICON("BENCHMARK");
        Random r = new Random(0L);
        for (int i = 0; i < 360000; i++) {
            benchmark.add(String.valueOf(r.nextLong()), String.valueOf(r.nextLong()));
        }
        long startTime = System.currentTimeMillis();
        benchmark = LEXICON.getInstance(benchmark.toString()).get(0);
        long cost = System.currentTimeMillis() - startTime;
        System.out.println("com.plzEnterCompanyName.HDQS.util 的基准测试成功完成于" + ' ' + cost + ' ' + "ms");
    }

    private static void TEST_LEXICON() {

        // 测试 空白LEXICON 的 lineCount()
        LEXICON blankLexicon = new LEXICON("BLANK");
        if (blankLexicon.lineCount() != 3)
            throw new RuntimeException("类 com.plzEnterCompanyName.HDQS.util.LEXICON 在空白返回了错误的文本行数量，" +
                    "正确值：" + 3 + "返回值：" + blankLexicon.lineCount() + "\n测试未完成");

        // 测试 纯文本LEXICON 的 lineCount()
        LEXICON textLexicon = new LEXICON("COMPANY");
        textLexicon.add("员工", "张三");
        textLexicon.add("员工", "李四");
        textLexicon.add("员工", "王五");
        textLexicon.add("设备", "1 普通设备套装");
        if (textLexicon.lineCount() != 7)
            throw new RuntimeException("类 com.plzEnterCompanyName.HDQS.util.LEXICON 在纯文本返回了错误的文本行数量，" +
                    "正确值：" + 7 + "返回值：" + textLexicon.lineCount() + "\n测试未完成");

        // 测试 嵌套LEXICON 的 lineCount()
        LEXICON nestLexicon = new LEXICON("ROOT");
        nestLexicon.add("市场", "");
        nestLexicon.add("市场容量", "");
        nestLexicon.add("生产力指标", "");
        nestLexicon.add(textLexicon);
        if (nestLexicon.lineCount() != 13)
            throw new RuntimeException("类 com.plzEnterCompanyName.HDQS.util.LEXICON 在嵌套返回了错误的文本行数量，" +
                    "正确值：" + 13 + "返回值：" + textLexicon.lineCount() + "\n测试未完成");

        // 测试列举LEXICON内部的所有LEXICON
        /* 懒得测了 */

        // 测试直接自指LEXICON
        LEXICON infiniteLexicon = new LEXICON("INFINITE");
        {
            boolean pass = false;
            try {
                infiniteLexicon.add(infiniteLexicon);
            } catch (IllegalContentException ice) {
                pass = true;
            }
            if (!pass) {
                throw new RuntimeException("类 com.plzEnterCompanyName.HDQS.util.LEXICON 在自指时不抛异常");
            }
        }

        // 测试 空白LEXICON 的 toString()
        String blank = blankLexicon.toString();
        if (!(
                blank.contains("BLANK") &&
                blank.contains("{")&&
                blank.contains("}")
        )) {
            throw new RuntimeException("空白LEXICON 的 toString() 方法未包含所有要点");
        }
        String[] blanks = blank.split("\n");
        if (blanks.length < 3) {
            throw new RuntimeException("空白LEXICON 的 toString() 方法行数不足");
        }

        // 测试 纯文本LEXICON 的 toString()
        String text = textLexicon.toString();
        if (!(
                text.contains("COMPANY") &&
                text.contains("{")&&
                text.contains("=") &&
                text.contains("员工")&&
                text.contains("张三")&&
                text.contains("李四")&&
                text.contains("王五")&&
                text.contains("设备")&&
                text.contains("1 普通设备套装")&&
                text.contains("}")
        )) {
            throw new RuntimeException("纯文本LEXICON 的 toString() 方法未包含所有要点");
        }
        String[] texts = text.split("\n");
        if (texts.length < 7) {
            throw new RuntimeException("纯文本LEXICON 的 toString() 方法行数不足");
        }

        // 测试 嵌套LEXICON 的 toString()
        String nest = nestLexicon.toString();
        if (!(
                nest.contains("ROOT")&&
                nest.contains("{")&&
                nest.contains("=") &&
                nest.contains("市场"   )&&
                nest.contains("市场容量" )&&
                nest.contains("生产力指标")&&
                nest.contains("COMPANY") &&
                nest.contains("员工")&&
                nest.contains("张三")&&
                nest.contains("李四")&&
                nest.contains("王五")&&
                nest.contains("设备")&&
                nest.contains("1 普通设备套装")&&
                nest.contains("}")
                )) {
            throw new RuntimeException("嵌套LEXICON 的 toString() 方法未包含所有要点");
        }

        // 测试 空白LEXICON 的 LEXICON(String name, String str)
        // 懒了，不测了，不抛异常就行
        LEXICON bL = LEXICON.getInstance("bL{}").get(0);
        bL = LEXICON.getInstance("bL {}").get(0);
        bL = LEXICON.getInstance("""
                bL{
                }""").get(0);
        bL = LEXICON.getInstance("""
                bL
                {
                }""").get(0);

        // 测试 纯文本LEXICON 的 LEXICON(String name, String str)
        // 懒了，不测了，不抛异常就行
        LEXICON tL = LEXICON.getInstance("""
                tL{
                    des = a
                }""").get(0);
        tL = LEXICON.getInstance("""
                tL {
                    mm = ds
                }""").get(0);
        tL = LEXICON.getInstance("""
                tL
                {
                    hhh = hhh
                }""").get(0);

        // 测试 嵌套LEXICON 的 LEXICON(String name, String str)
        // 懒了，不测了，不抛异常就行
        LEXICON nL = LEXICON.getInstance("""
                nL
                {
                    a = a
                    b = b
                    sL
                    {
                        c = c
                    }
                    d = d
                    sL
                    {
                        e = e
                        f = f
                    }
                    g = g
                }""").get(0);

        // 测试 testRes\\util\\mk1Pod_v2.cfg文件 转 LEXICON
        String testFilePath = "testRes\\util\\mk1Pod_v2.cfg";
        LEXICON mk1pod_v2 = LEXICON.getInstance(PATH.getFile(testFilePath)).get(0);
        if (mk1pod_v2.lineCount() < 228)
            throw new RuntimeException(testFilePath + "文件 转 LEXICON 时结果不正确！");

        // 测试 放进LEXICON的数据能否原封不动地拿回？
        String members = Arrays.toString(textLexicon.getAll("员工"));
        if (!(
                members.contains("张三") &&
                members.contains("李四") &&
                members.contains("王五")
        )) {
            throw new RuntimeException("放进LEXICON的数据不能原封不动地拿回");
        }

        // 测试 String getFirst(String key) 方法
        if (!("1 普通设备套装".equals(textLexicon.getFirst("设备"))))
            throw new RuntimeException(" String getFirst(String key) 方法返回值不正确！");

        // 测试模糊查询
        LEXICON fuzzyLexicon = LEXICON.getInstance(PATH.getFile("testRes\\util\\fuzzyLexicon.cfg")).get(0);
        String a = fuzzyLexicon.getFirst("a", true);
        String b = fuzzyLexicon.getFirst("b", true);
        String c = fuzzyLexicon.getFirst("c", true);
        if (!(
                Objects.equals(a, "100") &&
                Objects.equals(b, "3.14") &&
                Objects.equals(c, "A String")
                )) {
            throw new RuntimeException("LEXICON无法进行模糊查询");
        }

        // 测试 boolean remove(String key)
        LEXICON removal = LEXICON.getInstance(textLexicon.toString()).get(0);
        removal.remove("员工");
        if (removal.lineCount() > 4) {
            throw new RuntimeException("boolean remove(String key) 方法不工作");
        }
    }
}

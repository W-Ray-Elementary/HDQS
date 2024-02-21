package util;

import java.util.List;

import com.plzEnterCompanyName.HDQS.util.Configuration;
import com.plzEnterCompanyName.HDQS.util.Configurations;
import com.plzEnterCompanyName.HDQS.util.lexicon.Lexicon;
import com.plzEnterCompanyName.HDQS.util.lexicon.LexiconConfiguration;

public class TestConfigrations {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        TEST_Configrations();
        long cost = System.currentTimeMillis() - startTime;
        System.out.println("com.plzEnterCompanyName.HDQS.util.lexicon.Configurations 的测试成功完成于" + ' ' + cost +
                ' ' + "ms");
    }

    private static void TEST_Configrations() {
        List<Configuration> subSets = new LexiconConfiguration(Lexicon.valueOf("""
                Test
                {
                    Animal
                    {
                        name = cat
                        act = meow
                    }
                    Animal
                    {
                        name = dog
                        act = bark
                    }
                    Animal
                    {
                        name = bird
                        act = sing
                    }
                }""").get(0)).subSets("Animal");
                Configuration cat = Configurations.orderName(subSets, "Animal", "cat");
                Utils.EXPECT_EQUALS("meow", cat.get("act"), "猫跑了");
                Configuration dog = Configurations.orderName(subSets, "Animal", "dog");
                Utils.EXPECT_EQUALS("bark", dog.get("act"), "狗跑了");
                Configuration bird = Configurations.orderName(subSets, "Animal", "bird");
                Utils.EXPECT_EQUALS("sing", bird.get("act"), "鸟飞了");
        try {
            Configuration exception = Configurations.strictOrderName(subSets, "Animal", "dragon");
            Utils.EXPECT_EQUALS(null, exception, "取到了不该取的东西");
            throw new RuntimeException("没有抛异常");
        } catch (Exception e) {
            // 正常！
        }
    }

}

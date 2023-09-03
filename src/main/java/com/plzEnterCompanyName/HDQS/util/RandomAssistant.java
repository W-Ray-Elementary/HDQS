package com.plzEnterCompanyName.HDQS.util;

import java.util.Random;

/**
 * 帮你掷骰子！
 */
public class RandomAssistant {
    private final Random random;
    public RandomAssistant(long seed) {
        random = new Random(seed);
    }
    public RandomAssistant() {
        random = new Random();
    }

    /**
     * 抛枚硬币，概率对半
     */
    public boolean coins() {
        return random.nextBoolean();
    }

    /**
     * 百分之几的概率发生，不检查上下限。
     * 小于零视为0，大于100视为100
     * @param percent 百分数
     */
    public boolean percent(int percent) {
        return random.nextInt(100) < percent;
    }
}

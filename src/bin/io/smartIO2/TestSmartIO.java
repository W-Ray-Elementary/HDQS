package bin.io.smartIO2;

public class TestSmartIO {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        TEST_Info();
        TEST_Operation();
        Test_MessageManager();
        TEST_SmartIO();
        long cost = System.currentTimeMillis() - startTime;
        System.out.println("bin.io.smartIO2 的测试成功完成于" + ' ' + cost + ' ' + "ms");
    }

    private static void Test_MessageManager() {

    }

    private static void TEST_SmartIO() {

    }

    private static void TEST_Operation() {

    }

    private static void TEST_Info() {
        // 测试简单情况（多了）
        Info simpleInfo = new Info("simpleInfo", 57989, 6);
        if (!"57989".equals(simpleInfo.getValueString()))
            throw new RuntimeException("bin.io.smartIO2.Info无法处理 简单情况（多了）");
        // 测试简单情况（合适）
        simpleInfo.setWidth(5);
        if (!"57989".equals(simpleInfo.getValueString()))
            throw new RuntimeException("bin.io.smartIO2.Info无法处理 简单情况（合适）");
        // 测试简单情况（少了）
        simpleInfo.setWidth(4);
        if (!"5.79e+4".equals(simpleInfo.getValueString()))
            throw new RuntimeException("bin.io.smartIO2.Info无法处理 简单情况（少了）");
        // 测试简单情况（更少）
        simpleInfo.setWidth(3);
        if (!"5.7e+4".equals(simpleInfo.getValueString()))
            throw new RuntimeException("bin.io.smartIO2.Info无法处理 简单情况（更少）");
    }

    static class TestAutoPlay implements Out {

        @Override
        public void out(Message msg) {

        }
    }
}

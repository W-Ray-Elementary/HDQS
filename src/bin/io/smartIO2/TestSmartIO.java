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

    }

    static class TestAutoPlay implements Out {

        @Override
        public void out(Message msg) {

        }
    }
}

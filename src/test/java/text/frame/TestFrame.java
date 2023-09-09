package text.frame;

import com.plzEnterCompanyName.HDQS.text.frame.Frame;

public class TestFrame {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        TEST_Frame();
        long cost = System.currentTimeMillis() - startTime;
        System.out.println("com.plzEnterCompanyName.HDQS.text.frame 的测试成功完成于" + ' ' + cost + ' ' + "ms");
    }

    private static void TEST_Frame() {
        Frame f = new Frame();
        f.out(null);
    }
}

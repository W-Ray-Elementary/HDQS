package text.frame.animation;

import com.plzEnterCompanyName.HDQS.io.smartIO2.Message;
import com.plzEnterCompanyName.HDQS.text.frame.animate.MessageIterator;
import text.frame.TestFrame;

public class TestMessageIterator {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        TEST_MessageIterator();
        long cost = System.currentTimeMillis() - startTime;
        System.out.println("com.plzEnterCompanyName.HDQS.text.frame.animation 的测试成功完成于" + ' ' + cost + ' ' + "ms");
    }

    private static void TEST_MessageIterator() {
        Message msg = TestFrame.messageForTest;
        MessageIterator mi = new MessageIterator(msg);
        while (mi.hasNext()) {
            mi.next();
        }
    }
}

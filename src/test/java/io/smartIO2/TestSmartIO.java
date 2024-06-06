package io.smartIO2;

import com.plzEnterCompanyName.HDQS.io.smartIO2.*;
import util.Utils;

import java.util.ArrayList;
import java.util.List;

public class TestSmartIO {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        TEST_SmartIO();
        long cost = System.currentTimeMillis() - startTime;
        System.out.println("com.plzEnterCompanyName.HDQS.io.smartIO2 的测试成功完成于" + ' ' + cost + ' ' + "ms");
    }

    private static void TEST_SmartIO() {
        Robot robot = new Robot();
        Robot robot2 = new Robot();
        Message msg = new MessageManager().toMessage();

        List<Out> outList = new ArrayList<>();
        outList.add(robot);
        SmartIO sio = new SmartIO(robot, outList);
        sio.setIn(robot2);
        sio.clearOut();
        sio.addOutInstance(robot2);
        robot2.NEXT = "Hello world!";
        Utils.EXPECT_EQUALS(sio.next(msg), "Hello world!", "不一样！");
        Utils.EXPECT_TRUE(!robot2.isWarned, "初始状态不对");
        robot2.NEXT = "aaa";
        int i = sio.nextInt(msg);
        Utils.EXPECT_TRUE(robot2.isWarned, "没报警");
        Utils.EXPECT_EQUALS(String.valueOf(i), "1", "不一样");
        i = sio.nextInt(msg, new IntChecker());
        Utils.EXPECT_EQUALS(String.valueOf(i), "1", "不一样");
    }
}

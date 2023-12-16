package com.plzEnterCompanyName.HDQS;

import com.plzEnterCompanyName.HDQS.io.smartIO2.Message;
import com.plzEnterCompanyName.HDQS.io.smartIO2.MessageManager;
import com.plzEnterCompanyName.HDQS.text.frame.Frame;

public class Launcher {
    private static void configureLaunch() {

    }

    public static void main(String[] args) {
        configureLaunch();
        for (int i = 0; i < args.length; i++) {
            if ("-helloWorld".equals(args[i])) {
                helloWorld(args[i + 1]);
            }
            if ("-frame".equals(args[i])) {
                frameDemo();
            }
        }
        cleanUp();
    }

    private static void helloWorld(String arg) {
        switch (arg) {
            case "true", "True", "TRUE" -> {
                System.out.println("你好世界！");
                System.out.println("Hello world!");
            }
            case "false", "False", "FALSE" -> {}
            default -> throw new RuntimeException("Invalid parameter: " + arg);
        }
    }

    private static void frameDemo() {
        Frame f = new Frame();
        MessageManager mm = new MessageManager();
        Message messageForTest;
        mm.title("MYRPG-VersionAlpha0.1" + SETTINGS.GAME_NAME);

        mm.info("等级    1");
        mm.info("经验    100/0");
        mm.info("属性点  0");
        mm.info("生命值  100/100");
        mm.info("魔力值  200/200");
        mm.info("攻击力  10");
        mm.info("防御力  2");
        mm.info("敏捷    5");
        mm.info("距离    1000/0");
        mm.info("武器    训练战士用的木剑      伤害:3");
        mm.info("盔甲    新手木甲      盔甲值:3.0");
        mm.info("输入6以查看详细信息....");

        mm.text("你遭遇了怪物,进入战斗状态!!");
        mm.text("==========================");
        mm.text("现在是战斗的第1回合!");
        mm.text("-------------------------------");
        mm.text("你的血量:100.0/100.0        僵尸血量:50.0/50.0      等级:1");
        mm.text("你的魔力值:200.0/200.0");
        mm.text("-------------------------------");
        mm.text("请选择你现在要做什么");

        mm.operation("1.【战斗】");
        mm.operation("2.【怪物信息】");
        mm.operation("3.【释放技能】");
        mm.operation("4.【更多功能】");
        mm.operation("5.【More function】");
        mm.operation("6.【その他の機能】");
        messageForTest = mm.toMessage();

        f.out(messageForTest);
        new java.util.Scanner(System.in).nextLine();
    }

    private static void cleanUp() {

    }
}

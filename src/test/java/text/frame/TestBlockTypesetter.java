package text.frame;

import com.plzEnterCompanyName.HDQS.io.smartIO2.Message;
import com.plzEnterCompanyName.HDQS.io.smartIO2.MessageManager;

public class TestBlockTypesetter {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        TEST_BlockTypesetter();
        long cost = System.currentTimeMillis() - startTime;
        System.out.println("com.plzEnterCompanyName.HDQS.text.frame.BlockTypesetter 的测试成功完成于" + ' ' + cost + ' ' + "ms");
    }
    public static final Message messageForTest;
    static {
        MessageManager mm = new MessageManager();
        mm.title("MYRPG-VersionAlpha0.1");

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

        mm.operation("战斗");
        mm.operation("怪物信息");
        mm.operation("释放技能");
        messageForTest = mm.toMessage();
    }
    private static void TEST_BlockTypesetter() {

    }
}

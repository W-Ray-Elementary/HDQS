package com.plzEnterCompanyName.HDQS.text.frame;

import com.plzEnterCompanyName.HDQS.io.ConfigureFile;
import com.plzEnterCompanyName.HDQS.io.PATH;
import com.plzEnterCompanyName.HDQS.io.smartIO2.*;
import com.plzEnterCompanyName.HDQS.text.AwtRuler;
import com.plzEnterCompanyName.HDQS.text.Ruler;
import com.plzEnterCompanyName.HDQS.text.Typography;
import com.plzEnterCompanyName.HDQS.text.zh_CN_Typography;

public class Frame implements Out, PageOutputAble, WarnAble {

    public static void demo() {
        Frame f = new Frame();
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
        mm.text();
        mm.text("请选择你现在要做什么");

        mm.operation("1.【战斗】");
        mm.operation("2.【怪物信息】");
        mm.operation("3.【释放技能】");
        mm.operation("4.【更多功能】");
        mm.operation("5.【More function】");
        mm.operation("6.【その他の機能】");
        f.out(mm.toMessage());
        new java.util.Scanner(System.in).nextLine();
        mm.text("    视频提供了功能强大的方法帮助您证明您的观点。当您单击联机视频时，可以在想要添加的视频的嵌入代码中进行粘贴。您也可以键入一个关键字以联机搜索最适合您的文档的视频。");
        mm.text("    为使您的文档具有专业外观，Word 提供了页眉、页脚、封面和文本框设计，这些设计可互为补充。例如，您可以添加匹配的封面、页眉和提要栏。单击“插入”，然后从不同库中选择所需元素。");
        mm.text("    主题和样式也有助于文档保持协调。当您单击设计并选择新的主题时，图片、图表或 SmartArt 图形将会更改以匹配新的主题。当应用样式时，您的标题会进行更改以匹配新的主题。");
        mm.text("    使用在需要位置出现的新按钮在 Word 中保存时间。若要更改图片适应文档的方式，请单击该图片，图片旁边将会显示布局选项按钮。当处理表格时，单击要添加行或列的位置，然后单击加号。");
        mm.text("    在新的阅读视图中阅读更加容易。可以折叠文档某些部分并关注所需文本。如果在达到结尾处之前需要停止读取，Word 会记住您的停止位置 - 即使在另一个设备上。");
        f.out(mm.toMessage());
        new java.util.Scanner(System.in).nextLine();
    }

    private final Layout layout;
    public static final Ruler AWT_RULER = new AwtRuler();
    public static final Typography TYPO = new zh_CN_Typography(AWT_RULER);
    public static final String DEFAULT_CONFIG__FILE_PATH = "settings\\frame.cfg";
    private static final String DEFAULT_CONFIG_VALUE =
            """
            Frame
            {
                width = 120
                height = 29
                BlockTypesetter
                {
                    name = SeparateLine
                    horizontalStyle = 45
                    verticalStyle = 124
                }
                BlockTypesetter
                {
                    name = Tittle
                    indentation = 1
                    indentationChar = 32 // decimal : 32 = binary 00010000 = the ascii code
                                        // of blank space
                    isDrawingGameName = true
                    gameName = [未定义 undefined]
                }
                BlockTypesetter
                {
                    name = Info
                    totalWidth = 41 // The max screen space that is available. Most of the
                                    // time, Info will not take up so much screen space.
                    totalHeight = 5 // If the position of Info that defined by Layout is UP
                                    // or DOWN, this setting value "totalHeight" will be
                                    // took into consideration. Otherwise Info will
                                    // consider "totalWidth"
                    indentation = 1
                    indentationChar = 32 // decimal : 32 = binary 00010000 = the ascii code
                                        // of blank space
                    singleInfoWidthMin = 29
                    singleInfoWidthMax = 39
                    horizontalSpacing = 4
                    blankRow = AUTO
                }
                BlockTypesetter
                {
                    name = Operation
                    indentation = 1
                    indentationChar = 32
                    horizontalSpacing = 3
                }
                BlockTypesetter
                {
                    name = Warning
                }
                BlockTypesetter
                {
                    name = Text
                    totalWidth = 41
                    totalHeight = 10
                    indentationLeft = 1
                    indentationRight = 1
                    indentationChar = 32
                    widowOrphanControl = 1
                }
                Layout
                {
                    Layer
                    {
                        type = SeparateLine
                        position = UP
                    }
                    Layer
                    {
                        type = SeparateLine
                        position = DOWN
                    }
                    Layer
                    {
                        type = SeparateLine
                        position = RIGHT
                    }
                    Layer
                    {
                        type = SeparateLine
                        position = LEFT
                    }
                    Layer
                    {
                        type = Tittle
                        position = UP
                    }
                    Layer
                    {
                        type = SeparateLine
                        position = UP
                    }
                    Layer
                    {
                        type = Info
                        position = RIGHT
                    }
                    Layer
                    {
                        type = SeparateLine
                        position = RIGHT
                    }
                    Layer
                    {
                        type = Operation
                        position = DOWN
                    }
                    Layer
                    {
                        type = SeparateLine
                        position = DOWN
                    }
                    Layer
                    {
                        type = Warning
                        position = DOWN
                    }
                    Layer
                    {
                        type = Text
                        position = UP
                    }
                }
            }""";
    public static final ConfigureFile DEFAULT_CONFIG;

    static {
        DEFAULT_CONFIG = new ConfigureFile(
                PATH.getFile(DEFAULT_CONFIG__FILE_PATH),
                DEFAULT_CONFIG_VALUE
        );
        DEFAULT_CONFIG.setComments("""
                //
                // this configure file is auto generated by Frame
                //
                """);
    }

    public Frame() {
        layout = new Layout(DEFAULT_CONFIG.read());
    }

    public Frame(Layout layout) {
        this.layout = layout;
    }

    @Override
    public void out(Message msg) {
        String typed = layout.setType(msg);
        System.out.print(typed);
    }

    @Override
    public void pageOutput() {

    }

    @Override
    public void warn(String WarnStr) {

    }
}

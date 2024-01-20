package com.plzEnterCompanyName.HDQS.text.frame;

import com.plzEnterCompanyName.HDQS.RequireBoot;
import com.plzEnterCompanyName.HDQS.io.ConfigureFile;
import com.plzEnterCompanyName.HDQS.io.PATH;
import com.plzEnterCompanyName.HDQS.io.smartIO2.*;
import com.plzEnterCompanyName.HDQS.text.AwtRuler;
import com.plzEnterCompanyName.HDQS.text.Ruler;
import com.plzEnterCompanyName.HDQS.text.Typography;
import com.plzEnterCompanyName.HDQS.text.zh_CN_Typography;
import com.plzEnterCompanyName.HDQS.util.Lexicon;
import com.plzEnterCompanyName.HDQS.util.Lexicons;

import java.io.PrintStream;
import java.util.List;
import java.util.Objects;

public class Frame implements Out, PageOutputAble, WarnAble, RequireBoot {

    @Override
    public void boot() {
        DEFAULT_LAYOUT = new Layout(DEFAULT_CONFIG.read());
        RequireBoot.out.println(getClass().getName());
    }

    private PrintStream printStream;

    public void setPrintStream(PrintStream printStream) {
        this.printStream = printStream;
    }

    public static void demo() {
        final ConfigureFile DEMO_RES = getDemoRes();
        DEMO_RES.setComments("""
                //
                // this file is auto generated by Frame
                //
                """);
        Lexicon read = DEMO_RES.read();
        List<Lexicon> messageLs = Lexicons.listOut(read, "Message");
        for (Lexicon messageLexicon : messageLs) {
        }
    }

    private static ConfigureFile getDemoRes() {
        final String DEMO_RES_FILEPATH = "gameData\\HDQS\\Frame\\resources\\demo.cfg";
        final String DEMO_RES_VALUE =
                """
                Messages
                {
                    Message
                    {
                        title = MYRPG-VersionAlpha0.1
                        
                        info = 等级    1
                        info = 经验    100/0
                        info = 属性点  0
                        info = 生命值  100/100
                        info = 魔力值  200/200
                        info = 攻击力  10
                        info = 防御力  2
                        info = 敏捷    5
                        info = 距离    1000/0
                        info = 武器    训练战士用的木剑      伤害:3
                        info = 盔甲    新手木甲      盔甲值:3.0
                        info = 输入6以查看详细信息....
                        
                        text = 你遭遇了怪物,进入战斗状态!!
                        text = ==========================
                        text = 现在是战斗的第1回合!
                        text = -------------------------------
                        text = 你的血量:100.0/100.0        僵尸血量:50.0/50.0      等级:1
                        text = 你的魔力值:200.0/200.0
                        text = -------------------------------
                        text =
                        text = 请选择你现在要做什么
                        
                        operation = 1.【战斗】
                        operation = 2.【怪物信息】
                        operation = 3.【释放技能】
                        operation = 4.【更多功能】
                        operation = 5.【More function】
                        operation = 6.【その他の機能】
                    }
                    Message
                    {
                        text =*    视频提供了功能强大的方法帮助您证明您的观点。当您单击联机视频时，可以在想要添加的视频的嵌入代码中进行粘贴。您也可以键入一个关键字以联机搜索最适合您的文档的视频。
                        text =*    为使您的文档具有专业外观，Word 提供了页眉、页脚、封面和文本框设计，这些设计可互为补充。例如，您可以添加匹配的封面、页眉和提要栏。单击“插入”，然后从不同库中选择所需元素。
                        text =*    主题和样式也有助于文档保持协调。当您单击设计并选择新的主题时，图片、图表或 SmartArt 图形将会更改以匹配新的主题。当应用样式时，您的标题会进行更改以匹配新的主题。
                        text =*    使用在需要位置出现的新按钮在 Word 中保存时间。若要更改图片适应文档的方式，请单击该图片，图片旁边将会显示布局选项按钮。当处理表格时，单击要添加行或列的位置，然后单击加号。
                        text =*    在新的阅读视图中阅读更加容易。可以折叠文档某些部分并关注所需文本。如果在达到结尾处之前需要停止读取，Word 会记住您的停止位置 - 即使在另一个设备上。
                        text =*Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Maecenas porttitor congue massa. Fusce posuere, magna sed pulvinar ultricies, purus lectus malesuada libero, sit amet commodo magna eros quis urna.
                    }
                }""";
        return new ConfigureFile(PATH.getFile(DEMO_RES_FILEPATH), DEMO_RES_VALUE);
    }
    private final Layout layout;
    private static Layout DEFAULT_LAYOUT;
    public static final Ruler RULER = new AwtRuler();
    public static final Typography TYPO = new zh_CN_Typography(RULER);
    public static final String DEFAULT_CONFIG_FILE_PATH = "settings\\frame.cfg";
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
        DEFAULT_CONFIG = new ConfigureFile(PATH.getFile(DEFAULT_CONFIG_FILE_PATH), DEFAULT_CONFIG_VALUE);
        DEFAULT_CONFIG.setComments("""
                //
                // this configure file is auto generated by Frame
                //
                """);
    }

    public Frame() {
        layout = Objects.requireNonNullElseGet(DEFAULT_LAYOUT, () -> new Layout(DEFAULT_CONFIG.read()));
    }

    public Frame(Layout layout) {
        this.layout = layout;
        printStream = System.out;
    }

    @Override
    public void out(Message msg) {
        String typed = layout.setType(msg);
        printStream.print(typed);
    }

    @Override
    public void pageOutput() {

    }

    @Override
    public void warn(String WarnStr) {

    }
}

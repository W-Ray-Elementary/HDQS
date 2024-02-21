package com.plzEnterCompanyName.HDQS.util.args;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import java.util.Iterator;

import com.plzEnterCompanyName.HDQS.Start;
import com.plzEnterCompanyName.HDQS.io.ConfigureFile;
import com.plzEnterCompanyName.HDQS.io.PATH;
import com.plzEnterCompanyName.HDQS.io.smartIO2.MessageManager;
import com.plzEnterCompanyName.HDQS.text.frame.Frame;
import com.plzEnterCompanyName.HDQS.util.lexicon.KVEntry;
import com.plzEnterCompanyName.HDQS.util.lexicon.Lexicon;
import com.plzEnterCompanyName.HDQS.util.lexicon.LexiconConfiguration;
import com.plzEnterCompanyName.HDQS.util.lexicon.Lexicons;

public class FrameDemo implements Argument {

    public static final String DEFAULT_CONFIG = """
            Layout
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
                    totalHeight = 7 // If the position of Info that defined by Layout is UP
                                    // or DOWN, this setting value "totalHeight" will be
                                    // took into consideration. Otherwise Info will
                                    // consider "totalWidth"
                    indentation = 1
                    indentationChar = 32 // decimal : 32 = binary 00010000 = the ascii code
                                            // of blank space
                    singleInfoWidthMin = 29
                    singleInfoWidthMax = 36 // This value may seem strange, but it has been
                                            // seriously, solemnly, gravely, severity, strictly,
                                            // earnestly, gravitas and carefully considered
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
            """;

    @Override
    public String getParam() {
        return "-frame";
    }

    @Override
    public Map<String, OptionType> getOptionsInfo() {
        return NO_OPTION;
    }

    @Override
    public Start process(String[] options) {
        return () -> {
            final ConfigureFile DEMO_RES = getDemoRes();
            DEMO_RES.setComments("""
                    //
                    // this file is auto generated by Frame demo
                    //
                    """);
            Lexicon read = DEMO_RES.read();
            List<Lexicon> messageLs = Lexicons.listOut(read, "Message");
            Scanner sc = new Scanner(System.in);
            for (Lexicon messageLexicon : messageLs) {
                MessageManager mm = read(messageLexicon);
                ConfigureFile cfgFile = new ConfigureFile(PATH.getFile("settings\\frame.cfg"), DEFAULT_CONFIG);
                Frame f = new Frame(new LexiconConfiguration(cfgFile.read()));
                f.out(mm.toMessage());
                sc.nextLine();
            }
            sc.close();
        };
    }

    private static MessageManager read(Lexicon messageLexicon) {
        List<KVEntry> entries = messageLexicon.getAllKVEntry();
        Iterator<KVEntry> it = entries.iterator();
        MessageManager mm = new MessageManager();
        while (it.hasNext()) {
            KVEntry entry = it.next();
            switch (entry.getKey()) {
                case "title":
                    mm.title(entry.getValue());
                    break;
                case "info":
                    mm.info(entry.getValue());
                    break;
                case "text":
                    mm.text(entry.getValue());
                    break;
                case "operation":
                    mm.operation(entry.getValue());
                    break;
            }
        }
        return mm;
    }

    private static ConfigureFile getDemoRes() {
        final String DEMO_RES_FILEPATH = "data\\HDQS\\Frame\\resources\\demo.cfg";
        final String DEMO_RES_VALUE = """
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
                        text =*   视频提供了功能强大的方法帮助您证明您的观点。当您单击联机视频时，可以在想要添加的视频的嵌入代码中进行粘贴。您也可以键入一个关键字以联机搜索最适合您的文档的视频。
                        text =*   为使您的文档具有专业外观，Word 提供了页眉、页脚、封面和文本框设计，这些设计可互为补充。例如，您可以添加匹配的封面、页眉和提要栏。单击“插入”，然后从不同库中选择所需元素。
                        text =*   主题和样式也有助于文档保持协调。当您单击设计并选择新的主题时，图片、图表或 SmartArt 图形将会更改以匹配新的主题。当应用样式时，您的标题会进行更改以匹配新的主题。
                        text =*   使用在需要位置出现的新按钮在 Word 中保存时间。若要更改图片适应文档的方式，请单击该图片，图片旁边将会显示布局选项按钮。当处理表格时，单击要添加行或列的位置，然后单击加号。
                        text =*   在新的阅读视图中阅读更加容易。可以折叠文档某些部分并关注所需文本。如果在达到结尾处之前需要停止读取，Word 会记住您的停止位置 - 即使在另一个设备上。
                        text =*Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Maecenas porttitor congue massa. Fusce posuere, magna sed pulvinar ultricies, purus lectus malesuada libero, sit amet commodo magna eros quis urna.
                    }
                }""";
        return new ConfigureFile(PATH.getFile(DEMO_RES_FILEPATH), DEMO_RES_VALUE);
    }
}

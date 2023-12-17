package com.plzEnterCompanyName.HDQS.text.frame;

import com.plzEnterCompanyName.HDQS.io.ConfigureFile;
import com.plzEnterCompanyName.HDQS.io.PATH;
import com.plzEnterCompanyName.HDQS.io.smartIO2.*;
import com.plzEnterCompanyName.HDQS.util.FormatCheck;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Frame implements Out, PageOutputAble, WarnAble {

    public static void demo() {
        Frame f = new Frame();
        MessageManager mm = new MessageManager();
        Message messageForTest;
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

    private final Layout layout;
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
                    tabStops = 8
                    infoValWidth = 8
                    alignment = LEFT
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

    /**
     * 获取一个字符的显示宽度
     * @return 宽度以int计，1代表单个西文字符2代表单个中文字符
     */
    protected static int measureWidth(char c) {
        String text = String.valueOf(c);
        Font font = new Font("新宋体", Font.PLAIN, 12);
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        FontMetrics metrics = g.getFontMetrics(font);
        int width = metrics.stringWidth(text);
        if (width == 12)
            width = 2;
        else if (width == 6)
            width = 1;
        else if (width == 0)
            width = 1;
        else if (width == 24)
            width = 1;
        else throw new RuntimeException(String.valueOf(width));
        return width;
    }

    /**
     * 获取该字符串的显示宽度，使用了{@link Frame#measureWidth(char c)}
     * @param s 要测量宽度的字符串
     * @return 宽度以int计，输出该字符串每个字符宽度的总和，1代表单个西文字符2代表单个中文字符
     * @throws IllegalArgumentException 当字符串是{@code null}时抛出此异常，当字符串包含
     * 换行符时抛出此异常
     */
    protected static int measureWidth(String s) {
        if (s.isEmpty()) return 0;
        FormatCheck.specialString(s,
                FormatCheck.NULL + FormatCheck.NEW_LINE);
        char[] chars = s.toCharArray();
        int returnVal = 0;
        for (char c : chars) {
            returnVal += measureWidth(c);
        }
        return returnVal;
    }

    /**
     * 不断重复字符串，直到达到指定的宽度，若遇到无法整除的中文，会自动添加空格
     * <p>例如，该方法{@code repeatW("ab", 3)}会返回{@code "aba"}，
     * 而{@code repeatW("ab稀滴", 11)}会返回{@code "ab稀滴ab稀 "}.在尾部添加空格以满足宽度要求。
     * @param s 被重复的字符串
     * @param width 要达到的宽度
     * @return 处理好的字符串
     * @throws IllegalArgumentException 当字符串长度为零时抛出此异常，
     *         当字符串包含换行符时抛出此异常
     */
    protected static String repeatW(String s, int width) {
        if (s == null) s = "null";
        FormatCheck.specialString(s,
                FormatCheck.NULL + FormatCheck.ZERO_LENGTH + FormatCheck.NEW_LINE);
        FormatCheck.intLimit(width, 1, Integer.MAX_VALUE);
        StringBuilder sb = new StringBuilder();
        char[] c = s.toCharArray();
        int index = 0;
        while (true) {
            if (width == 0) break;
            if (width == 1) {
                if (measureWidth(c[index]) == 1) {
                    sb.append(c[index]);
                }
                if (measureWidth(c[index]) == 2) {
                    sb.append(' ');
                }
                break;
            }
            sb.append(c[index]);
            width -= measureWidth(c[index]);
            if (index+1 == c.length) {
                index = 0;
            } else {
                index++;
            }
        }
        return sb.toString();
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

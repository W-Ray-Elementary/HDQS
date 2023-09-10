package com.plzEnterCompanyName.HDQS.text.frame;

import com.plzEnterCompanyName.HDQS.io.ConfigureFile;
import com.plzEnterCompanyName.HDQS.io.PATH;
import com.plzEnterCompanyName.HDQS.io.smartIO2.Message;
import com.plzEnterCompanyName.HDQS.io.smartIO2.Out;
import com.plzEnterCompanyName.HDQS.io.smartIO2.PageOutputAble;
import com.plzEnterCompanyName.HDQS.io.smartIO2.WarnAble;
import com.plzEnterCompanyName.HDQS.util.FormatCheck;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Frame implements Out, PageOutputAble, WarnAble {
    private Message currentMsg;
    private Layout layout;
    private static final String DEFAULT_CONFIG_VALUE =
            """
            Frame
            {
                width = 120
                height = 29
                BlockTypesetter
                {
                    name = SeparateLine
                }
                BlockTypesetter
                {
                    name = Tittle
                    retraction = 3
                    retractionChar = 32 // decimal : 32 = binary 00010000 = the ascii code
                                        // of blank space
                    isDrawingGameName = true
                }
                BlockTypesetter
                {
                    name = Info
                    totalWidth = 45 // The max screen space that is available. Most of the
                                    // time, Info will not take up so much screen space.
                    totalHeight = 9 // If the position of Info that defined by Layout is UP
                                    // or DOWN, this setting value "totalHeight" will be
                                    // took into consideration. Otherwise Info will
                                    // consider "totalWidth"
                    retraction = 3
                    retractionChar = 32 // decimal : 32 = binary 00010000 = the ascii code
                                        // of blank space
                    singleInfoWidthMin = 29
                    singleInfoWidthMax = 40
                    horizontalSpacing = 4
                    defaultTabStops = 8
                    alignment = LEFT
                    blankRow = AUTO
                }
                Layout
                {
                    Layer
                    {
                        type = SEPARATE_LINE
                        position = RIGHT
                    }
                    Layer
                    {
                        type = SEPARATE_LINE
                        position = UP
                    }
                    Layer
                    {
                        type = SEPARATE_LINE
                        position = LEFT
                    }
                    Layer
                    {
                        type = SEPARATE_LINE
                        position = DOWN
                    }
                    Layer
                    {
                        type = TITTLE
                        position = UP
                    }
                    Layer
                    {
                        type = SEPARATE_LINE
                        position = UP
                    }
                    Layer
                    {
                        type = INFO
                        position = RIGHT
                    }
                    Layer
                    {
                        type = SEPARATE_LINE
                        position = RIGHT
                    }
                    Layer
                    {
                        type = OPERATION
                        position = DOWN
                    }
                    Layer
                    {
                        type = SEPARATE_LINE
                        position = DOWN
                    }
                    Layer
                    {
                        type = WARNING
                        position = DOWN
                    }
                    Layer
                    {
                        type = TEXT
                        position = UP
                    }
                }
            }""";
    public static final ConfigureFile DEFAULT_CONFIG;

    static {
        DEFAULT_CONFIG = new ConfigureFile(
                PATH.getFile("settings\\frame.cfg"),
                DEFAULT_CONFIG_VALUE
        );
        DEFAULT_CONFIG.setComments("""
                //
                // this configure file is auto generated by Frame
                //
                """);
    }

    protected static int getWidth(char c) {
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

    protected static int getWidth(String s) {
        FormatCheck.specialString(s,
                FormatCheck.NULL + FormatCheck.ZERO_LENGTH + FormatCheck.NEW_LINE);
        char[] chars = s.toCharArray();
        int returnVal = 0;
        for (char c : chars) {
            returnVal += getWidth(c);
        }
        return returnVal;
    }

    public Frame() {
        layout = new Layout(DEFAULT_CONFIG.read());
    }

    public Frame(Layout layout) {
        this.layout = layout;
    }

    @Override
    public void out(Message msg) {
        this.currentMsg = msg;
    }

    @Override
    public void pageOutput() {

    }

    @Override
    public void warn(String WarnStr) {

    }
}

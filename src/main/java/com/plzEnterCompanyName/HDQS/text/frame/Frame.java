package com.plzEnterCompanyName.HDQS.text.frame;

import com.plzEnterCompanyName.HDQS.io.ConfigureFile;
import com.plzEnterCompanyName.HDQS.io.PATH;
import com.plzEnterCompanyName.HDQS.io.smartIO2.Message;
import com.plzEnterCompanyName.HDQS.io.smartIO2.Out;
import com.plzEnterCompanyName.HDQS.io.smartIO2.PageOutputAble;
import com.plzEnterCompanyName.HDQS.io.smartIO2.WarnAble;

public class Frame implements Out, PageOutputAble, WarnAble {
    private Message currentMsg;
    private Layout layout;
    public static final String DEFAULT_CONFIG_VALUE =
            """
            Frame
            {
                width = 120
                height = 29
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
    public static final ConfigureFile DEFAULT_CONFIG = new ConfigureFile(
            PATH.getFile("settings\\frame.cfg"),
            DEFAULT_CONFIG_VALUE
    );

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

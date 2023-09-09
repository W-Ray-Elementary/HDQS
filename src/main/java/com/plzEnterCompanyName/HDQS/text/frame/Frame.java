package com.plzEnterCompanyName.HDQS.text.frame;

import com.plzEnterCompanyName.HDQS.io.ConfigureFile;
import com.plzEnterCompanyName.HDQS.io.PATH;
import com.plzEnterCompanyName.HDQS.io.smartIO2.Message;
import com.plzEnterCompanyName.HDQS.io.smartIO2.Out;
import com.plzEnterCompanyName.HDQS.io.smartIO2.PageOutputAble;
import com.plzEnterCompanyName.HDQS.io.smartIO2.WarnAble;

public class Frame implements Out, PageOutputAble, WarnAble {
    private Message currentMsg;
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
                        position = right
                    }
                    Layer
                    {
                        type = SEPARATE_LINE
                        position = up
                    }
                    Layer
                    {
                        type = SEPARATE_LINE
                        position = left
                    }
                    Layer
                    {
                        type = SEPARATE_LINE
                        position = down
                    }
                    Layer
                    {
                        type = TITTLE
                        position = up
                    }
                    Layer
                    {
                        type = SEPARATE_LINE
                        position = up
                    }
                    Layer
                    {
                        type = INFO
                        position = right
                    }
                    Layer
                    {
                        type = SEPARATE_LINE
                        position = right
                    }
                    Layer
                    {
                        type = OPERATION
                        position = down
                    }
                    Layer
                    {
                        type = SEPARATE_LINE
                        position = down
                    }
                    Layer
                    {
                        type = WARNING
                        position = down
                    }
                    Layer
                    {
                        type = TEXT
                        position = up
                    }
                }
            }""";
    public static final ConfigureFile DEFAULT_CONFIG = new ConfigureFile(
            PATH.getFile("settings\\frame"),
            DEFAULT_CONFIG_VALUE
    );
    @Override
    public void out(Message msg) {
        this.currentMsg = msg;
    }

    @Override
    public void pageOutput() {

    }

    @Override
    public void warn() {

    }
}

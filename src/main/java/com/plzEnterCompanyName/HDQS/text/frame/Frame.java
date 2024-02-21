package com.plzEnterCompanyName.HDQS.text.frame;

import com.plzEnterCompanyName.HDQS.io.smartIO2.*;
import com.plzEnterCompanyName.HDQS.text.AwtRuler;
import com.plzEnterCompanyName.HDQS.text.Ruler;
import com.plzEnterCompanyName.HDQS.text.Typography;
import com.plzEnterCompanyName.HDQS.text.zh_CN_Typography;
import com.plzEnterCompanyName.HDQS.util.Configuration;

import java.io.PrintStream;

/**
 * <p>
 * 适用于Microsoft Windows cmd的文字排版处理工具。
 * </p>
 */
public class Frame implements Out, PageOutputAble, WarnAble {

    private PrintStream printStream;

    /**
     * 
     * @param printStream
     */
    public void setPrintStream(PrintStream printStream) {
        this.printStream = printStream;
    }

    private final Layout layout;
    public static final Ruler RULER = new AwtRuler();
    public static final Typography TYPO = new zh_CN_Typography(RULER);

    public Frame(Layout layout) {
        this.layout = layout;
        printStream = System.out;
    }

    public Frame(Configuration cfg) {
        this.layout = new Layout(cfg);
        printStream = System.out;
    }

    @Override
    public void out(Message msg) {
        String typed = layout.setType(msg);
        printStream.print(typed);
    }

    @Override
    public void pageOutput() {
        // comming soon.
    }

    @Override
    public void warn(String WarnStr) {
        // comming soon.
    }
}

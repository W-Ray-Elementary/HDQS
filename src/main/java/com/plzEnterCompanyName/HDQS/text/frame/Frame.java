package com.plzEnterCompanyName.HDQS.text.frame;

import com.plzEnterCompanyName.HDQS.io.smartIO2.Message;
import com.plzEnterCompanyName.HDQS.io.smartIO2.Out;
import com.plzEnterCompanyName.HDQS.io.smartIO2.PageOutputAble;
import com.plzEnterCompanyName.HDQS.io.smartIO2.WarnAble;
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
 * <p>
 * 为了开始排版，{@code Frame}需要知道三个信息：<br>
 * 1.排版时使用的布局。<br>
 * 2.要排版的内容<br>
 * 3.排版的模式<br>
 * 这些信息的内容与含义不同，作用特点及范围也不同，所以需要使用不同的方式进行配
 * 置。
 * </p>
 * <p>
 * 经过6次以上的重构，{@code Frame}中布局的管理现在由{@link Layout}进行，其
 * 实例是{@code Frame}中的一个成员变量，在构造{@code Frame}对象时完成初始化。
 * 所以创建{@code Frame}对象时需要且仅需要传入{@code Layout}对象。一般而言，
 * {@code Layout}对象需要在传入构造方法之前完成配置。为了便于从配置文件中直接读
 * 取配置，{@code Frame}额外提供了使用{@link Configuration}构造对象的构造方
 * 法。除未提及的其它方式外，以上两种方式均可把“信息1.排版时使用的布局。”配置完
 * 毕。“信息1.排版时使用的布局。”由{@link Frame#layout layout}属性存储，使用
 * {@code final}修饰，伴随这个{@code Frame}对象的整个生命流程。
 * </p>
 * <p>
 * “信息2.要排版的内容”通过{@link Frame#out(Message msg)}方法进行传递，可以看
 * 出，信息2在每次输出时进行更新。关于{@code Message}对象的创建，请参见
 * {@link Message}
 * </p>
 * <p>
 * “信息3.排版的模式”由你具体调用的输出方法决定，目前仅有
 * {@link Frame#out(Message msg)}方法可用，更多的排版模式仍在开发中。
 * </p>
 */
public class Frame implements Out, PageOutputAble, WarnAble {

    private PrintStream printStream;

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
        // coming soon.
    }

    @Override
    public void warn(String WarnStr) {
        // coming soon.
    }
}

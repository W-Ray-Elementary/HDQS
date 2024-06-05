package com.plzEnterCompanyName.HDQS.text.frame;

import com.plzEnterCompanyName.HDQS.io.smartIO2.*;
import com.plzEnterCompanyName.HDQS.util.Configuration;

import java.io.PrintStream;

/**
 * 适用于Microsoft Windows cmd的文字排版处理工具。
 *
 * <p>为了开始排版，{@code Frame}需要知道三个信息：<br>
 * 1.排版时使用的布局。<br>
 * 2.要排版的内容<br>
 * 3.排版的模式<br>
 * 这些信息的内容与含义不同，作用特点及范围也不同，所以需要使用不同的方式进行配
 * 置。
 *
 * <p>经过6次以上的重构，{@code Frame}中布局的管理现在由{@link Layout}进行，
 * 其实例是{@code Frame}中的一个成员变量，在构造{@code Frame}对象时完成初始
 * 化。所以创建{@code Frame}对象时需要且仅需要传入{@code Layout}对象。一般而
 * 言，{@code Layout}对象需要在传入构造方法之前完成配置。为了便于从配置文件中直
 * 接读取配置，{@code Frame}额外提供了使用{@link Configuration}构造对象的构
 * 造方法。除未提及的其它方式外，以上两种方式均可把“信息1.排版时使用的布局。”配置
 * 完毕。“信息1.排版时使用的布局。”由{@link Frame#layout layout}属性存储，
 * 使用{@code final}修饰，伴随这个{@code Frame}对象的整个生命流程。
 *
 * <p>“信息2.要排版的内容”通过{@link Frame#out(Message msg)}方法进行传递，
 * 可以看出，信息2在每次输出时进行更新。关于{@code Message}对象的创建，请参见
 * {@link Message}。
 *
 * <p>“信息3.排版的模式”由你具体调用的输出方法决定，目前仅有
 * {@link Frame#out(Message msg)}方法可用，更多的排版模式仍在开发中。
 *
 * @see com.plzEnterCompanyName.HDQS.text.frame.FrameExample
 */
public class Frame implements Out, PageOutputAble, WarnAble {

    /**
     * 进行输出时所使用的打印流。可以进行配置，以向不同的地方输出。
     */
    private PrintStream printStream;

    /**
     * 对输出时所使用的打印流进行配置
     *
     * @param printStream 调用该方法后，{@code Frame}将使用该打印流进行打印
     */
    public void setPrintStream(PrintStream printStream) {
        this.printStream = printStream;
    }

    /**
     * 布局对象，本类需要使用此布局进行排版。一个{@code Frame}对象对应一个{@code Layout}
     * 对象，这个布局会从构造{@code Frame}对象起伴随其整个生命流程。
     */
    private final Layout layout;

    /**
     * 上一次输出的Message
     */
    private Message lastTime = new MessageManager().toMessage();

    /**
     * 使用一个布局对象直接创建，布局对象应该在传入前配置完毕。
     */
    public Frame(Layout layout) {
        this.layout = layout;
        printStream = System.out;
    }

    /**
     * 从配置中创建，配置需要有特定的格式，格式要求应该在{@link Layout}中。
     */
    public Frame(Configuration cfg) {
        this.layout = new Layout(cfg);
        printStream = System.out;
    }

    @Override
    public void out(Message msg) {
        lastTime = msg;
        String typed = layout.setType(lastTime);
        printStream.print(typed);
    }

    @Override
    public void pageOutput() {
    }

    @Override
    public void warn(String warnStr) {
        lastTime.advancedInfo.put("FrameWarnStr", warnStr);
        String typed = layout.setType(lastTime);
        /* 阅后即焚 */
        lastTime.advancedInfo.remove("FrameWarnStr");
        printStream.print(typed);
    }
}

package io.smartIO2;

import com.plzEnterCompanyName.HDQS.io.smartIO2.*;

public class TestMessageManager {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        TEST_MessageManager();
        long cost = System.currentTimeMillis() - startTime;
        System.out.println("com.plzEnterCompanyName.HDQS.io.smartIO2.MessageManager 的测试成功完成于"
                + ' ' + cost + ' ' + "ms");
    }

    private static void TEST_MessageManager() {
        MessageManager mm = new MessageManager();
        mm.title("今日新闻");
        mm.text("利用 Word 在你所需之处显示的新按钮来节省时间。若要更改图片在文档中的显示方式，请单击该图片，旁边会显示一个用于布局选项的按钮。处理表格时，单击要添加行或列的位置，然后单击加号。");
        mm.text("在新的阅读视图中，阅读也更容易。你可以折叠部分文档，以将关注点放在所需的文本上。如果你需要在读完整个文档之前停止阅读，Word 会记忆你上次离开的位置 - 即使在其他设备上也是如此。");
        mm.text("主题和样式也有助于保持文档的协调性。单击“设计”并选择新主题时，图片、图表和 SmartArt 图形将会发生改变以匹配新主题。应用样式时，标题将会发生改变以匹配新主题。");
        mm.text("视频提供了一种强大的方式来帮助你证明自己的观点。单击“联机视频”时，可以粘贴要添加的视频的嵌入代码。你还可以键入一个关键字，以便在线搜索最适合你的文档的视频。");
        mm.text("为了使你的文档看起来更专业化，Word 提供了页眉、页脚、封面和文本框设计，它们相互补充。例如，可以添加匹配的封面页、页眉和边栏。 ");
        mm.text("图片题注：图片题注：为了使你的文档看起来更专业化，Word 提供了页眉、页脚、封面和文本框设计，它们相互补充。");
        Message msg = mm.toMessage();
        if (!"今日新闻".equals(msg.title.get(0)))
            throw new RuntimeException();
    }
}

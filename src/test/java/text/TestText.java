package text;

import com.plzEnterCompanyName.HDQS.text.AwtRuler;
import com.plzEnterCompanyName.HDQS.text.zh_CN_Typography;

import java.util.ArrayList;
import java.util.List;

public class TestText {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        TEST_zh_CN_Typography();
        long cost = System.currentTimeMillis() - startTime;
        System.out.println("com.plzEnterCompanyName.HDQS.text 的测试成功完成于" + ' ' + cost + ' ' + "ms");
    }

    public static final String s0 = "";
    public static final String s1 = "    视频提供了功能强大的方法帮助您证明您的观点。当您单击联机视频时，可以在想要添加的视频的嵌入代码中进行粘贴。您也可以键入一个关键字以联机搜索最适合您的文档的视频。";
    public static final String s2 = "    为使您的文档具有专业外观，Word 提供了页眉、页脚、封面和文本框设计，这些设计可互为补充。例如，您可以添加匹配的封面、页眉和提要栏。单击“插入”，然后从不同库中选择所需元素。";
    public static final String s3 = "    主题和样式也有助于文档保持协调。当您单击设计并选择新的主题时，图片、图表或 SmartArt 图形将会更改以匹配新的主题。当应用样式时，您的标题会进行更改以匹配新的主题。";
    public static final String s4 = "    使用在需要位置出现的新按钮在 Word 中保存时间。若要更改图片适应文档的方式，请单击该图片，图片旁边将会显示布局选项按钮。当处理表格时，单击要添加行或列的位置，然后单击加号。";
    public static final String s5 = "    在新的阅读视图中阅读更加容易。可以折叠文档某些部分并关注所需文本。如果在达到结尾处之前需要停止读取，Word 会记住您的停止位置 - 即使在另一个设备上。";

    private static void TEST_zh_CN_Typography() {
        zh_CN_Typography typography = new zh_CN_Typography(new AwtRuler());
        final int expectWidth = 30;
        List<String> p0 = typography.lineBreak(s0, expectWidth);
        List<String> p1 = typography.lineBreak(s1, expectWidth);
        List<String> p2 = typography.lineBreak(s2, expectWidth);
        List<String> p3 = typography.lineBreak(s3, expectWidth);
        List<String> p4 = typography.lineBreak(s4, expectWidth);
        List<String> p5 = typography.lineBreak(s5, expectWidth);
        List<String> all = new ArrayList<>();
        all.addAll(p0);
        all.addAll(p1);
        all.addAll(p2);
        all.addAll(p3);
        all.addAll(p4);
        all.addAll(p5);
        boolean flag = false;
        AwtRuler aRuler = new AwtRuler();
        for (String s : all)
            if (aRuler.measureWidth(s) != expectWidth)
                flag = true;
        if (flag)
            throw new RuntimeException("zh_CN_Typography未能如期断行！");
    }
}

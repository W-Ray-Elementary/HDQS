package com.plzEnterCompanyName.HDQS.text.frame;

import com.plzEnterCompanyName.HDQS.io.smartIO2.Info;
import com.plzEnterCompanyName.HDQS.io.smartIO2.Message;
import com.plzEnterCompanyName.HDQS.util.Lexicon;

import java.util.Arrays;

/**
 * 本java类注释中，Info指代{@link com.plzEnterCompanyName.HDQS.io.smartIO2.Info Info}
 * 而BT_Info指代{@link com.plzEnterCompanyName.HDQS.text.frame.BT_Info BT_Info}
 */
public class BT_Info extends BlockTypesetter {

    /**
     * <i>当且仅当position为LEFT, RIGHT时，TOTAL_WIDTH有效</i>
     *
     * <p>最大可用屏幕宽度，绝大多数时候，BT_Info不会使用如此多的空间。
     *
     * <p>该数值较小时。BT_Info会试图缩短单个Info的显示宽度,而当该数值较大时，
     * BT_Info会尝试在一行的空间中放下多个Info。
     *
     * <p>特别地，就像绝大多数BlockTypesetter的子类一样，该数值实在时太小太
     * 小时，输出'##########'以期望玩家增加该值大小。
     */
    protected final int TOTAL_WIDTH;

    /**
     * <i>当且仅当position为UP, DOWN时，TOTAL_HEIGHT有效</i>
     *
     * <p>最大可用屏幕行数。
     *
     * <p>例如，设一BT_Info，其一行可显示3个Info并且Info也选
     * 择了一行放三个Info，TOTAL_HEIGHT为5，进行排版时，来了10个Info。按照一行
     * 放三个的原则，这些Info占了4列，据此BT_Info完成了排版并将占掉了4行这个情况
     * 如实上报给 Layer -> Layout -> Frame 。排版完成
     *
     * <p>又例如，所有条件与上一组相同，但是来了16个Info，这时候BT_Info发现空间
     * 不够用了，它又没有权利打断 Frame 的排版请求，于是默不作声地把那五行全部填
     * 充上了'#######'。随后告诉 Layer -> Layout -> Frame 自己用了5行。排版
     * 完成。BT_Info这样做，是希望玩家增加该值大小
     */
    protected final int TOTAL_HEIGHT;

    /**
     * 要缩进几个字符，该数值会将INDENTATION_CHAR代表的字符重复INDENTATION次后
     * 加在开头的地方。
     */
    protected final int INDENTATION;

    /**
     * 缩进字符,INDENTATION_CHAR重复INDENTATION次后加在开头的地方。
     */
    protected final char INDENTATION_CHAR;

    /**
     * <i>当一行有2倍MAX_SINGLE_INFO_WIDTH及以上的空间时，此值无效</i>
     *
     * <p>若可用宽度小于2倍MAX_SINGLE_INFO_WIDTH，BT_Info便只能在一行里放下
     * 一个Info了，更进一步的，若是可用宽度小于MAX_SINGLE_INFO_WIDTH，BT_Info
     * 会试图缩短单个Info的显示宽度。我吃摩尔，若是可用宽度小于
     * MIN_SINGLE_INFO_WIDTH，BT_Info输出'##########'以期望玩家进行调整。
     */
    protected final int MIN_SINGLE_INFO_WIDTH;

    /**
     * <i>当一行空间不足MAX_SINGLE_INFO_WIDTH时，此值无效</i>
     *
     * <p>若可用宽度大于2倍MAX_SINGLE_INFO_WIDTH，BT_Info就会放下尽可能多的
     * Info，并且每个Info都是此值大小。
     */
    protected final int MAX_SINGLE_INFO_WIDTH;

    /**
     * 要是一行里放得下多个Info，BT_Info要怎么知道每个Info之间空几格呢？
     * 这个数值就是解决这个问题的。
     */
    protected final int HORIZONTAL_SPACING;

    /**
     * 懒得解释了，上案例！
     *
     * <p>TAB_STOPS 为4时：
     * <blockquote><pre>{@code
     *     字  123
     *     A字 123
     *     字字    123
     *     A字字   123
     *     字字字  123
     *     A字字字 123
     *     字字字字    123
     * }</pre></blockquote>
     *
     * <p>TAB_STOPS 为5时：
     * <blockquote><pre>{@code
     *      字   123
     *      A字  123
     *      字字 123
     *      A字字     123
     *      字字字    123
     *      A字字字   123
     *      字字字字  123
     * }</pre></blockquote>
     *
     * <p>TAB_STOPS 为6时：
     * <blockquote><pre>{@code
     *      字    123
     *      A字   123
     *      字字  123
     *      A字字 123
     *      字字字      123
     *      A字字字     123
     *      字字字字    123
     * }</pre></blockquote>
     */
    protected final int TAB_STOPS;

    /**
     * Info是包含名字和数值的，有的时候，数值很长，不得不使用科学计数法
     * <p>INFO_VAL_WIDTH 为8时：
     * <blockquote><pre>{@code
     *      名字    123      // 123
     *      名字    12345678 // 123
     *      名字    1.234e+9 // 123456789
     *      名字    1.23e+11 // 12345678901
     * }</pre></blockquote>
     */
    protected final int INFO_VAL_WIDTH;

    /**
     * 数值是左对齐，中对齐，还是在名字后面直接加TAB_STOPS个空格？
     * 这个数值就是解决这个问题的。
     */
    protected final Aliment ALIMENT;

    enum Aliment {
        LEFT, RIGHT, BAR
    }

    /**
     * 有时，Microsoft Windows cmd 的行距会显得过于拥挤，就要看看加不加空行
     *
     * <p>TRUE, FALSE都挺好理解的，就是AUTO。AUTO代表着，空间够的时候空行，
     * 不够的时候不空行，实在不够就输出'##########'以期望玩家进行调整。
     */
    protected final BlankRow BLANK_ROW;

    enum BlankRow {
        TRUE, FALSE, AUTO
    }

    private int cacheIndex;

    /**
     * 创建BT_Info对象以用于排版
     * @param position position是一个特殊的枚举，具体影响我希望由用户自行测试，
     *                 通过实践得到答案。
     * @param config   该BT_Info的配置文件
     */
    protected BT_Info(SupportedBT_Position position, Lexicon config) {
        super(position);
        String totalWidthStr         = config.getFirst("totalWidth"        );
        String totalHeightStr        = config.getFirst("totalHeight"       );
        String indentationStr        = config.getFirst("indentation"       );
        String indentationCharStr    = config.getFirst("indentationChar"   );
        String singleInfoWidthMinStr = config.getFirst("singleInfoWidthMin");
        String singleInfoWidthMaxStr = config.getFirst("singleInfoWidthMax");
        String horizontalSpacingStr  = config.getFirst("horizontalSpacing" );
        String tabStopsStr           = config.getFirst("tabStops"          );
        String infoValWidthStr       = config.getFirst("infoValWidth"      );
        String alignmentStr          = config.getFirst("alignment"         );
        String blankRowStr           = config.getFirst("blankRow"          );
        TOTAL_WIDTH           =        Integer.parseInt(totalWidthStr        );
        TOTAL_HEIGHT          =        Integer.parseInt(totalHeightStr       );
        INDENTATION           =        Integer.parseInt(indentationStr       );
        INDENTATION_CHAR      = (char) Integer.parseInt(indentationCharStr   );
        MIN_SINGLE_INFO_WIDTH =        Integer.parseInt(singleInfoWidthMinStr);
        MAX_SINGLE_INFO_WIDTH =        Integer.parseInt(singleInfoWidthMaxStr);
        HORIZONTAL_SPACING    =        Integer.parseInt(horizontalSpacingStr );
        TAB_STOPS             =        Integer.parseInt(tabStopsStr          );
        INFO_VAL_WIDTH        =        Integer.parseInt(infoValWidthStr      );
        switch (alignmentStr) {
            case "LEFT"  -> ALIMENT = Aliment.LEFT ;
            case "RIGHT" -> ALIMENT = Aliment.RIGHT;
            case "BAR"   -> ALIMENT = Aliment.BAR  ;
            default -> throw new RuntimeException("Unsupported alignment : " + alignmentStr);
        }
        switch (blankRowStr) {
            case "TRUE"  -> BLANK_ROW = BlankRow.TRUE ;
            case "FALSE" -> BLANK_ROW = BlankRow.FALSE;
            case "AUTO"  -> BLANK_ROW = BlankRow.AUTO ;
            default -> throw new RuntimeException("Unsupported blank row action : " + blankRowStr);
        }
    }

    @Override
    protected int setType(Message message, int posLimit) {
        String $ = String.valueOf('$').repeat(TOTAL_WIDTH);
        if (position == SupportedBT_Position.UP || position == SupportedBT_Position.DOWN)
            cache = new String[TOTAL_HEIGHT];
        else
            cache = new String[posLimit];
        Arrays.fill(cache, $);
        cacheIndex = 0;
        return switch (position) {
            case UP, DOWN -> TOTAL_HEIGHT;
            case LEFT, RIGHT -> TOTAL_WIDTH;
        };
    }
    /**
     * 便捷地计算一下，对于当前的可用显示宽度，屏幕上可以放得下几栏info
     * @param availableWidth 允许使用的显示宽度
     * @return 返回值是一个int数组，数组下标为0的数据代表分几栏，下标为1的数据代表每栏长度
     *         下标为2的为尾间隔。
     */
    private int[] tryToPlace(int availableWidth) {
        if(availableWidth < MIN_SINGLE_INFO_WIDTH) {
            throw new RuntimeException(spaceInsufficientMsg);
        }
        if(availableWidth >= MAX_SINGLE_INFO_WIDTH) return new int[]{(int) (double) (availableWidth / MAX_SINGLE_INFO_WIDTH),MAX_SINGLE_INFO_WIDTH,availableWidth % MAX_SINGLE_INFO_WIDTH};
        return new int[]{1,availableWidth,0};
    }

    /*
    *
    * */

    private String setType0(Info info, int availableWidth) {
        if (info == null) return Frame.repeatW(null, availableWidth);
        StringBuilder sb = new StringBuilder();
        setType : {
            if (info.getValueString(INFO_VAL_WIDTH).isEmpty()) {
                int w = Frame.measureWidth(info.getName());
                if (availableWidth < w) break setType;
                availableWidth -= w;
                sb.append(info.getName());
            }

        }
        sb.append(" ".repeat(Math.max(0, availableWidth)));
        return sb.toString();
    }

    @Override
    protected String getCache() {
        return cache[cacheIndex++];
    }

    @Override
    protected void nextPage() {
        cacheIndex = 0;
    }
}

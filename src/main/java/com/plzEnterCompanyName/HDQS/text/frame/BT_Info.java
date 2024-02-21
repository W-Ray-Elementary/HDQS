package com.plzEnterCompanyName.HDQS.text.frame;

import com.plzEnterCompanyName.HDQS.io.smartIO2.Info;
import com.plzEnterCompanyName.HDQS.io.smartIO2.Message;
import com.plzEnterCompanyName.HDQS.util.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * <i>此类编码水平较高，乃BlockTypesetter中的典范</i>
 * </p>
 * <p>
 * 本java类注释中，Info指代{@link com.plzEnterCompanyName.HDQS.io.smartIO2.Info Info}
 * 而BT_Info指代{@link com.plzEnterCompanyName.HDQS.text.frame.BT_Info BT_Info}
 * </p>
 */
public class BT_Info extends BlockTypesetter {

    /**
     * <p>
     * <i>当且仅当position为LEFT, RIGHT时，TOTAL_WIDTH有效</i>
     * </p>
     * <p>
     * 最大可用屏幕宽度，绝大多数时候，BT_Info不会使用如此多的空间。
     * </p>
     * <p>
     * 该数值较小时。BT_Info会试图缩短单个Info的显示宽度,而当该数值较大时，
     * BT_Info会尝试在一行的空间中放下多个Info。
     * </p>
     * <p>
     * 特别地，就像绝大多数BlockTypesetter的子类一样，该数值实在时太小太
     * 小时，输出'##########'以期望玩家增加该值大小。
     * </p>
     */
    protected final int TOTAL_WIDTH;

    /**
     * <p>
     * <i>当且仅当position为UP, DOWN时，TOTAL_HEIGHT有效</i>
     * </p>
     * <p>
     * 最大可用屏幕行数。
     * </p>
     * <p>
     * 例如，设一BT_Info，其一行可显示3个Info并且Info也选
     * 择了一行放三个Info，TOTAL_HEIGHT为5，进行排版时，来了10个Info。按照一行
     * 放三个的原则，这些Info占了4列，据此BT_Info完成了排版并将占掉了4行这个情况
     * 如实上报给 Layer -> Layout -> Frame 。排版完成
     * <p/>
     * <p>
     * 又例如，所有条件与上一组相同，但是来了16个Info，这时候BT_Info发现空间
     * 不够用了，它又没有权利打断 Frame 的排版请求，于是默不作声地把那五行全部填
     * 充上了'#######'。随后告诉 Layer -> Layout -> Frame 自己用了5行。排版
     * 完成。BT_Info这样做，是希望玩家增加该值大小
     * </p>
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
     * <p>
     * <i>当一行有2倍MAX_SINGLE_INFO_WIDTH及以上的空间时，此值无效</i>
     * </p>
     * <p>
     * 若可用宽度小于2倍MAX_SINGLE_INFO_WIDTH，BT_Info便只能在一行里放下
     * 一个Info了，更进一步的，若是可用宽度小于MAX_SINGLE_INFO_WIDTH，BT_Info
     * 会试图缩短单个Info的显示宽度。我吃摩尔，若是可用宽度小于
     * MIN_SINGLE_INFO_WIDTH，BT_Info输出'##########'以期望玩家进行调整。
     * </p>
     */
    protected final int MIN_SINGLE_INFO_WIDTH;

    /**
     * <p>
     * <i>当一行空间不足MAX_SINGLE_INFO_WIDTH时，此值无效</i>
     * </p>
     * <p>
     * 若可用宽度大于2倍MAX_SINGLE_INFO_WIDTH，BT_Info就会放下尽可能多的
     * Info，并且每个Info都是此值大小。
     * </p>
     */
    protected final int MAX_SINGLE_INFO_WIDTH;

    /**
     * 要是一行里放得下多个Info，BT_Info要怎么知道每个Info之间空几格呢？
     * 这个数值就是解决这个问题的。
     */
    protected final int HORIZONTAL_SPACING;

    /**
     * <p>
     * 有时，Microsoft Windows cmd 的行距会显得过于拥挤，就要看看加不加空行
     * </p>
     * <p>
     * TRUE, FALSE都挺好理解的，就是AUTO。AUTO代表着，空间够的时候空行，
     * 不够的时候不空行，实在不够就输出'##########'以期望玩家进行调整。
     * </p>
     */
    protected final BlankRowStatus BLANK_ROW;

    private int cacheIndex;

    /**
     * 创建BT_Info对象以用于排版
     * 
     * @param position position是一个特殊的枚举，具体影响我希望由用户自行测试，
     *                 通过实践得到答案。
     * @param config   该BT_Info的配置文件
     */
    protected BT_Info(SupportedBT_Position position, Configuration config) {
        super(position);
        TOTAL_WIDTH = config.getInt("totalWidth");
        TOTAL_HEIGHT = config.getInt("totalHeight");
        INDENTATION = config.getInt("indentation");
        INDENTATION_CHAR = (char) config.getInt("indentationChar");
        MIN_SINGLE_INFO_WIDTH = config.getInt("singleInfoWidthMin");
        MAX_SINGLE_INFO_WIDTH = config.getInt("singleInfoWidthMax");
        HORIZONTAL_SPACING = config.getInt("horizontalSpacing");
        String blankRowStr = config.get("blankRow");
        switch (blankRowStr) {
            case "TRUE" -> BLANK_ROW = BlankRowStatus.TRUE;
            case "FALSE" -> BLANK_ROW = BlankRowStatus.FALSE;
            case "AUTO" -> BLANK_ROW = BlankRowStatus.AUTO;
            default -> throw new RuntimeException("Unsupported blank row action : " + blankRowStr);
        }
    }

    @Override
    protected int setType(Message message, final int posLimit) {
        String indentationStr = String.valueOf(INDENTATION_CHAR).repeat(INDENTATION);
        String spacing = Frame.RULER.repeatW(" ", HORIZONTAL_SPACING);
        final int totalWidth;
        final int totalHeight;
        final int secondLimit;
        if (position == SupportedBT_Position.UP || position == SupportedBT_Position.DOWN) {
            totalWidth = posLimit - INDENTATION;
            totalHeight = TOTAL_HEIGHT;
            secondLimit = TOTAL_HEIGHT;
        } else {
            totalWidth = TOTAL_WIDTH - INDENTATION;
            totalHeight = posLimit;
            secondLimit = TOTAL_WIDTH;
        }
        String blankLineWI /* WI: with indentation */
                = Frame.RULER.repeatW(" ", totalWidth + INDENTATION);
        int singleWidth = singleWidth(totalWidth);
        String endOfLineSpace = Frame.RULER.repeatW(" ", endOfLineSpace(totalWidth));
        List<String> lines = new ArrayList<>();
        { // 防止对 infosStr 进行意外的引用
            List<String> infosStr = new ArrayList<>(message.infos.size());
            for (Info info : message.infos) {
                infosStr.add(setType0(info, singleWidth));
            }
            Iterator<String> it = infosStr.iterator();
            while (it.hasNext()) {
                StringBuilder sb = new StringBuilder(indentationStr);
                int availWidth = totalWidth;
                if (it.hasNext()) {
                    sb.append(it.next());
                    availWidth -= singleWidth;
                }
                while (it.hasNext()) {
                    if (availWidth >= HORIZONTAL_SPACING + MAX_SINGLE_INFO_WIDTH) {
                        sb.append(spacing);
                        availWidth -= HORIZONTAL_SPACING;
                        sb.append(it.next());
                        availWidth -= singleWidth;
                        continue;
                    }
                    break;
                }
                sb.append(endOfLineSpace);
                lines.add(sb.toString());
            }
        }
        cache = new String[totalHeight];
        Arrays.fill(cache, blankLineWI);
        if (lines.isEmpty()) {
            return secondLimit;
        }
        BlankRowStatus currentBlankRow;
        if (BLANK_ROW == BlankRowStatus.AUTO) {
            if (totalHeight >= (lines.size() * 2 - 1))
                currentBlankRow = BlankRowStatus.TRUE;
            else
                currentBlankRow = BlankRowStatus.FALSE;
        } else
            currentBlankRow = BLANK_ROW;
        int lineCount = 0;
        int cacheCount = 0;
        if (lines.size() > totalHeight) {
            Arrays.fill(cache, String.valueOf('#').repeat(totalWidth + INDENTATION));
        } else {
            cache[cacheCount++] = lines.get(lineCount++);
            while (lineCount < lines.size()) {
                if (currentBlankRow == BlankRowStatus.TRUE) {
                    cacheCount++;
                }
                cache[cacheCount++] = lines.get(lineCount++);
            }
        }
        return secondLimit;
    }

    private int singleWidth(int totalWidth) {
        return (totalWidth < MAX_SINGLE_INFO_WIDTH + HORIZONTAL_SPACING + MAX_SINGLE_INFO_WIDTH)
                ? Math.min(totalWidth, MAX_SINGLE_INFO_WIDTH)
                : MAX_SINGLE_INFO_WIDTH;
    }

    /*
     * 能run不出BUG就行，我知道这里的代码可以简化。
     */
    private int endOfLineSpace(final int totalWidth) {
        if (totalWidth < MAX_SINGLE_INFO_WIDTH) {
            return 0;
        } else if (totalWidth < MAX_SINGLE_INFO_WIDTH + HORIZONTAL_SPACING + MAX_SINGLE_INFO_WIDTH) {
            return totalWidth - MAX_SINGLE_INFO_WIDTH;
        } else {
            return (totalWidth - MAX_SINGLE_INFO_WIDTH) % (MAX_SINGLE_INFO_WIDTH + HORIZONTAL_SPACING);
        }
    }

    private String setType0(Info info, int availableWidth) {
        if (info == null)
            return Frame.RULER.repeatW(null, availableWidth);
        String returnVal = "";
        String infoStr = info.getName();
        int neededWidth = Frame.RULER.measureWidth(infoStr);
        if (neededWidth > availableWidth)
            return String.valueOf('#').repeat(availableWidth);
        int endBlanks = availableWidth - neededWidth;
        returnVal += infoStr + String.valueOf(' ').repeat(endBlanks);
        return returnVal;
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

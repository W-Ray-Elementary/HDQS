package com.plzEnterCompanyName.HDQS.text.frame;

import com.plzEnterCompanyName.HDQS.io.smartIO2.Info;
import com.plzEnterCompanyName.HDQS.io.smartIO2.Message;
import com.plzEnterCompanyName.HDQS.util.Lexicon;
import com.plzEnterCompanyName.HDQS.util.Lexicons;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BT_Info extends BlockTypesetter {
    protected BT_Info(SupportedBT_Position position) {
        super(position);
    }

    public static final int TOTAL_WIDTH;
    public static final int TOTAL_HEIGHT;
    public static final int RETRACTION;
    public static final char RETRACTION_CHAR;
    public static final int MIN_SINGLE_INFO_WIDTH;
    public static final int MAX_SINGLE_INFO_WIDTH;
    public static final int HORIZONTAL_SPACING;
    public static final int TAB_STOPS;
    public static final int INFO_VAL_WIDTH;
    public static final Aliment ALIMENT;
    public static final BlankRow BLANK_ROW;
    static {
        Lexicon config = Lexicons.orderName(Frame.DEFAULT_CONFIG.read(),
                "BlockTypesetter", "Info");
        String totalWidthStr         = config.getFirst("totalWidth"        );
        String totalHeightStr        = config.getFirst("totalHeight"       );
        String retractionStr         = config.getFirst("retraction"        );
        String retractionCharStr     = config.getFirst("retractionChar"    );
        String singleInfoWidthMinStr = config.getFirst("singleInfoWidthMin");
        String singleInfoWidthMaxStr = config.getFirst("singleInfoWidthMax");
        String horizontalSpacingStr  = config.getFirst("horizontalSpacing" );
        String tabStopsStr           = config.getFirst("tabStops"          );
        String infoValWidthStr       = config.getFirst("infoValWidth"      );
        String alignmentStr          = config.getFirst("alignment"         );
        String blankRowStr           = config.getFirst("blankRow"          );
        TOTAL_WIDTH           =        Integer.parseInt(totalWidthStr        );
        TOTAL_HEIGHT          =        Integer.parseInt(totalHeightStr       );
        RETRACTION            =        Integer.parseInt(retractionStr        );
        RETRACTION_CHAR       = (char) Integer.parseInt(retractionCharStr    );
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

    private int cacheIndex;

    @Override
    protected void setType(Message message, int firstPosLimit) {
        int rW = Frame.getWidth(String.valueOf(RETRACTION_CHAR).repeat(RETRACTION));
        List<String> effectiveLines = new ArrayList<>();
        int tasksRemain = message.infos.size();
        if (position == SupportedBT_Position.UP || position == SupportedBT_Position.DOWN) {
            int[] placeInfo = tryToPlace(firstPosLimit - rW);
            lines : for (int l = 0; l <= TOTAL_HEIGHT; l++) {
                Iterator<Info> it = message.infos.iterator();
                while (it.hasNext()) {
                    Info info = it.next();
                }
            }
        } else {

        }
        isTyped = true;
        cacheIndex = 0;
    }

    /**
     * 便捷地计算一下，对于当前的可用显示宽度，屏幕上可以放得下几栏info
     * @param maxTotalWidth 允许使用的显示宽度
     * @return 返回值是一个int数组，数组下标为0的数据代表分几栏，下标为1的数据代表每栏长度
     *         下标为2的为尾间隔。
     */
    private static int[] tryToPlace(int maxTotalWidth) {
        int[] placeInfo = new int[3];
        if (maxTotalWidth < 2* MAX_SINGLE_INFO_WIDTH && maxTotalWidth > MIN_SINGLE_INFO_WIDTH)
            return new int[]{
                    1,
                    Math.min(MAX_SINGLE_INFO_WIDTH, maxTotalWidth),
                    maxTotalWidth - Math.min(MAX_SINGLE_INFO_WIDTH, maxTotalWidth)
            };
        int available = maxTotalWidth;
        if (maxTotalWidth >= 2* MAX_SINGLE_INFO_WIDTH) {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                available -= MAX_SINGLE_INFO_WIDTH;
                available -= HORIZONTAL_SPACING;
                if (available < 0)
                    return new int[]{
                            i,
                            MAX_SINGLE_INFO_WIDTH,
                            maxTotalWidth - i* MAX_SINGLE_INFO_WIDTH - (--i)*HORIZONTAL_SPACING
                    };
            }
        }
        throw new RuntimeException(spaceInsufficientMsg);
    }

    private String setType0(Info info, int availableWidth) {
        if (info == null) return String.valueOf(' ').repeat(availableWidth);
        StringBuilder sb = new StringBuilder();
        setType : {
            if (info.getValueString(INFO_VAL_WIDTH).isEmpty()) {
                int w = Frame.getWidth(info.getName());
                if (availableWidth < w) break setType;
                availableWidth -= w;
                sb.append(info.getName());
            }

        }
        sb.append(" ".repeat(Math.max(0, availableWidth)));
        return sb.toString();
    }

    public static void main(String[] args) {

    }

    @Override
    protected int getSecondPosLimit() {
        if (isTyped) {
            isTyped = false;
            return -1; // (position == SupportedBT_Position.UP || position == SupportedBT_Position.DOWN) ?
                       // TOTAL_HEIGHT : TOTAL_WIDTH;
        }
        else throw new RuntimeException(untypedMsg);
    }

    @Override
    protected String getCache() {
        return cache[cacheIndex++];
    }

    enum Aliment {
        LEFT, RIGHT, BAR
    }

    enum BlankRow {
        TRUE, FALSE, AUTO
    }
}

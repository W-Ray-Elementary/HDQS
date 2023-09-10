package com.plzEnterCompanyName.HDQS.text.frame;

import com.plzEnterCompanyName.HDQS.io.smartIO2.Info;
import com.plzEnterCompanyName.HDQS.io.smartIO2.Message;
import com.plzEnterCompanyName.HDQS.util.Lexicon;
import com.plzEnterCompanyName.HDQS.util.Lexicons;

import java.util.ArrayList;
import java.util.Arrays;

public class BT_Info extends BlockTypesetter {
    protected BT_Info(SupportedBT_Position position) {
        super(position);
    }

    public static final int TOTAL_WIDTH;
    public static final int TOTAL_HEIGHT;
    public static final int SINGLE_INFO_WIDTH_MIN;
    public static final int SINGLE_INFO_WIDTH_MAX;
    public static final int HORIZONTAL_SPACING;
    public static final int DEFAULT_TAB_STOPS;
    public static final Aliment ALIMENT;
    public static final BlankRow BLANK_ROW;
    static {
        Lexicon config = Lexicons.orderName(Frame.DEFAULT_CONFIG.read(),
                "BlockTypesetter", "Info");
        String totalWidthStr = config.getFirst("totalWidth");
        String totalHeightStr = config.getFirst("totalHeight");
        String singleInfoWidthMinStr = config.getFirst("singleInfoWidthMin");
        String singleInfoWidthMaxStr = config.getFirst("singleInfoWidthMax");
        String horizontalSpacingStr = config.getFirst("horizontalSpacing");
        String defaultTabStopsStr = config.getFirst("defaultTabStops");
        String alignmentStr = config.getFirst("alignment");
        String blankRowStr = config.getFirst("blankRow");
        TOTAL_WIDTH = Integer.parseInt(totalWidthStr);
        TOTAL_HEIGHT = Integer.parseInt(totalHeightStr);
        SINGLE_INFO_WIDTH_MIN = Integer.parseInt(singleInfoWidthMinStr);
        SINGLE_INFO_WIDTH_MAX = Integer.parseInt(singleInfoWidthMaxStr);
        HORIZONTAL_SPACING = Integer.parseInt(horizontalSpacingStr);
        DEFAULT_TAB_STOPS = Integer.parseInt(defaultTabStopsStr);
        switch (alignmentStr) {
            case "LEFT" -> ALIMENT = Aliment.LEFT;
            case "RIGHT" -> ALIMENT = Aliment.RIGHT;
            case "BAR" -> ALIMENT = Aliment.BAR;
            default -> throw new RuntimeException("Unsupported alignment : " + alignmentStr);
        }
        switch (blankRowStr) {
            case "TRUE" -> BLANK_ROW = BlankRow.TRUE;
            case "FALSE" -> BLANK_ROW = BlankRow.FALSE;
            case "AUTO" -> BLANK_ROW = BlankRow.AUTO;
            default -> throw new RuntimeException("Unsupported blank row action : " + blankRowStr);
        }
    }

    private int cacheIndex;

    @Override
    protected void setType(Message message, int firstPosLimit) {
        if (position == SupportedBT_Position.UP || position == SupportedBT_Position.DOWN) {
            int[] placeInfo = tryToPlace(firstPosLimit);
        } else {

        }
        isTyped = true;
        cacheIndex = 0;
    }

    /**
     * @param maxTotalWidth
     * @return 返回值是一个int数组，数组下标为0的数据代表分几栏，下标为1的数据代表每栏长度
     *         下标为2的为尾间隔。
     */
    private static int[] tryToPlace(int maxTotalWidth) {
        int[] placeInfo = new int[3];
        if (maxTotalWidth < 2*SINGLE_INFO_WIDTH_MAX && maxTotalWidth > SINGLE_INFO_WIDTH_MIN)
            return new int[]{
                    1,
                    Math.min(SINGLE_INFO_WIDTH_MAX, maxTotalWidth),
                    maxTotalWidth - Math.min(SINGLE_INFO_WIDTH_MAX, maxTotalWidth)
            };
        int available = maxTotalWidth;
        if (maxTotalWidth >= 2*SINGLE_INFO_WIDTH_MAX) {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                available -= SINGLE_INFO_WIDTH_MAX;
                available -= HORIZONTAL_SPACING;
                if (available < 0)
                    return new int[]{
                            i,
                            SINGLE_INFO_WIDTH_MAX,
                            maxTotalWidth - i*SINGLE_INFO_WIDTH_MAX - (--i)*HORIZONTAL_SPACING
                                    + i
                                    // 这个“+ i”可能看起来很迷惑，但请不要删除，此程序依靠它运行。
                    };
            }
        }
        throw new RuntimeException(spaceInsufficientMsg);
    }

    private String setType0(Info info, int availableWidth) {
        if (info == null) return String.valueOf(' ').repeat(availableWidth);
        StringBuilder sb = new StringBuilder();
        setType : {
            if (info.getValueString().isEmpty()) {
                int w = Frame.getWidth(info.getName());
                if (availableWidth < w) break setType;
                availableWidth -= w;
                sb.append(info.getName());
            }
        }
        sb.append(" ".repeat(Math.max(0, availableWidth)));
        return sb.toString();
    }

    @Override
    protected int getSecondPosLimit() {
        if (isTyped) {
            isTyped = false;
            return  (position == SupportedBT_Position.UP || position == SupportedBT_Position.DOWN) ?
                    TOTAL_HEIGHT : TOTAL_WIDTH;
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

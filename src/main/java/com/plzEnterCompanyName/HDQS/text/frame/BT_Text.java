package com.plzEnterCompanyName.HDQS.text.frame;

import com.plzEnterCompanyName.HDQS.io.smartIO2.Message;
import com.plzEnterCompanyName.HDQS.text.Typography;
import com.plzEnterCompanyName.HDQS.text.zh_CN_Typography;
import com.plzEnterCompanyName.HDQS.util.Lexicon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BT_Text extends BlockTypesetter implements AdjustableBT {
    protected final int TOTAL_WIDTH;
    protected final int TOTAL_HEIGHT;
    protected final int INDENTATION_LEFT;
    protected final int INDENTATION_RIGHT;
    protected final char INDENTATION_CHAR;
    protected final int LINE_SPACING;

    private int availSpace;
    private int cacheIndex;
    private boolean areYouTheLastOne;
    private final Typography typography;
    private static final boolean I_BELIEVED_THAT_I_AM_NOT_THE_LAST_ONE = false;
    protected BT_Text(SupportedBT_Position position, Lexicon config) {
        super(position);
        areYouTheLastOne = I_BELIEVED_THAT_I_AM_NOT_THE_LAST_ONE;
        typography = new zh_CN_Typography(Frame.AWT_RULER);
        String totalWidthStr       = config.getFirst("totalWidth"      );
        String totalHeightStr      = config.getFirst("totalHeight"     );
        String indentationLeftStr  = config.getFirst("indentationLeft" );
        String indentationRightStr = config.getFirst("indentationRight");
        String indentationCharStr  = config.getFirst("indentationChar" );
        String lineSpacingStr      = config.getFirst("lineSpacing"     );
        TOTAL_WIDTH       =        Integer.parseInt(totalWidthStr      );
        TOTAL_HEIGHT      =        Integer.parseInt(totalHeightStr     );
        INDENTATION_LEFT  =        Integer.parseInt(indentationLeftStr );
        INDENTATION_RIGHT =        Integer.parseInt(indentationRightStr);
        INDENTATION_CHAR  = (char) Integer.parseInt(indentationCharStr );
        LINE_SPACING      =        Integer.parseInt(lineSpacingStr     );
    }

    @Override
    protected int setType(Message message, final int posLimit) {
        if (position == SupportedBT_Position.UP || position == SupportedBT_Position.DOWN) {
            List<List<String>> paragraphs = new ArrayList<>();
            String indentationLeftStr = Frame.AWT_RULER.repeatW(INDENTATION_CHAR, INDENTATION_LEFT);
            String indentationRightStr = Frame.AWT_RULER.repeatW(INDENTATION_CHAR, INDENTATION_RIGHT);
            int widthAvail = posLimit - INDENTATION_LEFT - INDENTATION_RIGHT;
            for (String s : message.texts) {
                paragraphs.add(typography.lineBreak(s, widthAvail));
            }
            if (!areYouTheLastOne) {
                throw new RuntimeException("Text is not the last is still developing");
            } else {
                
            }
        } else {
            throw new RuntimeException("Text LEFT and RIGHT is still developing.");
        }
        return availSpace;
    }

    @Override
    protected String getCache() {
        return cache[cacheIndex++];
    }

    @Override
    public void tellBTThisFactThatItIsTheLastOne() {
        areYouTheLastOne = true;
    }

    @Override
    public void tellAvailSpace(int availSpace) {
        this.availSpace = availSpace;
    }

    @Override
    protected void nextPage() {
        cacheIndex = 0;
    }
}

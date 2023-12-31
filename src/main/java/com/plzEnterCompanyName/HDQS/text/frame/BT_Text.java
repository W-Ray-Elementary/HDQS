package com.plzEnterCompanyName.HDQS.text.frame;

import com.plzEnterCompanyName.HDQS.io.smartIO2.Message;
import com.plzEnterCompanyName.HDQS.text.Typography;
import com.plzEnterCompanyName.HDQS.text.zh_CN_Typography;
import com.plzEnterCompanyName.HDQS.util.Lexicon;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BT_Text extends BlockTypesetter implements AdjustableBT {
    protected final int TOTAL_WIDTH;
    protected final int TOTAL_HEIGHT;
    protected final int INDENTATION_LEFT;
    protected final int INDENTATION_RIGHT;
    protected final char INDENTATION_CHAR;
    protected final int WIDOW_ORPHAN_CONTROL;

    private int availSpace;
    private int cacheIndex;
    private boolean areYouTheLastOne;
    private final Typography typography;
    private static final boolean I_BELIEVED_THAT_I_AM_NOT_THE_LAST_ONE = false;
    protected BT_Text(SupportedBT_Position position, Lexicon config) {
        super(position);
        areYouTheLastOne = I_BELIEVED_THAT_I_AM_NOT_THE_LAST_ONE;
        typography = new zh_CN_Typography(Frame.AWT_RULER);
        String totalWidthStr         = config.getFirst("totalWidth"        );
        String totalHeightStr        = config.getFirst("totalHeight"       );
        String indentationLeftStr    = config.getFirst("indentationLeft"   );
        String indentationRightStr   = config.getFirst("indentationRight"  );
        String indentationCharStr    = config.getFirst("indentationChar"   );
        String widowOrphanControlStr = config.getFirst("widowOrphanControl");
        TOTAL_WIDTH          =        Integer.parseInt(totalWidthStr      );
        TOTAL_HEIGHT         =        Integer.parseInt(totalHeightStr     );
        INDENTATION_LEFT     =        Integer.parseInt(indentationLeftStr );
        INDENTATION_RIGHT    =        Integer.parseInt(indentationRightStr);
        INDENTATION_CHAR     = (char) Integer.parseInt(indentationCharStr );
        WIDOW_ORPHAN_CONTROL =        Integer.parseInt(widowOrphanControlStr);
    }

    @Override
    protected int setType(Message message, final int posLimit) {
        int blockWidth;
        int blockHeight;
        if (position == SupportedBT_Position.UP || position == SupportedBT_Position.DOWN) {
            blockWidth = posLimit;
            blockHeight = areYouTheLastOne ? availSpace : TOTAL_HEIGHT;
        } else {
            blockHeight = posLimit;
            blockWidth = areYouTheLastOne ? availSpace : TOTAL_WIDTH;
        }
        List<List<String>> paragraphs = new ArrayList<>();
        String indentationLeftStr = Frame.AWT_RULER.repeatW(INDENTATION_CHAR, INDENTATION_LEFT);
        String indentationRightStr = Frame.AWT_RULER.repeatW(INDENTATION_CHAR, INDENTATION_RIGHT);
        int widthAvail = blockWidth - INDENTATION_LEFT - INDENTATION_RIGHT;
        String blankLineStr = Frame.AWT_RULER.repeatW(' ', widthAvail);
        for (String s : message.texts) {
            paragraphs.add(Frame.TYPO.lineBreak(s, widthAvail));
        }
        int linesAvail = blockHeight;
        List<String> preCache = new ArrayList<>();
        for (int i = 0; i < paragraphs.size(); i++) {
            if (linesAvail == blockHeight) {
                for (int j = 0; j < paragraphs.get(i).size(); j++) {
                    if (linesAvail > 0) {
                        preCache.add(indentationLeftStr + paragraphs.get(i).get(j) + indentationRightStr);
                        linesAvail--;
                        continue;
                    }
                    linesAvail = blockHeight;
                }
            } else {
                if (linesAvail > 0) {
                    preCache.add(indentationLeftStr + blankLineStr + indentationRightStr);
                    linesAvail--;
                }
                for (int j = 0; j < paragraphs.get(i).size(); j++) {
                    if (linesAvail > 0) {
                        preCache.add(indentationLeftStr + paragraphs.get(i).get(j) + indentationRightStr);
                        linesAvail--;
                        continue;
                    }
                    linesAvail = blockHeight;
                }
            }
        }
        for (int restLine = blockHeight - (preCache.size() % blockHeight); restLine > 0; restLine--) {
            preCache.add(indentationLeftStr + blankLineStr + indentationRightStr);
        }
        cache = new String[preCache.size()];
        for (int i = 0; i < preCache.size(); i++)
            cache[i] = preCache.get(i);
        return (position == SupportedBT_Position.UP || position == SupportedBT_Position.DOWN) ?
                blockHeight : blockWidth;
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

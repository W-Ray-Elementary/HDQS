package com.plzEnterCompanyName.HDQS.text.frame;

import com.plzEnterCompanyName.HDQS.io.smartIO2.Message;
import com.plzEnterCompanyName.HDQS.util.Configuration;

import java.util.ArrayList;
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
    private static final boolean I_BELIEVED_THAT_I_AM_NOT_THE_LAST_ONE = false;

    protected BT_Text(SupportedBT_Position position, Configuration config) {
        super(position);
        areYouTheLastOne = I_BELIEVED_THAT_I_AM_NOT_THE_LAST_ONE;
        TOTAL_WIDTH = config.getInt("totalWidth");
        TOTAL_HEIGHT = config.getInt("totalHeight");
        INDENTATION_LEFT = config.getInt("indentationLeft");
        INDENTATION_RIGHT = config.getInt("indentationRight");
        INDENTATION_CHAR = (char) config.getInt("indentationChar");
        WIDOW_ORPHAN_CONTROL = config.getInt("widowOrphanControl");
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
        String indentationLeftStr = Frame.RULER.repeatW(INDENTATION_CHAR, INDENTATION_LEFT);
        String indentationRightStr = Frame.RULER.repeatW(INDENTATION_CHAR, INDENTATION_RIGHT);
        int widthAvail = blockWidth - INDENTATION_LEFT - INDENTATION_RIGHT;
        String blankLineStr = Frame.RULER.repeatW(' ', widthAvail);
        for (String s : message.texts) {
            paragraphs.add(Frame.TYPO.lineBreak(s, widthAvail));
        }
        int linesAvail = blockHeight;
        List<String> preCache = new ArrayList<>();
        for (List<String> paragraph : paragraphs) {
            if (linesAvail == blockHeight) {
                for (String s : paragraph) {
                    if (linesAvail > 0) {
                        preCache.add(indentationLeftStr + s + indentationRightStr);
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
                for (String s : paragraph) {
                    if (linesAvail > 0) {
                        preCache.add(indentationLeftStr + s + indentationRightStr);
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
        return (position == SupportedBT_Position.UP || position == SupportedBT_Position.DOWN) ? blockHeight
                : blockWidth;
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

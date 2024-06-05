package com.plzEnterCompanyName.HDQS.text.frame;

import com.plzEnterCompanyName.HDQS.io.smartIO2.Message;
import com.plzEnterCompanyName.HDQS.util.Configuration;

import java.util.ArrayList;
import java.util.List;

public class BT_Operation extends BlockTypesetter {

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
     * 单个底部提示之间的间距数
     */
    protected final int HORIZONTAL_SPACING;

    private int cacheIndex;

    public BT_Operation(SupportedBT_Position position, int indentation, char indentationChar, int horizontalSpacing) {
        super(position);
        INDENTATION = indentation;
        INDENTATION_CHAR = indentationChar;
        HORIZONTAL_SPACING = horizontalSpacing;
    }

    protected BT_Operation(SupportedBT_Position position, Configuration config) {
        super(position);
        if (position == SupportedBT_Position.LEFT || position == SupportedBT_Position.RIGHT)
            throw new UnsupportedOperationException("BT_Operation can not execute vertical layout.");
        INDENTATION = config.getInt("indentation");
        INDENTATION_CHAR = (char) config.getInt("indentationChar");
        HORIZONTAL_SPACING = config.getInt("horizontalSpacing");
    }

    @Override
    protected int setType(Message message, final int posLimit) {
        List<StringBuilder> lines = new ArrayList<>();
        List<String> operationStrings = message.operations;
        String indentationStr = String.valueOf(INDENTATION_CHAR).repeat(INDENTATION);
        String spacing = String.valueOf(' ').repeat(HORIZONTAL_SPACING);
        int lineCount = 0;
        boolean isLineBegin = true;
        int lineSpaceAvail = posLimit;
        if (position == SupportedBT_Position.UP || position == SupportedBT_Position.DOWN) {
            if (operationStrings.isEmpty())
                return 0;
            for (String str : operationStrings) {
                if (isLineBegin) {
                    lines.add(new StringBuilder());
                    lines.get(lineCount).append(indentationStr);
                    lines.get(lineCount).append(str);
                    lineSpaceAvail -= Layout.RULER.measureWidth(indentationStr);
                    lineSpaceAvail -= Layout.RULER.measureWidth(str);
                    isLineBegin = false;
                    continue;
                }
                int neededSpace = Layout.RULER.measureWidth(spacing) + Layout.RULER.measureWidth(str);
                if (neededSpace > lineSpaceAvail) {
                    lines.get(lineCount).append(String.valueOf(' ').repeat(lineSpaceAvail));
                    lineCount++;
                    lineSpaceAvail = posLimit;
                    lines.add(new StringBuilder());
                    lines.get(lineCount).append(indentationStr);
                    lines.get(lineCount).append(str);
                    lineSpaceAvail -= Layout.RULER.measureWidth(indentationStr);
                    lineSpaceAvail -= Layout.RULER.measureWidth(str);
                    continue;
                }
                lineSpaceAvail -= neededSpace;
                lines.get(lineCount).append(spacing);
                lines.get(lineCount).append(str);
            }
            lines.get(lineCount).append(String.valueOf(' ').repeat(lineSpaceAvail));
            cache = new String[lines.size()];
            for (int i = 0; i < lines.size(); i++)
                cache[i] = lines.get(i).toString();
            return lines.size();
        } else {
            throw new RuntimeException("Operation LEFT and RIGHT is still developing!");
        }
    }

    @Override
    protected String getCache() {
        return cache[cacheIndex++];
    }

    @Override
    protected void reset() {
        cacheIndex = 0;
    }
}

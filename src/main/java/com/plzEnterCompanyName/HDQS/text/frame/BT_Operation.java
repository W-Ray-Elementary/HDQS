package com.plzEnterCompanyName.HDQS.text.frame;

import com.plzEnterCompanyName.HDQS.io.smartIO2.Message;
import com.plzEnterCompanyName.HDQS.io.smartIO2.Operation;
import com.plzEnterCompanyName.HDQS.util.Lexicon;

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

    protected BT_Operation(SupportedBT_Position position, Lexicon config) {
        super(position);
        if (position == SupportedBT_Position.LEFT || position == SupportedBT_Position.RIGHT)
            throw new UnsupportedOperationException("BT_Operation can not execute vertical layout.");
        String indentationStr       = config.getFirst("indentation"      );
        String indentationCharStr   = config.getFirst("indentationChar"  );
        String horizontalSpacingStr = config.getFirst("horizontalSpacing");
        INDENTATION        =        Integer.parseInt(indentationStr      );
        INDENTATION_CHAR   = (char) Integer.parseInt(indentationCharStr  );
        HORIZONTAL_SPACING =        Integer.parseInt(horizontalSpacingStr);
    }

    @Override
    protected int setType(Message message, final int posLimit) {
        List<StringBuilder> lines = new ArrayList<>();
        List<String> operationStrings = new ArrayList<>(message.operations.size());
        for (Operation operation : message.operations)
            operationStrings.add(operation.getName());
        String indentationStr = String.valueOf(INDENTATION_CHAR).repeat(INDENTATION);
        String spacing = String.valueOf(' ').repeat(HORIZONTAL_SPACING);
        int lineCount = 0;
        boolean isLineBegin = true;
        int lineSpaceAvail = posLimit;
        if (position == SupportedBT_Position.UP || position == SupportedBT_Position.DOWN) {
            if (operationStrings.isEmpty()) return 0;
            for (String str : operationStrings) {
                if (isLineBegin) {
                    lines.add(new StringBuilder());
                    lines.get(lineCount).append(indentationStr);
                    lines.get(lineCount).append(str);
                    lineSpaceAvail -= Frame.RULER.measureWidth(indentationStr);
                    lineSpaceAvail -= Frame.RULER.measureWidth(str);
                    isLineBegin = false;
                    continue;
                }
                int neededSpace = Frame.RULER.measureWidth(spacing) + Frame.RULER.measureWidth(str);
                if (neededSpace > lineSpaceAvail) {
                    lines.get(lineCount).append(String.valueOf(' ').repeat(lineSpaceAvail));
                    lineCount++;
                    lineSpaceAvail = posLimit;
                    lines.add(new StringBuilder());
                    lines.get(lineCount).append(indentationStr);
                    lines.get(lineCount).append(str);
                    lineSpaceAvail -= Frame.RULER.measureWidth(indentationStr);
                    lineSpaceAvail -= Frame.RULER.measureWidth(str);
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
    protected void nextPage() {
        cacheIndex = 0;
    }
}

package com.plzEnterCompanyName.HDQS.util.lexicon;

import org.apache.commons.text.StringEscapeUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.plzEnterCompanyName.HDQS.util.lexicon.TokenType.*;

public class Token2Lexicon {
    private final List<Token> source;
    private final List<Lexicon> lexicons = new ArrayList<>();
    private int current = 0;
    private File sourceFile;

    Token2Lexicon(List<Token> source) {
        this.source = source;
    }

    public Token2Lexicon(List<Token> source, File sourceFile) {
        this.source = source;
        this.sourceFile = sourceFile;
    }

    public List<Lexicon> convert() {
        while (!isAtEnd()) {
            convert0();
        }
        return lexicons;
    }

    private void convert0() {
        Token t = advance();
        switch (t.type) {
            case WHITESPACE, NEWLINE, EOF:
                break;
            case STRING:
                while (!isAtEnd()) {
                    Token lexiconBegin = advance();
                    switch (lexiconBegin.type) {
                        case EQUAL:
                            System.err.println(errMsg(lexiconBegin, "Key-Value should be surrounded by a lexicon", 0, 0));
                            skipLine();
                            break;
                        case LEFT_BRACE:
                            lexicons.add(readLexicon(t));
                            return;
                        case WHITESPACE, NEWLINE, EOF:
                            break;
                        case STRING:
                            System.err.println(errMsg(lexiconBegin, "cannot resolve symbol " + lexiconBegin.value(), 0, lexiconBegin.value().length()));
                            break;
                        case RIGHT_BRACE:
                            System.err.println(errMsg(lexiconBegin, "cannot resolve symbol '}'" , 0, 1));
                            break;
                    }
                }
                break;
            case EQUAL:
                System.err.println(errMsg(t, "cannot resolve symbol '='", 0, 1));
                break;
            case LEFT_BRACE:
                System.err.println(errMsg(t, "cannot resolve symbol '{'", 0, 1));
                break;
            case RIGHT_BRACE:
                System.err.println(errMsg(t, "cannot resolve symbol '}'", 0, 1));
                break;
        }
    }

    private void skipLine() {
        int cLine = source.get(current - 1).line;
        for (; !isAtEnd(); advance()) {
            if (cLine != source.get(current).line) break;
        }
    }

    private Lexicon readLexicon(Token head) {
        Lexicon returnVal = new Lexicon(head.value());
        advance(); // skip left brace
        while (!isAtEnd()) {
            Token subHead = advance();
            switch (subHead.type) {
                case EQUAL:
                    System.err.println(errMsg(subHead, "missing name", 0, 1));
                    break;
                case LEFT_BRACE:
                    System.err.println(errMsg(subHead, "missing name", 0, 1));
                    readLexicon(head);
                    break;
                case RIGHT_BRACE:
                    return returnVal;
                case WHITESPACE, NEWLINE:
                    break;
                case STRING:
                    boolean canContinue = true;
                    while (!isAtEnd() && canContinue) {
                        Token criterion = advance();
                        switch (criterion.type) {
                            case EOF:
                                System.err.println(errMsg(criterion, "unexpected EOF", criterion.value().length(), 1));
                                return returnVal;
                            case WHITESPACE, NEWLINE:
                                break;
                            case RIGHT_BRACE:
                                System.err.println(errMsg(subHead, "RIGHT_BRACE, cannot find an '=' or '{'", 0, subHead.value().length()));
                                return returnVal;
                            case STRING:
                                System.err.println(errMsg(criterion, "multiple String as a name", 0, criterion.value().length()));
                                break;
                            case LEFT_BRACE:
                                returnVal.add(readLexicon(subHead));
                                canContinue = false;
                                break;
                            case EQUAL:
                                Token vToken = advance();
                                StringBuilder sb = new StringBuilder();
                                while (vToken.type != NEWLINE) {
                                    sb.append(vToken.value());
                                    vToken = advance();
                                }
                                String unescaped = sb.toString().strip();
                                String escaped = StringEscapeUtils.unescapeJava(unescaped);
                                returnVal.add(subHead.value(), escaped);
                                canContinue = false;
                                break;
                        }
                    }
                    break;
                case EOF:
                    System.err.println(errMsg(subHead, "expect '}'", 0, 0));
                    return returnVal;
            }
        }
        System.err.println(errMsg(source.get(current-1), "expect '}'", source.get(current-1).value().length(), 1));
        return returnVal;
    }

    private Token peek() {
        if (isAtEnd()) return source.get(source.size() - 1);
        return source.get(current);
    }

    private Token advance() {
        return source.get(current++);
    }

    private boolean isAtEnd() {
        return current >= source.size();
    }
    /*
     * length: 0 代表整行。
     * */

    private String errMsg(Token t, String msg, int pos, int length) {
        StringBuilder sb = new StringBuilder();
        sb.append("Error: ");
        sb.append(msg);
        sb.append(".\n");
        if (sourceFile != null) {
            sb.append("In \"");
            sb.append(sourceFile.getAbsolutePath());
            sb.append("\"\n");
        }
        sb.append('\n');
        String line = String.valueOf(t.line);
        sb.append(" ".repeat(Math.max(0, 6 - line.length())));
        sb.append(line);
        sb.append(" | ");
        sb.append(getLineStr(t));
        sb.append('\n');
        sb.append(pointOut(t, pos, length));
        return sb.toString();
    }

    private String pointOut(Token t, int pos, int length) {
        List<Token> currentLine = getLine(t);
        String cLString = List2String(currentLine);
        StringBuilder sb = new StringBuilder();
        sb.append("         ");
        if (length == 0) {
            sb.append("^".repeat(cLString.length()));
        } else {
            int tokenPos = currentLine.indexOf(t);
            int begin = 0;
            for (int i = 0; i < tokenPos; i++) {
                begin += currentLine.get(i).value().length();
            }
            sb.append(" ".repeat(begin + pos));
            sb.append("^".repeat(length));
        }
        return sb.toString();
    }

    private String getLineStr(Token t) {
        List<Token> currentLine = getLine(t);
        return List2String(currentLine);
    }

    private String List2String(List<Token> tokens) {
        if (tokens.get(0).type == WHITESPACE) {
            tokens.remove(0);
        }
        if (tokens.get(tokens.size() - 1).type == NEWLINE) {
            tokens.remove(tokens.size() - 1);
        }
        StringBuilder sb = new StringBuilder();
        for (Token ct : tokens) {
            sb.append(ct.value());
        }
        return sb.toString();
    }

    private List<Token> getLine(Token t) {
        int line = t.line;
        List<Token> currentLine = new ArrayList<>();
        for (Token ft : source) {
            if (ft.line == line) {
                currentLine.add(ft);
            }
            if (ft.line > line) {
                break;
            }
        }
        return currentLine;
    }
}

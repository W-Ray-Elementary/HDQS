package com.plzEnterCompanyName.HDQS.util.lexicon;

import static com.plzEnterCompanyName.HDQS.util.lexicon.TokenType.*;

import java.util.ArrayList;
import java.util.List;

public class LScanner {
    private final String source;
    private final List<Token> tokens = new ArrayList<>();
    private int start = 0;
    private int current = 0;
    private int line = 1;

    LScanner(String source) {
        this.source = source;
    }

    List<Token> scanTokens() {
        while (!isAtEnd()) {
            // We are at the beginning of the next lexeme.
            start = current;
            scanToken();
        }

        tokens.add(new Token(EOF, "", null, line));
        return tokens;
    }

    private void scanToken() {
        char c = advance();
        switch (c) {
            case '{':
                addToken(LEFT_BRACE);
                break;
            case '}':
                addToken(RIGHT_BRACE);
                break;
            case '=':
                addToken(EQUAL);
                break;
            case '\n':
                addToken(NEWLINE);
                line++;
                break;
            case ' ', '\r', '\t':
                whitespace();
                break;
            case '/':
                if (match('/')) {
                    // A comment goes until the end of the line.
                    while (peek() != '\n' && !isAtEnd())
                        advance();
                }
                break;
            default:
                string();
                break;
        }
    }

    private void whitespace() {
        while ((peek() == ' ' || peek() == '\r' || peek() == '\t') && !isAtEnd()) {
            if (peek() == '{')
                break;
            if (peek() == '}')
                break;
            if (peek() == '=')
                break;
            if (peek() == '\n')
                break;
            if (peek() == '/') {
                advance();
                if (match('/')) {
                    // A comment goes until the end of the line.
                    while (peek() != '\n' && !isAtEnd())
                        advance();
                }
                break;
            }
            advance();
        }

        String value = source.substring(start, current);
        addToken(WHITESPACE, value);

        if (peek() == '{') {
            addToken(LEFT_BRACE);
            advance();
        }
        if (peek() == '}') {
            addToken(RIGHT_BRACE);
            advance();
        }
        if (peek() == '=') {
            addToken(EQUAL);
            advance();
        }
        if (peek() == '\n') {
            addToken(NEWLINE);
            line++;
            advance();
        }
    }

    private void string() {
        while (peek() != ' ' && peek() != '\r' && peek() != '\t' && !isAtEnd()) {
            if (peek() == '{')
                break;
            if (peek() == '}')
                break;
            if (peek() == '=')
                break;
            if (peek() == '\n')
                break;
            if (peek() == '/') {
                advance();
                if (match('/')) {
                    // A comment goes until the end of the line.
                    while (peek() != '\n' && !isAtEnd())
                        advance();
                }
                break;
            }
            advance();
        }

        String value = source.substring(start, current);
        addToken(STRING, value);

        if (peek() == '{') {
            addToken(LEFT_BRACE);
            advance();
        }
        if (peek() == '}') {
            addToken(RIGHT_BRACE);
            advance();
        }
        if (peek() == '=') {
            addToken(EQUAL);
            advance();
        }
        if (peek() == '\n') {
            addToken(NEWLINE);
            line++;
            advance();
        }
    }

    private boolean match(char expected) {
        if (isAtEnd())
            return false;
        if (source.charAt(current) != expected)
            return false;

        current++;
        return true;
    }

    private char peek() {
        if (isAtEnd())
            return '\0';
        return source.charAt(current);
    }

    private boolean isAtEnd() {
        return current >= source.length();
    }

    private char advance() {
        return source.charAt(current++);
    }

    private void addToken(TokenType type) {
        addToken(type, null);
    }

    private void addToken(TokenType type, Object literal) {
        String text = source.substring(start, current);
        tokens.add(new Token(type, text, literal, line));
    }
}

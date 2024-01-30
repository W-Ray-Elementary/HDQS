package com.plzEnterCompanyName.HDQS.util.lexicon;

public class Token {
    final TokenType type;
    final String lexeme;
    final Object literal;
    final int line;

    Token(TokenType type, String lexeme, Object literal, int line) {
        this.type = type;
        this.lexeme = lexeme;
        this.literal = literal;
        this.line = line;
    }

    public String toString() {
        return type + " " + lexeme + " " + literal;
    }

    public String value() {
        return switch (type) {
            case LEFT_BRACE -> "{";
            case RIGHT_BRACE -> "}";
            case EQUAL -> "=";
            case NEWLINE -> "\n";
            case WHITESPACE, STRING -> lexeme;
            case EOF -> "";
        };
    }
}

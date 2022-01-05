/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.tarleton.welborn.duckcodeinterpreter.lexer;

/**
 *
 * @author Ethan Welborn - ethan.welborn@go.tarleton.edu
 */
public class Token {
    private final TokenType tokenType;
    private final int line;
    private final int column;
    private final String value;

    public Token(TokenType tokenType, int line, int column, String value) {
        this.tokenType = tokenType;
        this.line = line;
        this.column = column;
        this.value = value;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Token{" + "tokenType=" + tokenType + ", line=" + line + ", column=" + column + ", value=" + value + '}';
    }
    
}

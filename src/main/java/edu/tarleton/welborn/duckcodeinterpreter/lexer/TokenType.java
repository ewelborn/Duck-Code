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
public enum TokenType {
    NEW_LINE,
    SPACE,
    LEFT_PARENTHESES,
    RIGHT_PARENTHESES,
    COLON,
    STRING,
    IDENT,
    COMMA,
    PLUS,
    MINUS,
    MULTIPLY,
    DIVIDE,
    INTEGER,
    EQUALS,
    
    IF,
    IS,
    ELSE,
    OR,
    AND,
    NOT,
    LESS,
    GREATER,
    THAN,
    VAR,
    REPEAT,
    TIMES,
    BREAK,
    DEFINE,
    TRUE,
    FALSE,
}

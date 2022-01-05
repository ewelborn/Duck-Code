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
public class LexerException extends Exception {

    /**
     * Creates a new instance of <code>LexerException</code> without detail
     * message.
     */
    public LexerException() {
    }

    /**
     * Constructs an instance of <code>LexerException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public LexerException(String msg) {
        super(msg);
    }
}

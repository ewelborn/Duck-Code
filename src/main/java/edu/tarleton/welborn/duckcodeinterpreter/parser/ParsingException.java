/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.tarleton.welborn.duckcodeinterpreter.parser;

/**
 *
 * @author Ethan Welborn - ethan.welborn@go.tarleton.edu
 */
public class ParsingException extends Exception {

    /**
     * Creates a new instance of <code>ParsingException</code> without detail
     * message.
     */
    public ParsingException() {
    }

    /**
     * Constructs an instance of <code>ParsingException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ParsingException(int line, int column, String msg) {
        super("Parsing exception at line "+line+", column "+column+" : "+msg);
    }
}

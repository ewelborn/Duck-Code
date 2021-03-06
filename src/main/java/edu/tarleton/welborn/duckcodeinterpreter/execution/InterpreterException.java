/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.tarleton.welborn.duckcodeinterpreter.execution;

/**
 *
 * @author Ethan Welborn - ethan.welborn@go.tarleton.edu
 */
public class InterpreterException extends Exception {

    /**
     * Creates a new instance of <code>InterpreterException</code> without
     * detail message.
     */
    public InterpreterException() {
    }

    /**
     * Constructs an instance of <code>InterpreterException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public InterpreterException(String msg) {
        super(msg);
    }
}

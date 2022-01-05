/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.tarleton.welborn.duckcodeinterpreter.execution;

import edu.tarleton.welborn.duckcodeinterpreter.parser.FunctionDefinitionTree;
import edu.tarleton.welborn.duckcodeinterpreter.parser.Variable;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 *
 * @author Ethan Welborn - ethan.welborn@go.tarleton.edu
 */
public class InterpreterAPI {

    private final boolean debugMode;
    private final Deque<Pair<String,Variable>> variables = new ArrayDeque<>();
    private final Deque<Pair<String,JavaFunction>> javaFunctions = new ArrayDeque<>();
    private final Deque<Pair<String,FunctionDefinitionTree>> userFunctions = new ArrayDeque<>();
    private boolean breakFlag = false; // Turn this on when a break statement is reached so that the parent loop knows to stop looping
    private boolean returnFlag = false; // Turn this on when a return statement is reached so that the parent function knows when to stop execution

    public InterpreterAPI(boolean debugMode) {
        this.debugMode = debugMode;
    }
    
    public InterpreterAPI() {
        this.debugMode = false;
    }
    
    public Deque<Pair<String,Variable>> getVariables() {
        return variables;
    }

    public Deque<Pair<String, JavaFunction>> getJavaFunctions() {
        return javaFunctions;
    }

    public Deque<Pair<String, FunctionDefinitionTree>> getUserFunctions() {
        return userFunctions;
    }
    
    public JavaFunction getJavaFunction(String functionName) {
        for(Pair<String,JavaFunction> pair : javaFunctions) {
            if(pair.getKey().equals(functionName)) {
                return pair.getValue();
            }
        }
        
        return null;
    }

    public boolean getBreakFlag() {
        return breakFlag;
    }

    public void setBreakFlag(boolean breakFlag) {
        this.breakFlag = breakFlag;
    }

    public boolean getReturnFlag() {
        return returnFlag;
    }

    public void setReturnFlag(boolean returnFlag) {
        this.returnFlag = returnFlag;
    }
    
    public void debugPrint(String msg) {
        if(debugMode) {
            System.out.println("Interpreter Debug -> "+msg);
        }
    }
}

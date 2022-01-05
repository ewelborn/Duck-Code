/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.tarleton.welborn.duckcodeinterpreter.execution;

import edu.tarleton.welborn.duckcodeinterpreter.parser.StatementBlockTree;
import edu.tarleton.welborn.duckcodeinterpreter.parser.Variable;
import java.util.Stack;

/**
 *
 * @author Ethan Welborn - ethan.welborn@go.tarleton.edu
 */
public class Interpreter {
    private final StatementBlockTree statementBlock;
    private final InterpreterAPI API;

    public Interpreter(StatementBlockTree statementBlock, boolean debugMode) {
        this.statementBlock = statementBlock;
        API = new InterpreterAPI(debugMode);
    }
    
    public Interpreter(StatementBlockTree statementBlock) {
        this.statementBlock = statementBlock;
        API = new InterpreterAPI(false);
    }

    public StatementBlockTree getStatementBlock() {
        return statementBlock;
    }

    public InterpreterAPI getAPI() {
        return API;
    }
    
    public void execute() throws InterpreterException, InterruptedException {
        statementBlock.executeAllStatements(API);
    }
}

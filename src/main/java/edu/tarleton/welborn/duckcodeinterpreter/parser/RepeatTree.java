/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.tarleton.welborn.duckcodeinterpreter.parser;

import edu.tarleton.welborn.duckcodeinterpreter.execution.InterpreterAPI;
import edu.tarleton.welborn.duckcodeinterpreter.execution.InterpreterException;

/**
 *
 * @author Ethan Welborn - ethan.welborn@go.tarleton.edu
 */
public class RepeatTree extends StatementTree {

    private ExpressionTree iterations;
    private StatementBlockTree statementBlock;

    public RepeatTree(ExpressionTree iterations, StatementBlockTree statementBlock) {
        this.iterations = iterations;
        this.statementBlock = statementBlock;
    }

    public RepeatTree(StatementBlockTree statementBlock) {
        this.statementBlock = statementBlock;
    }

    public ExpressionTree getIterations() {
        return iterations;
    }

    public void setIterations(ExpressionTree iterations) {
        this.iterations = iterations;
    }

    public StatementBlockTree getStatementBlock() {
        return statementBlock;
    }

    public void setStatementBlock(StatementBlockTree statementBlock) {
        this.statementBlock = statementBlock;
    }
    
    @Override
    public Variable execute(InterpreterAPI API) throws InterpreterException, InterruptedException {
        if(iterations != null) {
            Variable var = iterations.getResult(API);
            if(var.getVariableType() != VariableType.INT) {
                throw new InterpreterException("Loop expected an int for loop number, got "+var.getVariableType());
            }
            
            for(int i=0;i<var.getIntValue();i++) {
                statementBlock.executeAllStatements(API);
                
                if(API.getBreakFlag()) {
                    API.setBreakFlag(false);
                    break;
                }
            }
        } else {
            while(true) {
                statementBlock.executeAllStatements(API);
                
                if(API.getBreakFlag()) {
                    API.setBreakFlag(false);
                    break;
                }
            }
        }
        
        return null;
    }

    @Override
    public void printTree(int currentLayer) {
        super.printString(this.getClass().getSimpleName() + ":",currentLayer);
        
        currentLayer = currentLayer + 1;
        
        if(iterations != null) {
            iterations.printTree(currentLayer);
        }
        
        statementBlock.printTree(currentLayer);
    }
    
}

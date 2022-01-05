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
public class IfTree extends StatementTree {

    ExpressionTree condition;
    StatementBlockTree statementBlock;
    
    // The following are mutually exclusive
    IfTree elseIfTree;
    StatementBlockTree elseStatementBlock;

    public IfTree(ExpressionTree condition, StatementBlockTree statementBlock) {
        this.condition = condition;
        this.statementBlock = statementBlock;
    }

    public IfTree(ExpressionTree condition, StatementBlockTree statementBlock, IfTree elseIfTree) {
        this.condition = condition;
        this.statementBlock = statementBlock;
        this.elseIfTree = elseIfTree;
    }

    public IfTree(ExpressionTree condition, StatementBlockTree statementBlock, StatementBlockTree elseStatementBlock) {
        this.condition = condition;
        this.statementBlock = statementBlock;
        this.elseStatementBlock = elseStatementBlock;
    }

    public IfTree getElseIfTree() {
        return elseIfTree;
    }

    public void setElseIfTree(IfTree elseIfTree) {
        this.elseIfTree = elseIfTree;
    }

    public StatementBlockTree getElseStatementBlock() {
        return elseStatementBlock;
    }

    public void setElseStatementBlock(StatementBlockTree elseStatementBlock) {
        this.elseStatementBlock = elseStatementBlock;
    }

    public ExpressionTree getCondition() {
        return condition;
    }

    public void setCondition(ExpressionTree condition) {
        this.condition = condition;
    }

    public StatementBlockTree getStatementBlock() {
        return statementBlock;
    }

    public void setStatementBlock(StatementBlockTree statementBlock) {
        this.statementBlock = statementBlock;
    }
    
    @Override
    public void printTree(int currentLayer) {
        super.printString(this.getClass().getSimpleName() + ":",currentLayer);
        
        currentLayer = currentLayer + 1;
        condition.printTree(currentLayer);
        statementBlock.printTree(currentLayer);
        
        if(elseIfTree != null) {
            super.printString("Else If Tree below ",currentLayer);
            elseIfTree.printTree(currentLayer);
        } else if(elseStatementBlock != null) {
            super.printString("Else Statement Block below",currentLayer);
            elseStatementBlock.printTree(currentLayer);
        }
    }

    @Override
    public Variable execute(InterpreterAPI API) throws InterpreterException, InterruptedException {
        Variable var = condition.getResult(API);
        if(var.getVariableType() == VariableType.BOOLEAN) {
            if(var.getBooleanValue() == true) {
                statementBlock.executeAllStatements(API);
            } else {
                // Ironic, we're using if statements to execute an if statement :P
                
                // If we have an else if, then hand control over to that statement
                // Otherwise, if we have an else, run that statement block
                if(elseIfTree != null) {
                    elseIfTree.execute(API);
                } else if(elseStatementBlock != null) {
                    elseStatementBlock.executeAllStatements(API);
                }
            }
        } else {
            // Freak out
            throw new InterpreterException("If statement only accepts boolean results");
        }
        
        return null;
    }
    
}

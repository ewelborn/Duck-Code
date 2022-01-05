/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.tarleton.welborn.duckcodeinterpreter.parser;

import edu.tarleton.welborn.duckcodeinterpreter.execution.InterpreterAPI;
import edu.tarleton.welborn.duckcodeinterpreter.execution.InterpreterException;
import edu.tarleton.welborn.duckcodeinterpreter.lexer.TokenType;

/**
 *
 * @author Ethan Welborn - ethan.welborn@go.tarleton.edu
 */
public class AddTree extends AST {

    private TokenType operator;
    private TermTree term;
    private AddTree add;

    public AddTree(TokenType operator, TermTree term, AddTree add) {
        this.operator = operator;
        this.term = term;
        this.add = add;
    }

    public TokenType getOperator() {
        return operator;
    }

    public TermTree getTerm() {
        return term;
    }

    public AddTree getAdd() {
        return add;
    }

    @Override
    public void printTree(int currentLayer) {
        super.printString(this.getClass().getSimpleName() + ":",currentLayer);
        
        currentLayer = currentLayer + 1;
        super.printString("Operator: "+operator,currentLayer);
        term.printTree(currentLayer);
        if(add != null) {
            add.printTree(currentLayer);
        }
    }

    public Variable getResult(InterpreterAPI API,Variable left) throws InterpreterException, InterruptedException {
        Variable right = term.getResult(API);
        Variable result = null;
        
        switch(operator) {
            case PLUS:
                if(left.getVariableType() == VariableType.INT && right.getVariableType() == VariableType.INT) {
                    result = new Variable(VariableType.INT,left.getIntValue() + right.getIntValue());
                }
                break;
            case MINUS:
                if(left.getVariableType() == VariableType.INT && right.getVariableType() == VariableType.INT) {
                    result = new Variable(VariableType.INT,left.getIntValue() - right.getIntValue());
                }
                break;
            case OR:
                if(left.getVariableType() == VariableType.BOOLEAN && right.getVariableType() == VariableType.BOOLEAN) {
                    result = new Variable(VariableType.BOOLEAN,left.getBooleanValue() || right.getBooleanValue());
                }
                break;
            default:
                break;
        }
        
        if(add != null) {
            result = add.getResult(API,result);
        }
        
        return result;
    }
    
}

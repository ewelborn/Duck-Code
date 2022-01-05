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
public class MultiplyTree extends AST {

    private TokenType operator;
    private FactorTree factor;
    private MultiplyTree multiply;

    public MultiplyTree(TokenType operator, FactorTree factor, MultiplyTree multiply) {
        this.operator = operator;
        this.factor = factor;
        this.multiply = multiply;
    }

    public TokenType getOperator() {
        return operator;
    }

    public FactorTree getFactor() {
        return factor;
    }

    public MultiplyTree getMultiply() {
        return multiply;
    }

    @Override
    public void printTree(int currentLayer) {
        super.printString(this.getClass().getSimpleName() + ":",currentLayer);
        
        currentLayer = currentLayer + 1;
        super.printString("Operator: "+operator,currentLayer);
        factor.printTree(currentLayer);
        if(multiply != null) {
            multiply.printTree(currentLayer);
        }
    }

    public Variable getResult(InterpreterAPI API, Variable left) throws InterpreterException, InterruptedException {
        Variable right = factor.getResult(API);
        Variable result = null;
        
        switch(operator) {
            case MULTIPLY:
                if(left.getVariableType() == VariableType.INT && right.getVariableType() == VariableType.INT) {
                    result = new Variable(VariableType.INT,left.getIntValue() * right.getIntValue());
                }
                break;
            case DIVIDE:
                if(left.getVariableType() == VariableType.INT && right.getVariableType() == VariableType.INT) {
                    result = new Variable(VariableType.INT,left.getIntValue() / right.getIntValue());
                }
                break;
            case AND:
                if(left.getVariableType() == VariableType.BOOLEAN && right.getVariableType() == VariableType.BOOLEAN) {
                    result = new Variable(VariableType.BOOLEAN,left.getBooleanValue() && right.getBooleanValue());
                }
                break;
            default:
                break;
        }
        
        if(multiply != null) {
            result = multiply.getResult(API, result);
        }
        
        return result;
    }
    
}

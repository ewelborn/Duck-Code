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
public class MoreLogicTree extends AST {

    private TokenType operator;
    private LogicTree logic;
    private MoreLogicTree moreLogic;

    public MoreLogicTree(TokenType operator, LogicTree logic, MoreLogicTree moreLogic) {
        this.operator = operator;
        this.logic = logic;
        this.moreLogic = moreLogic;
    }

    public TokenType getOperator() {
        return operator;
    }

    public void setOperator(TokenType operator) {
        this.operator = operator;
    }

    public LogicTree getLogic() {
        return logic;
    }

    public void setLogic(LogicTree logic) {
        this.logic = logic;
    }

    public MoreLogicTree getMoreLogic() {
        return moreLogic;
    }

    public void setMoreLogic(MoreLogicTree moreLogic) {
        this.moreLogic = moreLogic;
    }

    @Override
    public void printTree(int currentLayer) {
        super.printString(this.getClass().getSimpleName() + ":",currentLayer);
        
        currentLayer = currentLayer + 1;
        super.printString("Operator: "+operator,currentLayer);
        logic.printTree(currentLayer);
        if(moreLogic != null) {
            moreLogic.printTree(currentLayer);
        }
    }

    public Variable getResult(InterpreterAPI API,Variable left) throws InterpreterException, InterruptedException {
        Variable right = logic.getResult(API);
        Variable result = null;
        
        switch(operator) {
            case IS:
                if(left.getVariableType() == right.getVariableType()) {
                    switch(left.getVariableType()) {
                        case INT:
                            return new Variable(VariableType.BOOLEAN,left.getIntValue() == right.getIntValue());
                        case DOUBLE:
                            return new Variable(VariableType.BOOLEAN,left.getDoubleValue() == right.getDoubleValue());
                        case STRING:
                            return new Variable(VariableType.BOOLEAN,left.getStringValue().equals(right.getStringValue()));
                        case BOOLEAN:
                            return new Variable(VariableType.BOOLEAN,left.getBooleanValue() == right.getBooleanValue());
                    }
                }
                break;
            case LESS:
                if(left.getVariableType() == right.getVariableType()) {
                    switch(left.getVariableType()) {
                        case INT:
                            return new Variable(VariableType.BOOLEAN,left.getIntValue() < right.getIntValue());
                        case DOUBLE:
                            return new Variable(VariableType.BOOLEAN,left.getDoubleValue() < right.getDoubleValue());
                        case STRING:
                            throw new InterpreterException("String values cannot be operated on with less than operator");
                        case BOOLEAN:
                            throw new InterpreterException("Boolean values cannot be operated on with less than operator");
                    }
                }
                break;
            case GREATER:
                if(left.getVariableType() == right.getVariableType()) {
                    switch(left.getVariableType()) {
                        case INT:
                            return new Variable(VariableType.BOOLEAN,left.getIntValue() > right.getIntValue());
                        case DOUBLE:
                            return new Variable(VariableType.BOOLEAN,left.getDoubleValue() > right.getDoubleValue());
                        case STRING:
                            throw new InterpreterException("String values cannot be operated on with greater than operator");
                        case BOOLEAN:
                            throw new InterpreterException("Boolean values cannot be operated on with greater than operator");
                    }
                }
                break;
            default:
                break;
        }
        
        if(moreLogic != null) {
            result = moreLogic.getResult(API,result);
        }
        
        return result;
    }

}

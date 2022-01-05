/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.tarleton.welborn.duckcodeinterpreter.parser;

import edu.tarleton.welborn.duckcodeinterpreter.execution.InterpreterAPI;
import edu.tarleton.welborn.duckcodeinterpreter.execution.InterpreterException;
import edu.tarleton.welborn.duckcodeinterpreter.execution.Pair;

/**
 *
 * @author Ethan Welborn - ethan.welborn@go.tarleton.edu
 */
public class ExpTree extends AST {

    private Variable variable;
    private FunctionCallTree functionCall;
    private String variableName;

    public ExpTree(Variable variable) {
        this.variable = variable;
    }

    public ExpTree(FunctionCallTree functionCall) {
        this.functionCall = functionCall;
    }
    
    public ExpTree(String variableName) {
        this.variableName = variableName;
    }

    public FunctionCallTree getFunctionCall() {
        return functionCall;
    }

    public void setFunctionCall(FunctionCallTree functionCall) {
        this.functionCall = functionCall;
    }

    public Variable getVariable() {
        return variable;
    }

    public void setVariable(Variable variable) {
        this.variable = variable;
    }

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }
    
    @Override
    public void printTree(int currentLayer) {
        super.printString(this.getClass().getSimpleName() + ":",currentLayer);
        
        currentLayer = currentLayer + 1;
        if(variable != null) {
            super.printString("Variable: "+variable,currentLayer);
        }
        
        if(functionCall != null) {
            functionCall.printTree(currentLayer);
        }
        
        if(variableName != null) {
            super.printString("Variable name: "+variableName,currentLayer);
        }
    }

    public Variable getResult(InterpreterAPI API) throws InterpreterException, InterruptedException {
        if(variable != null) {
            return variable;
        }
        
        if(functionCall != null) {
            return functionCall.execute(API);
        }
        
        if(variableName != null) {
            for(Pair<String,Variable> pair : API.getVariables()) {
                if(pair.getKey().equals(variableName)) {
                    if(pair.getValue() != null) {
                        return pair.getValue();
                    } else {
                        throw new InterpreterException("Encountered undeclared variable with name "+variableName);
                    }
                }
            }
            
            throw new InterpreterException("Could not find variable with name "+variableName);
        }
        
        return null;
    }

}

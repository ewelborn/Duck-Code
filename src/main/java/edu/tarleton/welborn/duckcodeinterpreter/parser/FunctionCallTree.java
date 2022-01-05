/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.tarleton.welborn.duckcodeinterpreter.parser;

import edu.tarleton.welborn.duckcodeinterpreter.execution.InterpreterAPI;
import edu.tarleton.welborn.duckcodeinterpreter.execution.InterpreterException;
import edu.tarleton.welborn.duckcodeinterpreter.execution.JavaFunction;
import edu.tarleton.welborn.duckcodeinterpreter.execution.Pair;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ethan Welborn - ethan.welborn@go.tarleton.edu
 */
public class FunctionCallTree extends StatementTree {

    private String functionName;
    private List<ExpressionTree> functionArguments;

    public FunctionCallTree(String functionName) {
        this.functionName = functionName;
        this.functionArguments = new ArrayList<>();
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public List<ExpressionTree> getFunctionArguments() {
        return functionArguments;
    }

    public void setFunctionArguments(List<ExpressionTree> functionArguments) {
        this.functionArguments = functionArguments;
    }
    
    @Override
    public void printTree(int currentLayer) {
        super.printString(this.getClass().getSimpleName() + ":",currentLayer);
        
        currentLayer = currentLayer + 1;
        super.printString("Function name: "+functionName,currentLayer);
        
        for(ExpressionTree expression : functionArguments) {
            expression.printTree(currentLayer);
        }
    }

    @Override
    public Variable execute(InterpreterAPI API) throws InterpreterException, InterruptedException {
        // TODO: Use the list of expressions to generate a list of variables
        List<Variable> args = new ArrayList<>();
        
        for(ExpressionTree expression : functionArguments) {
            args.add(expression.getResult(API));
        }
        
        for(Pair<String,FunctionDefinitionTree> userFunction : API.getUserFunctions()) {
            if(userFunction.getKey().equals(functionName)) {
                return userFunction.getValue().call(API,args);
            }
        }
        
        // We didn't find a user function. Maybe it's a java function?
        
        JavaFunction javaFunction = API.getJavaFunction(functionName);
        
        if(javaFunction != null) {
            return javaFunction.execute(args);
        }
        
        throw new InterpreterException("Function '"+functionName+"' does not exist!");
    }
    
}

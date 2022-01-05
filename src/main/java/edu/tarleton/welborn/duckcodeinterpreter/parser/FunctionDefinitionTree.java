/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.tarleton.welborn.duckcodeinterpreter.parser;

import edu.tarleton.welborn.duckcodeinterpreter.execution.InterpreterAPI;
import edu.tarleton.welborn.duckcodeinterpreter.execution.InterpreterException;
import edu.tarleton.welborn.duckcodeinterpreter.execution.Pair;
import java.util.List;

/**
 *
 * @author Ethan Welborn - ethan.welborn@go.tarleton.edu
 */
public class FunctionDefinitionTree extends StatementTree {

    private String functionName;
    private List<String> functionArguments;
    private StatementBlockTree statementBlock;

    public FunctionDefinitionTree(String functionName, List<String> functionArguments, StatementBlockTree statementBlock) {
        this.functionName = functionName;
        this.functionArguments = functionArguments;
        this.statementBlock = statementBlock;
    }

    public StatementBlockTree getStatementBlock() {
        return statementBlock;
    }

    public void setStatementBlock(StatementBlockTree statementBlock) {
        this.statementBlock = statementBlock;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public List<String> getFunctionArguments() {
        return functionArguments;
    }

    public void setFunctionArguments(List<String> functionArguments) {
        this.functionArguments = functionArguments;
    }
    
    @Override
    public Variable execute(InterpreterAPI API) throws InterpreterException {
        // This declares the function
        Pair<String,FunctionDefinitionTree> userFunction = new Pair<>(functionName,this);
        
        API.getUserFunctions().add(userFunction);
        
        return null;
    }
    
    public Variable call(InterpreterAPI API, List<Variable> args) throws InterpreterException, InterruptedException {
        // This calls the function
        
        // Take each arg and declare it as a local variable
        if(functionArguments.size() < args.size()) {
            throw new InterpreterException("Function "+functionName+" expects at most "+functionArguments.size()+" arguments, encountered "+args.size()+" arguments");
        }
        
        for(int i=0;i<args.size();i++) {
            Variable arg = args.get(i);
            String localVariableName = functionArguments.get(i);
            
            Pair<String,Variable> declaration = null;
        
            declaration = new Pair<>(localVariableName,arg);

            API.getVariables().push(declaration);
        }
        
        statementBlock.executeAllStatements(API);
        
        // Clean up all local variable declarations
        for(int i=0;i<args.size();i++) {
            API.getVariables().pop();
        }
        
        return null;
    }

    @Override
    public void printTree(int currentLayer) {
        super.printString(this.getClass().getSimpleName() + ":",currentLayer);
        
        currentLayer = currentLayer + 1;
        super.printString("Function name: "+functionName,currentLayer);
        for(String arg : functionArguments) {
            super.printString("Function argument: "+arg,currentLayer);
        }
        statementBlock.printTree(currentLayer);
    }
    
}

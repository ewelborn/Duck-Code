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
public class VariableDeclarationTree extends StatementTree {

    private String variableName;
    private ExpressionTree optionalAssignment;

    public VariableDeclarationTree(String variableName) {
        this.variableName = variableName;
    }

    public VariableDeclarationTree(String variableName, ExpressionTree optionalAssignment) {
        this.variableName = variableName;
        this.optionalAssignment = optionalAssignment;
    }

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    public ExpressionTree getOptionalAssignment() {
        return optionalAssignment;
    }

    public void setOptionalAssignment(ExpressionTree optionalAssignment) {
        this.optionalAssignment = optionalAssignment;
    }
    
    @Override
    public void printTree(int currentLayer) {
        super.printString(this.getClass().getSimpleName() + ":",currentLayer);
        
        currentLayer = currentLayer + 1;
        super.printString("Variable name: "+variableName,currentLayer);
        if(optionalAssignment != null) {
            optionalAssignment.printTree(currentLayer);
        }
    }

    @Override
    public Variable execute(InterpreterAPI API) throws InterpreterException, InterruptedException {
        Pair<String,Variable> declaration = null;
        
        if(optionalAssignment != null) {
            declaration = new Pair<>(variableName,optionalAssignment.getResult(API));
        } else {
            declaration = new Pair<>(variableName,null);
        }
        
        API.getVariables().push(declaration);
        return declaration.getValue();
    }
    
}

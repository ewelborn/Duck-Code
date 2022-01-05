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
public class VariableAssignmentTree extends StatementTree {

    private String variableName;
    private ExpressionTree assignment;

    public VariableAssignmentTree(String variableName, ExpressionTree assignment) {
        this.variableName = variableName;
        this.assignment = assignment;
    }

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    public ExpressionTree getAssignment() {
        return assignment;
    }

    public void setAssignment(ExpressionTree assignment) {
        this.assignment = assignment;
    }

    @Override
    public Variable execute(InterpreterAPI API) throws InterpreterException, InterruptedException {
        for(Pair<String,Variable> pair : API.getVariables()) {
            if(pair.getKey().equals(variableName)) {
                pair.setValue(assignment.getResult(API));
                return assignment.getResult(API);
            }
        }
        
        throw new InterpreterException("Undeclared variable encountered with name "+variableName);
    }

    @Override
    public void printTree(int currentLayer) {
        super.printString(this.getClass().getSimpleName() + ":",currentLayer);
        
        currentLayer = currentLayer + 1;
        super.printString("Variable name: "+variableName,currentLayer);
        assignment.printTree(currentLayer);
    }
    
}

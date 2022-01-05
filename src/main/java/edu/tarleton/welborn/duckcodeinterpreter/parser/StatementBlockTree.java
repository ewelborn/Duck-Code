/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.tarleton.welborn.duckcodeinterpreter.parser;

import edu.tarleton.welborn.duckcodeinterpreter.execution.InterpreterAPI;
import edu.tarleton.welborn.duckcodeinterpreter.execution.InterpreterException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ethan Welborn - ethan.welborn@go.tarleton.edu
 */
public class StatementBlockTree extends AST {

    private List<StatementTree> statements;
    private int previousIndentationLevel;
    private int currentIndentationLevel;

    public StatementBlockTree(int previousIndentationLevel) {
        this.statements = new ArrayList<>();
        this.previousIndentationLevel = previousIndentationLevel;
        this.currentIndentationLevel = -1;
    }

    public List<StatementTree> getStatements() {
        return statements;
    }

    public void setStatements(List<StatementTree> statements) {
        this.statements = statements;
    }

    public int getPreviousIndentationLevel() {
        return previousIndentationLevel;
    }

    public void setPreviousIndentationLevel(int previousIndentationLevel) {
        this.previousIndentationLevel = previousIndentationLevel;
    }

    public int getCurrentIndentationLevel() {
        return currentIndentationLevel;
    }

    public void setCurrentIndentationLevel(int currentIndentationLevel) {
        this.currentIndentationLevel = currentIndentationLevel;
    }
    
    @Override
    public void printTree(int currentLayer) {
        super.printString(this.getClass().getSimpleName() + ":",currentLayer);
        
        currentLayer = currentLayer + 1;
        super.printString("Previous indentation: "+previousIndentationLevel,currentLayer);
        super.printString("Current indentation: "+currentIndentationLevel,currentLayer);
        
        for(StatementTree statement : statements) {
            statement.printTree(currentLayer);
        }
    }

    public void executeAllStatements(InterpreterAPI API) throws InterpreterException, InterruptedException {
        int variablesInScope = API.getVariables().size();
        int userFunctionsInScope = API.getUserFunctions().size();
        
        for(StatementTree statement : statements ) {
            if(API.getBreakFlag() || API.getReturnFlag()) {
                break;
            }
            
            statement.execute(API);
        }

        // Clean up the variable and user function stack so that nothing declared in this statement block
        // can leak out of its local scope
        int variablesToRemove = API.getVariables().size() - variablesInScope;
        int userFunctionsToRemove = API.getUserFunctions().size() - userFunctionsInScope;
        
        if(variablesToRemove > 0) {
            API.debugPrint("Removing "+variablesToRemove+" variables from the scope");
        }
        if(userFunctionsToRemove > 0) {
            API.debugPrint("Removing "+userFunctionsToRemove+" user functions from the scope");
        }
        
        for(int i=0;i<variablesToRemove;i++) {
            API.getVariables().pop();
        }
        for(int i=0;i<userFunctionsToRemove;i++) {
            API.getUserFunctions().pop();
        }
    }

}

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
public class LogicTree extends AST {

    private TermTree term;
    private AddTree add;

    public LogicTree(TermTree term, AddTree add) {
        this.term = term;
        this.add = add;
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
        term.printTree(currentLayer);
        if(add != null) {
            add.printTree(currentLayer);
        }
    }

    public Variable getResult(InterpreterAPI API) throws InterpreterException, InterruptedException {
        Variable result = term.getResult(API);
        if(add != null) {
            result = add.getResult(API,result);
        }
        
        return result;
    }

}

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
public class TermTree extends AST {
    
    private FactorTree factor;
    private MultiplyTree multiply;

    public TermTree(FactorTree factor, MultiplyTree multiply) {
        this.factor = factor;
        this.multiply = multiply;
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
        factor.printTree(currentLayer);
        if(multiply != null) {
            multiply.printTree(currentLayer);
        }
    }

    public Variable getResult(InterpreterAPI API) throws InterpreterException, InterruptedException {
        Variable result = factor.getResult(API);
        if(multiply != null) {
            result = multiply.getResult(API,result);
        }
        
        return result;
    }
    
}

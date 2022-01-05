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
public class FactorTree extends AST {

    private ExpTree exp;
    private PowerTree power;

    public FactorTree(ExpTree exp, PowerTree power) {
        this.exp = exp;
        this.power = power;
    }

    public ExpTree getExp() {
        return exp;
    }

    public PowerTree getPower() {
        return power;
    }

    @Override
    public void printTree(int currentLayer) {
        super.printString(this.getClass().getSimpleName() + ":",currentLayer);
        
        currentLayer = currentLayer + 1;
        exp.printTree(currentLayer);
        if(power != null) {
            power.printTree(currentLayer);
        }
    }

    public Variable getResult(InterpreterAPI API) throws InterpreterException, InterruptedException {
        Variable result = exp.getResult(API);
        if(power != null) {
            result = power.getResult(result);
        }
        
        return result;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.tarleton.welborn.duckcodeinterpreter.parser;

import edu.tarleton.welborn.duckcodeinterpreter.execution.InterpreterException;

/**
 *
 * @author Ethan Welborn - ethan.welborn@go.tarleton.edu
 */
public class PowerTree extends AST {

    private ExpTree exp;
    private PowerTree power;

    public PowerTree(ExpTree exp, PowerTree power) {
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Variable getResult(Variable result) throws InterpreterException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

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
public class BreakTree extends StatementTree {

    @Override
    public Variable execute(InterpreterAPI API) throws InterpreterException {
        API.setBreakFlag(true);
        return null;
    }

    @Override
    public void printTree(int currentLayer) {
        super.printString(this.getClass().getSimpleName() + ":",currentLayer);
    }

}

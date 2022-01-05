/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.tarleton.welborn.duckcodeinterpreter.execution.systemFunctions;

import edu.tarleton.welborn.duckcodeinterpreter.DuckFrame;
import edu.tarleton.welborn.duckcodeinterpreter.execution.InterpreterException;
import edu.tarleton.welborn.duckcodeinterpreter.execution.JavaFunction;
import edu.tarleton.welborn.duckcodeinterpreter.parser.Variable;
import edu.tarleton.welborn.duckcodeinterpreter.parser.VariableType;
import java.util.List;

/**
 *
 * @author Ethan Welborn - ethan.welborn@go.tarleton.edu
 */
public class Instruction implements JavaFunction {

    private final DuckFrame frame;

    public Instruction(DuckFrame frame) {
        this.frame = frame;
    }
    
    @Override
    public Variable execute(List<Variable> args) throws InterpreterException {
        if(args.size() == 1) {
            if(args.get(0).getVariableType() == VariableType.STRING) {
                frame.showMessageBox(args.get(0).getStringValue());
            } else {
                throw new InterpreterException("SetGameSize expects string argument");
            }
        } else {
            throw new InterpreterException("SetGameSize expects 1 argument, received "+args.size());
        }
        
        return null;
    }
}

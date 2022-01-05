/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.tarleton.welborn.duckcodeinterpreter.execution;

import edu.tarleton.welborn.duckcodeinterpreter.parser.Variable;
import java.util.List;

/**
 *
 * @author Ethan Welborn - ethan.welborn@go.tarleton.edu
 */
public interface JavaFunction {
    
    public Variable execute(List<Variable> args) throws InterpreterException, InterruptedException;
    
}

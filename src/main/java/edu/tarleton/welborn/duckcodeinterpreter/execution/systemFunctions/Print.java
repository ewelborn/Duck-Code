/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.tarleton.welborn.duckcodeinterpreter.execution.systemFunctions;

import edu.tarleton.welborn.duckcodeinterpreter.DuckFrame;
import edu.tarleton.welborn.duckcodeinterpreter.execution.JavaFunction;
import edu.tarleton.welborn.duckcodeinterpreter.parser.Variable;
import static java.lang.System.console;
import java.util.List;

/**
 *
 * @author Ethan Welborn - ethan.welborn@go.tarleton.edu
 */
public class Print implements JavaFunction {

    private DuckFrame frame;
    
    public Print(DuckFrame frame) {
        this.frame = frame;
    }
    
    @Override
    public Variable execute(List<Variable> args) {
        if(frame != null) {
            frame.print("> ");
            for(Variable variable : args) {
                frame.print(variable + " ");
            }
            frame.print("\n");
            
            return null;
        } else {
            for(Variable variable : args) {
                System.out.print(variable + " ");
            }
            System.out.println("");

            return null;
        }
    }
    
}

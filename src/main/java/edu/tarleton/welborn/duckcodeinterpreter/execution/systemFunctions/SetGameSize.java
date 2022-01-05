/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.tarleton.welborn.duckcodeinterpreter.execution.systemFunctions;

import edu.tarleton.welborn.duckcodeinterpreter.Game;
import edu.tarleton.welborn.duckcodeinterpreter.execution.InterpreterException;
import edu.tarleton.welborn.duckcodeinterpreter.execution.JavaFunction;
import edu.tarleton.welborn.duckcodeinterpreter.parser.Variable;
import edu.tarleton.welborn.duckcodeinterpreter.parser.VariableType;
import java.util.List;

/**
 *
 * @author Ethan Welborn - ethan.welborn@go.tarleton.edu
 */
public class SetGameSize implements JavaFunction {

    private final Game game;

    public SetGameSize(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }
    
    @Override
    public Variable execute(List<Variable> args) throws InterpreterException {
        if(args.size() == 2) {
            if(args.get(0).getVariableType() == VariableType.INT && args.get(1).getVariableType() == VariableType.INT) {
                game.setWorldWidth(args.get(0).getIntValue());
                game.setWorldHeight(args.get(1).getIntValue());
            } else {
                throw new InterpreterException("SetGameSize expects integer arguments");
            }
        } else {
            throw new InterpreterException("SetGameSize expects 2 arguments, received "+args.size());
        }
        
        return null;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.tarleton.welborn.duckcodeinterpreter.execution.systemFunctions;

import edu.tarleton.welborn.duckcodeinterpreter.Game;
import edu.tarleton.welborn.duckcodeinterpreter.execution.JavaFunction;
import edu.tarleton.welborn.duckcodeinterpreter.parser.Variable;
import edu.tarleton.welborn.duckcodeinterpreter.parser.VariableType;
import java.util.List;

/**
 *
 * @author Ethan Welborn - ethan.welborn@go.tarleton.edu
 */
public class Wait implements JavaFunction {

    private final Game game;

    public Wait(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }
    
    @Override
    public Variable execute(List<Variable> args) throws InterruptedException {
        if(args.size() > 0 && args.get(0).getVariableType() == VariableType.INT && args.get(0).getIntValue() > 0) {
            for(int i=0;i<args.get(0).getIntValue();i++) {
                game.tick();
            }
        } else if(args.isEmpty()) {
            game.tick();
        }
        
        return null;
    }
    
}
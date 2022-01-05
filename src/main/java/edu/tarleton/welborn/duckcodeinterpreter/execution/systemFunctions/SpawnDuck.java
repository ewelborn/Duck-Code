/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.tarleton.welborn.duckcodeinterpreter.execution.systemFunctions;

import edu.tarleton.welborn.duckcodeinterpreter.Duck;
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
public class SpawnDuck implements JavaFunction {

    private final Game game;

    public SpawnDuck(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }
    
    @Override
    public Variable execute(List<Variable> args) throws InterpreterException {
        if(args.size() == 3) {
            if(args.get(0).getVariableType() == VariableType.STRING && args.get(1).getVariableType() == VariableType.INT && args.get(1).getVariableType() == VariableType.INT) {
                double rotation = 0;
                switch(args.get(0).getStringValue().toLowerCase()) {
                    case "north":
                        rotation = 0;
                        break;
                    case "east":
                        rotation = 90;
                        break;
                    case "south":
                        rotation = 180;
                        break;
                    case "west":
                        rotation = 270;
                        break;
                    default:
                        throw new InterpreterException("SpawnDuck expected north, south, east, or west as direction argument, got "+args.get(0).getStringValue());
                }
                
                Duck quackers = new Duck(game,args.get(1).getIntValue(),args.get(2).getIntValue(),rotation);
                game.setDuck(quackers);
                game.getGameObjects().add(quackers);
            } else {
                throw new InterpreterException("SpawnDuck expects string,integer,integer arguments");
            }
        } else {
            throw new InterpreterException("SpawnDuck expects 3 arguments, received "+args.size());
        }
        
        return null;
    }
}

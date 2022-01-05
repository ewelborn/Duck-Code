/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.tarleton.welborn.duckcodeinterpreter.execution.systemFunctions;

import edu.tarleton.welborn.duckcodeinterpreter.Box;
import edu.tarleton.welborn.duckcodeinterpreter.BoxRemover;
import edu.tarleton.welborn.duckcodeinterpreter.BoxSpawner;
import edu.tarleton.welborn.duckcodeinterpreter.Conveyor;
import edu.tarleton.welborn.duckcodeinterpreter.Game;
import edu.tarleton.welborn.duckcodeinterpreter.Painter;
import edu.tarleton.welborn.duckcodeinterpreter.execution.InterpreterException;
import edu.tarleton.welborn.duckcodeinterpreter.execution.JavaFunction;
import edu.tarleton.welborn.duckcodeinterpreter.parser.Variable;
import edu.tarleton.welborn.duckcodeinterpreter.parser.VariableType;
import java.util.List;

/**
 *
 * @author Ethan Welborn - ethan.welborn@go.tarleton.edu
 */
public class SpawnItem implements JavaFunction {

    private final Game game;

    public SpawnItem(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }
    
    @Override
    public Variable execute(List<Variable> args) throws InterpreterException {
        if(args.size() >= 5) {
            if(args.get(0).getVariableType() == VariableType.STRING && (args.get(1).getVariableType() == VariableType.STRING || args.get(1).getVariableType() == VariableType.BOOLEAN) && args.get(2).getVariableType() == VariableType.STRING && args.get(3).getVariableType() == VariableType.INT && args.get(4).getVariableType() == VariableType.INT) {
                String itemClassName = args.get(0).getStringValue();
                
                String ID = "";
                boolean useID = true;
                
                if(args.get(1).getVariableType() == VariableType.STRING) {
                    ID = args.get(1).getStringValue();
                } else {
                    useID = false;
                }
                
                String direction = args.get(2).getStringValue();
                int x = args.get(3).getIntValue();
                int y = args.get(4).getIntValue();
                
                double rotation = 0;
                switch(direction.toLowerCase()) {
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
                        throw new InterpreterException("SpawnItem expected north, south, east, or west as direction argument, got "+direction);
                }
                
                switch(itemClassName.toLowerCase()) {
                    case "boxspawner":
                        BoxSpawner spawner = new BoxSpawner(game,x,y,rotation);
                        if(useID) { spawner.setID(ID); }
                        game.spawnGameObject(spawner);
                        
                        break;
                    case "boxremover":
                        BoxRemover remover = new BoxRemover(game,x,y);
                        if(useID) { remover.setID(ID); }
                        game.spawnGameObject(remover);
                        
                        break;
                    case "boxpainter":
                        if(args.size() >= 6 && args.get(5).getVariableType() == VariableType.STRING) {
                            Box.BoxColor color = Box.BoxColor.BLUE;
                            switch(args.get(5).getStringValue().toLowerCase()) {
                                case "blue":
                                    color = Box.BoxColor.BLUE;
                                    break;
                                case "red":
                                    color = Box.BoxColor.RED;
                                    break;
                            }
                            
                            Painter painter = new Painter(game,x,y,color);
                            if(useID) { painter.setID(ID); }
                            game.spawnGameObject(painter);
                        }
                        
                        break;
                    case "conveyorbelt":
                        Conveyor belt = new Conveyor(game,x,y,rotation);
                        if(useID) { belt.setID(ID); }
                        game.spawnGameObject(belt);
                        
                        break;
                    default:
                        throw new InterpreterException("No such item class exists with the name "+itemClassName);
                }
            } else {
                throw new InterpreterException("SpawnItem expects string, string/boolean, string, integer, integer arguments");
            }
        } else {
            throw new InterpreterException("SpawnItem expects at least 5 arguments, received "+args.size());
        }
        
        return null;
    }
}

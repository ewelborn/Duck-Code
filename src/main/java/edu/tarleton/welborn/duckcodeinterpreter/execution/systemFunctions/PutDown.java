/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.tarleton.welborn.duckcodeinterpreter.execution.systemFunctions;

import edu.tarleton.welborn.duckcodeinterpreter.Box;
import edu.tarleton.welborn.duckcodeinterpreter.Game;
import edu.tarleton.welborn.duckcodeinterpreter.GameObject;
import edu.tarleton.welborn.duckcodeinterpreter.execution.JavaFunction;
import edu.tarleton.welborn.duckcodeinterpreter.parser.Variable;
import edu.tarleton.welborn.duckcodeinterpreter.parser.VariableType;
import java.util.List;

/**
 *
 * @author Ethan Welborn - ethan.welborn@go.tarleton.edu
 */
public class PutDown implements JavaFunction {

    private final Game game;

    public PutDown(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }
    
    @Override
    public Variable execute(List<Variable> args) throws InterruptedException {
        int x = game.getDuck().getXInGrid();
        int y = game.getDuck().getYInGrid();
        
        switch(game.getDuck().getDirection()) {
            case NORTH:
                y = y - 1;
                break;
            case EAST:
                x = x + 1;
                break;
            case SOUTH:
                y = y + 1;
                break;
            case WEST:
                x = x - 1;
                break;
        }
        
        if(game.getDuck().getHolding() != null) {
            for(GameObject gameObject : game.getGameObjects()) {
                if(gameObject.getXInGrid() == x && gameObject.getYInGrid() == y && !gameObject.equals(game.getDuck()) && gameObject instanceof Box) {
                    return null;
                }
            }
            
            game.getDuck().getHolding().setX(x);
            game.getDuck().getHolding().setY(y);
            game.getDuck().getHolding().setNextX(x);
            game.getDuck().getHolding().setNextY(y);
            game.spawnGameObject(game.getDuck().getHolding());
            
            game.getDuck().setHolding(null);
        }
        
        game.tick();
        
        return null;
    }
    
}
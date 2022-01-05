/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.tarleton.welborn.duckcodeinterpreter.execution.systemFunctions;

import edu.tarleton.welborn.duckcodeinterpreter.Game;
import edu.tarleton.welborn.duckcodeinterpreter.GameObject;
import edu.tarleton.welborn.duckcodeinterpreter.execution.JavaFunction;
import edu.tarleton.welborn.duckcodeinterpreter.parser.Variable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.Timer;

/**
 *
 * @author Ethan Welborn - ethan.welborn@go.tarleton.edu
 */
public class MoveForward implements JavaFunction {

    private final Game game;

    public MoveForward(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }
    
    @Override
    public Variable execute(List<Variable> args) throws InterruptedException {
        
        // Advance duck position
        switch(game.getDuck().getDirection()) {
            case NORTH:
                game.getDuck().setNextY(game.getDuck().getY() - 1);
                break;
            case EAST:
                game.getDuck().setNextX(game.getDuck().getX() + 1);
                break;
            case SOUTH:
                game.getDuck().setNextY(game.getDuck().getY() + 1);
                break;
            case WEST:
                game.getDuck().setNextX(game.getDuck().getX() - 1);
                break;
        }
        
        // Run gametick
        game.tick();
        
        return null;
    }
    
}

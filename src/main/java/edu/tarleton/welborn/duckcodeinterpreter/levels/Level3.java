/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.tarleton.welborn.duckcodeinterpreter.levels;

import edu.tarleton.welborn.duckcodeinterpreter.Box;
import edu.tarleton.welborn.duckcodeinterpreter.BoxRemover;
import edu.tarleton.welborn.duckcodeinterpreter.BoxSpawner;
import edu.tarleton.welborn.duckcodeinterpreter.Conveyor;
import edu.tarleton.welborn.duckcodeinterpreter.Duck;
import edu.tarleton.welborn.duckcodeinterpreter.Game;
import edu.tarleton.welborn.duckcodeinterpreter.Level;
import edu.tarleton.welborn.duckcodeinterpreter.Painter;

/**
 *
 * @author Ethan Welborn - ethan.welborn@go.tarleton.edu
 */
public class Level3 extends Level {

    private Game game;
    
    @Override
    public void loadLevel(Game game) {
        this.game = game;
        
        game.setWorldWidth(10);
        game.setWorldHeight(6);
        
        for(int i=1;i<=4;i++) {
            game.getGameObjects().add(new Conveyor(game,1,i,0));
        }

        for(int i=1;i<=8;i++) {
            game.getGameObjects().add(new Conveyor(game,i,0,90));
        }

        BoxSpawner spawner = new BoxSpawner(game,1,5,0);
        BoxRemover remover = new BoxRemover(game,9,0);
        Painter painter = new Painter(game,4,2,Box.BoxColor.BLUE);

        game.getGameObjects().add(spawner);
        game.getGameObjects().add(remover);
        game.getGameObjects().add(painter);

        Duck quackers = new Duck(game,3,4,270);
        game.setDuck(quackers);
        game.getGameObjects().add(quackers);
    }

    private int ticksSinceLastBoxSpawn = 0;
    
    @Override
    public void onTick() {
        ticksSinceLastBoxSpawn++;
        
        if(ticksSinceLastBoxSpawn >= 12) {
            ticksSinceLastBoxSpawn = 0;
            
            Box box = new Box(game,1,5,Box.BoxColor.RED);
            box.setNextY(4);
            game.getGameObjects().add(box);
        }
    }

    private int blueBoxesRemoved = 0;
    
    @Override
    public void onBoxRemoval(BoxRemover remover, Box box) {
        if(box.getColor() == Box.BoxColor.BLUE) {
            blueBoxesRemoved++;
            if(blueBoxesRemoved == 5) {
                game.playerWon("5 blue boxes made it to the end");
            }
        } else {
            game.playerLost("You let a red box through");
        }
    }
    
}

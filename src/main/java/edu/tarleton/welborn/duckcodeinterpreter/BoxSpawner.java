/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.tarleton.welborn.duckcodeinterpreter;

import java.awt.image.BufferedImage;

/**
 *
 * @author Ethan Welborn - ethan.welborn@go.tarleton.edu
 */
public class BoxSpawner extends GameObject {
    
    private static BufferedImage boxSpawnerSprite;
    
    private int lastBoxSpawn = 4;

    public static void setBoxSpawnerSprite(BufferedImage boxSpawnerSprite) {
        BoxSpawner.boxSpawnerSprite = boxSpawnerSprite;
    }
    
    public BoxSpawner(Game game, int x, int y, double rotation) {
        super(game);
        
        super.setX(x);
        super.setY(y);
        super.setNextX(x);
        super.setNextY(y);
        super.setRotation(rotation);
        super.setNextRotation(rotation);

        super.setSprite(boxSpawnerSprite);
        
        super.setCanCollide(false);
    }
    
    @Override
    public void tick() {
        /*lastBoxSpawn++;
        
        if(lastBoxSpawn >= 11) {
            int x = this.getXInGrid();
            int y = this.getYInGrid();
            
            switch(super.getDirection()) {
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
            
            Box red = new Box(super.getGame(),x,y,Box.BoxColor.RED);
            
            super.getGame().spawnGameObject(red);
            lastBoxSpawn = 0;
        }*/
    }
}
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
public class Conveyor extends GameObject {
    
    private static BufferedImage conveyorSprite;
    
    public static void setConveyorSprite(BufferedImage newSprite) {
        conveyorSprite = newSprite;
    }
    
    public Conveyor(Game game, int x, int y, double rotation) {
        super(game);
        
        super.setX(x);
        super.setY(y);
        super.setNextX(x);
        super.setNextY(y);
        super.setRotation(rotation);
        super.setNextRotation(rotation);
        
        super.setSprite(conveyorSprite);
        
        super.setCanCollide(false);
    }
    
    @Override
    public void tick() {
        // Is there something on top of us? If so, move it!
        for(GameObject gameObject : super.getGame().getGameObjects()) {
            if(gameObject.getXInGrid() == this.getXInGrid() && gameObject.getYInGrid() == this.getYInGrid() && !gameObject.equals(this)) {
                //System.out.println("Object moved "+gameObject.getNextX()+" "+gameObject.getNextY());
                switch(super.getDirection()) {
                    case NORTH:
                        gameObject.setNextY(gameObject.getY() - 1);
                        break;
                    case EAST:
                        gameObject.setNextX(gameObject.getX() + 1);
                        break;
                    case SOUTH:
                        gameObject.setNextY(gameObject.getY() + 1);
                        break;
                    case WEST:
                        gameObject.setNextX(gameObject.getX() - 1);
                        break;
                }
            }
        }
    }

    @Override
    public String toString() {
        return "ConveyorBelt";
    }
    
    
    
}
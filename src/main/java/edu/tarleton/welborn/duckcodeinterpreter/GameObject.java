/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.tarleton.welborn.duckcodeinterpreter;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Ethan Welborn - ethan.welborn@go.tarleton.edu
 */
public abstract class GameObject {
    
    private final Game game;
    private BufferedImage sprite;
    
    private boolean canCollide = true;
    
    private String ID;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
    
    // Position in grid coordinates. Converted to screen coordinates by DuckFrame.java
    private double x;
    private double y;
    
    // Degrees
    private double rotation;
    private double oldRotation;
    private double nextRotation;
    
    // Where does this object want to move to on the next game tick?
    private double nextX;
    private double nextY;
    
    private double oldX;
    private double oldY;

    public GameObject(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public boolean isCanCollide() {
        return canCollide;
    }

    public void setCanCollide(boolean canCollide) {
        this.canCollide = canCollide;
    }
    
    public Direction getDirection() {
        if(rotation >= 45 && rotation < 135) {
            return Direction.EAST;
        } else if(rotation >= 135 && rotation < 225) {
            return Direction.SOUTH;
        } else if(rotation >= 225 && rotation < 315) {
            return Direction.WEST;
        } else {
            return Direction.NORTH;
        }
    }
    
    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        while(rotation < 0) {
            rotation = rotation + 360;
        }
        while(rotation >= 360) {
            rotation = rotation - 360;
        }
        this.rotation = rotation;
    }

    public double getOldRotation() {
        return oldRotation;
    }

    public void setOldRotation(double oldRotation) {
        this.oldRotation = oldRotation;
    }

    public double getNextRotation() {
        return nextRotation;
    }

    public void setNextRotation(double newRotation) {
        this.nextRotation = newRotation;
    }
    
    public double getOldX() {
        return oldX;
    }

    public void setOldX(double oldX) {
        this.oldX = oldX;
    }

    public double getOldY() {
        return oldY;
    }

    public void setOldY(double oldY) {
        this.oldY = oldY;
    }

    public BufferedImage getSprite() {
        return sprite;
    }

    public void setSprite(BufferedImage sprite) {
        this.sprite = sprite;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getNextX() {
        return nextX;
    }

    public void setNextX(double nextX) {
        if(x >= 0 && x < game.getWorldWidth()) {
            // Not out of bounds. Are we colliding with anyone?
            for(GameObject gameObject : game.getGameObjects()) {
                if(gameObject.getXInGrid() == nextX && gameObject.getYInGrid() == this.y && gameObject.canCollide == true && this.canCollide == true) {
                    return;
                }
            }
            this.nextX = nextX;
        }
    }

    public double getNextY() {
        return nextY;
    }

    public void setNextY(double nextY) {
        if(y >= 0 && y < game.getWorldWidth()) {
            // Not out of bounds. Are we colliding with anyone?
            for(GameObject gameObject : game.getGameObjects()) {
                if(gameObject.getXInGrid() == this.x && gameObject.getYInGrid() == nextY && gameObject.canCollide == true && this.canCollide == true) {
                    return;
                }
            }
            this.nextY = nextY;
        }
    }
    
    // Return the game object's grid position without having to worry about floating point numbers
    public int getXInGrid() {
        return (int)(x+0.5);
    }
    
    public int getYInGrid() {
        return (int)(y+0.5);
    }
    
    public abstract void tick();
    
}

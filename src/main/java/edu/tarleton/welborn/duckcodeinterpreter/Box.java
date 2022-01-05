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
public class Box extends GameObject {

    public enum BoxColor {
        BLUE,
        RED,
    }
    
    private static BufferedImage blueBoxSprite;
    private static BufferedImage redBoxSprite;
    private BoxColor color;
    
    public static void setBlueBoxSprite(BufferedImage newSprite) {
        blueBoxSprite = newSprite;
    }
    
    public static void setRedBoxSprite(BufferedImage newSprite) {
        redBoxSprite = newSprite;
    }
    
    public Box(Game game, int x, int y, BoxColor color) {
        super(game);
        
        super.setX(x);
        super.setY(y);
        super.setNextX(x);
        super.setNextY(y);
        super.setRotation(0);
        super.setNextRotation(0);
        
        this.setColor(color);
    }

    public BoxColor getColor() {
        return color;
    }

    public void setColor(BoxColor color) {
        this.color = color;
        switch(color) {
            case BLUE:
                super.setSprite(blueBoxSprite);
                break;
            case RED:
                super.setSprite(redBoxSprite);
                break;
        }
    }
    
    @Override
    public void tick() {
        
    }
}

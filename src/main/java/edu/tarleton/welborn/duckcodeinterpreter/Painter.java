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
public class Painter extends GameObject {
    
    private static BufferedImage bluePainterSprite;
    private static BufferedImage redPainterSprite;
    private String spawnerID;
    private Box.BoxColor color;
    
    public static void setBluePainterSprite(BufferedImage newSprite) {
        bluePainterSprite = newSprite;
    }
    
    public static void setRedPainterSprite(BufferedImage newSprite) {
        redPainterSprite = newSprite;
    }
    
    public Painter(Game game, int x, int y, Box.BoxColor color) {
        super(game);
        
        super.setX(x);
        super.setY(y);
        super.setNextX(x);
        super.setNextY(y);
        super.setRotation(0);
        super.setNextRotation(0);

        this.setColor(color);
        
        super.setCanCollide(false);
    }

    public Box.BoxColor getColor() {
        return color;
    }

    public void setColor(Box.BoxColor color) {
        this.color = color;
        switch(color) {
            case BLUE:
                super.setSprite(bluePainterSprite);
                break;
            case RED:
                super.setSprite(redPainterSprite);
                break;
        }
    }
    
    @Override
    public void tick() {
        int x = super.getXInGrid();
        int y = super.getYInGrid();
        
        for(GameObject gameObject : super.getGame().getGameObjects()) {
            if(gameObject.getXInGrid() == x && gameObject.getYInGrid() == y && !gameObject.equals(this) && gameObject instanceof Box) {
                Box box = (Box) gameObject;
                box.setColor(color);
            }
        }
    }
}
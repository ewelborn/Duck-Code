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
public class Duck extends GameObject {

    private static BufferedImage sprite;
    
    private Box holding;
    
    public static void setDuckSprite(BufferedImage newSprite) {
        sprite = newSprite;
    }
    
    public Duck(Game game, int x, int y, double rotation) {
        super(game);
        
        super.setX(x);
        super.setY(y);
        super.setNextX(x);
        super.setNextY(y);
        super.setRotation(rotation);
        super.setNextRotation(rotation);
        super.setSprite(sprite);
    }

    @Override
    public void tick() {
        
    }

    public Box getHolding() {
        return holding;
    }

    public void setHolding(Box holding) {
        this.holding = holding;
    }
    
}

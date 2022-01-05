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
public class BoxRemover extends GameObject {
    
    private static BufferedImage boxRemoverSprite;

    public static void setBoxRemoverSprite(BufferedImage boxRemoverSprite) {
        BoxRemover.boxRemoverSprite = boxRemoverSprite;
    }
    
    public BoxRemover(Game game, int x, int y) {
        super(game);
        
        super.setX(x);
        super.setY(y);
        super.setNextX(x);
        super.setNextY(y);
        super.setRotation(0);
        super.setNextRotation(0);

        super.setSprite(boxRemoverSprite);
        
        super.setCanCollide(false);
    }
    
    @Override
    public void tick() {
        int x = super.getXInGrid();
        int y = super.getYInGrid();
        
        for(GameObject gameObject : super.getGame().getGameObjects()) {
            if(gameObject.getXInGrid() == x && gameObject.getYInGrid() == y && !gameObject.equals(this) && gameObject instanceof Box) {
                super.getGame().getLevel().onBoxRemoval(this, (Box) gameObject);
                super.getGame().removeGameObject(gameObject);
            }
        }
    }
}
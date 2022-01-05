/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.tarleton.welborn.duckcodeinterpreter;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import javax.swing.JPanel;

/**
 *
 * @author Ethan Welborn - ethan.welborn@go.tarleton.edu
 */
public class GamePanel extends JPanel {
    
    private Game game;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
    
    public GamePanel() {
        
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 200);
    }

    private double X_getGridToScreenCoordinates(double x) {
        int size = (int)(((double) Math.min(getWidth() - 4, getHeight() - 4)) / ((double) Math.max(game.getWorldWidth(), game.getWorldHeight())));
        return ((getWidth() - (size * game.getWorldWidth())) / 2) + ((x) * size);
    }
    
    private double Y_getGridToScreenCoordinates(double y) {
        int size = (int)(((double) Math.min(getWidth() - 4, getHeight() - 4)) / ((double) Math.max(game.getWorldWidth(), game.getWorldHeight())));
        return ((getHeight() - (size * game.getWorldHeight())) / 2) + ((y) * size);
    }
    
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        //System.out.println("painting");
        
        Graphics2D g2d = (Graphics2D) g.create();
        if(game != null) {
            int size = (int)(((double) Math.min(getWidth() - 4, getHeight() - 4)) / ((double) Math.max(game.getWorldWidth(), game.getWorldHeight())));

            int y = (getHeight() - (size * game.getWorldHeight())) / 2;
            for (int horz = 0; horz < game.getWorldHeight(); horz++) {
                int x = (getWidth() - (size * game.getWorldWidth())) / 2;
                for (int vert = 0; vert < game.getWorldWidth(); vert++) {
                    g.drawRect(x, y, size, size);
                    x += size;
                }
                y += size;
            }
        
            synchronized(game.getGameObjects()) {
                //System.out.println("draw call");
                for(GameObject gameObject : game.getGameObjects()) {
                    //System.out.println("drawing object");
                    //Image a = gameObject.getSprite().getScaledInstance(size, size, Image.SCALE_DEFAULT);
                    //System.out.println((int) X_getGridToScreenCoordinates(gameObject.getX()));

                    AffineTransform tx = AffineTransform.getScaleInstance(((double) size) / ((double) gameObject.getSprite().getWidth()), ((double) size) / ((double) gameObject.getSprite().getHeight()));
                    tx.rotate(Math.toRadians(gameObject.getRotation()), gameObject.getSprite().getWidth() / 2, gameObject.getSprite().getHeight() / 2);

                    //AffineTransform tx = AffineTransform.getRotateInstance(Math.toRadians(gameObject.getRotation()), gameObject.getSprite().getWidth() / 2, gameObject.getSprite().getHeight() / 2);
                    //tx.scale(((double) size) / ((double) gameObject.getSprite().getWidth()), ((double) size) / ((double) gameObject.getSprite().getHeight()));

                    AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
                    //System.out.println(gameObject.getRotation());
                    g2d.drawImage(op.filter(gameObject.getSprite(),null),(int) X_getGridToScreenCoordinates(gameObject.getX()),(int) Y_getGridToScreenCoordinates(gameObject.getY()),this);
                }
                //System.out.println("draw call OVER");
            }
        }
        
        //g.drawImage(game.getDuck().getSprite().getScaledInstance(size, size, Image.SCALE_DEFAULT), (int) X_getGridToScreenCoordinates(game.getDuck().getX()), (int) Y_getGridToScreenCoordinates(game.getDuck().getY()), this);
        
        g2d.dispose();
    }
    
}

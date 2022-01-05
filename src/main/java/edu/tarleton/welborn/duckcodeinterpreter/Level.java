/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.tarleton.welborn.duckcodeinterpreter;

/**
 *
 * @author Ethan Welborn - ethan.welborn@go.tarleton.edu
 */
public abstract class Level {

    public abstract void loadLevel(Game game);
    
    public abstract void onTick();
    
    public abstract void onBoxRemoval(BoxRemover remover, Box box);
    
}

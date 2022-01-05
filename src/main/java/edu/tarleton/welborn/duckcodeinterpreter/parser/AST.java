/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.tarleton.welborn.duckcodeinterpreter.parser;

/**
 *
 * @author Ethan Welborn - ethan.welborn@go.tarleton.edu
 */
public abstract class AST {
    
    public abstract void printTree(int currentLayer);
    
    public void printString(String str, int currentLayer) {
        System.out.println(" ".repeat(currentLayer) + "[" + currentLayer + "] " + str);
    }
    
}

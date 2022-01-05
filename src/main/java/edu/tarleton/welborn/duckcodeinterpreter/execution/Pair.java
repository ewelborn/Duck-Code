/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.tarleton.welborn.duckcodeinterpreter.execution;

/**
 *
 * @author Ethan Welborn - ethan.welborn@go.tarleton.edu
 */
public class Pair<T0, T1> {
    
    private T0 key;
    private T1 value;

    public Pair(T0 key, T1 value) {
        this.key = key;
        this.value = value;
    }

    public T0 getKey() {
        return key;
    }

    public void setKey(T0 key) {
        this.key = key;
    }

    public T1 getValue() {
        return value;
    }

    public void setValue(T1 value) {
        this.value = value;
    }
    
}

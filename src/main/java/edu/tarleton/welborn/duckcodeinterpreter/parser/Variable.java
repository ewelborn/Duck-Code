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
public class Variable {
    
    private final VariableType variableType;
    private final String stringValue;
    private final int intValue;
    private final double doubleValue;
    private final boolean booleanValue;

    public Variable(VariableType variableType, String stringValue) {
        this.variableType = variableType;
        
        if(stringValue.matches("^\".*\"$")) {
            this.stringValue = stringValue.substring(1, stringValue.length() - 1); // Remove the first and last quote marker;
        } else {
            this.stringValue = stringValue;
        }
        
        this.intValue = 0;
        this.doubleValue = 0.0;
        this.booleanValue = false;
    }

    public Variable(VariableType variableType, int intValue) {
        this.variableType = variableType;
        this.intValue = intValue;
        
        this.doubleValue = 0.0;
        this.booleanValue = false;
        this.stringValue = null;
    }
    
    public Variable(VariableType variableType, double doubleValue) {
        this.variableType = variableType;
        this.doubleValue = doubleValue;
        
        this.intValue = 0;
        this.booleanValue = false;
        this.stringValue = null;
    }
    
    public Variable(VariableType variableType, boolean booleanValue) {
        this.variableType = variableType;
        this.booleanValue = booleanValue;
        
        this.intValue = 0;
        this.doubleValue = 0.0;
        this.stringValue = null;
    }

    public VariableType getVariableType() {
        return variableType;
    }

    public String getStringValue() {
        return stringValue;
    }

    public int getIntValue() {
        return intValue;
    }

    public double getDoubleValue() {
        return doubleValue;
    }

    public boolean getBooleanValue() {
        return booleanValue;
    }

    @Override
    public String toString() {
        String returnValue = "";// this.variableType + " variable, value: ";
        
        switch(this.variableType) {
            case STRING:
                returnValue += stringValue;
                break;
            case INT:
                returnValue += intValue;
                break;
            case DOUBLE:
                returnValue += doubleValue;
                break;
            case BOOLEAN:
                returnValue += booleanValue;
                break;
        }
        
        return returnValue;
    }
    
}

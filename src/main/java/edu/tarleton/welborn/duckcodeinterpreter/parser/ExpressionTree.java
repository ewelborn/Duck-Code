/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.tarleton.welborn.duckcodeinterpreter.parser;

import edu.tarleton.welborn.duckcodeinterpreter.execution.InterpreterAPI;
import edu.tarleton.welborn.duckcodeinterpreter.execution.InterpreterException;

/**
 *
 * @author Ethan Welborn - ethan.welborn@go.tarleton.edu
 */
public class ExpressionTree extends AST {

    private LogicTree logic;
    private MoreLogicTree moreLogic;

    public ExpressionTree(LogicTree logic, MoreLogicTree moreLogic) {
        this.logic = logic;
        this.moreLogic = moreLogic;
    }

    public LogicTree getLogic() {
        return logic;
    }

    public void setLogic(LogicTree logic) {
        this.logic = logic;
    }

    public MoreLogicTree getMoreLogic() {
        return moreLogic;
    }

    public void setMoreLogic(MoreLogicTree moreLogic) {
        this.moreLogic = moreLogic;
    }
    
    @Override
    public void printTree(int currentLayer) {
        super.printString(this.getClass().getSimpleName() + ":",currentLayer);
        
        currentLayer = currentLayer + 1;
        logic.printTree(currentLayer);
        if(moreLogic != null) {
            moreLogic.printTree(currentLayer);
        }
    }

    public Variable getResult(InterpreterAPI API) throws InterpreterException, InterruptedException {
        Variable result = logic.getResult(API);
        if(moreLogic != null) {
            result = moreLogic.getResult(API,result);
        }
        
        return result;
    }

}

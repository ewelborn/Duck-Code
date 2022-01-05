/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.tarleton.welborn.duckcodeinterpreter;

import edu.tarleton.welborn.duckcodeinterpreter.execution.Interpreter;
import edu.tarleton.welborn.duckcodeinterpreter.execution.InterpreterException;
import edu.tarleton.welborn.duckcodeinterpreter.execution.JavaFunction;
import edu.tarleton.welborn.duckcodeinterpreter.execution.Pair;
import edu.tarleton.welborn.duckcodeinterpreter.execution.systemFunctions.MoveForward;
import edu.tarleton.welborn.duckcodeinterpreter.execution.systemFunctions.PickUp;
import edu.tarleton.welborn.duckcodeinterpreter.execution.systemFunctions.Print;
import edu.tarleton.welborn.duckcodeinterpreter.execution.systemFunctions.PutDown;
import edu.tarleton.welborn.duckcodeinterpreter.execution.systemFunctions.ScanForward;
import edu.tarleton.welborn.duckcodeinterpreter.execution.systemFunctions.TurnLeft;
import edu.tarleton.welborn.duckcodeinterpreter.execution.systemFunctions.TurnRight;
import edu.tarleton.welborn.duckcodeinterpreter.execution.systemFunctions.Wait;
import edu.tarleton.welborn.duckcodeinterpreter.parser.StatementBlockTree;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 *
 * @author Ethan Welborn - ethan.welborn@go.tarleton.edu
 */
public class Game {
    
    private final List<GameObject> gameObjects = Collections.synchronizedList(new ArrayList<>());
    private final List<GameObject> gameObjects_temp = new ArrayList<>();
    private final List<GameObject> gameObjects_destroy = new ArrayList<>();
    private Duck duck;
    private StatementBlockTree script;
    private DuckFrame frame;
    private long animationLength;
    private int worldWidth = 1;
    private int worldHeight = 1;
    private boolean ticking = false;
    private boolean stopped = false;
    private Thread executionThread;
    private edu.tarleton.welborn.duckcodeinterpreter.Level level;

    public Game() {
        
    }

    public edu.tarleton.welborn.duckcodeinterpreter.Level getLevel() {
        return level;
    }

    public void setLevel(edu.tarleton.welborn.duckcodeinterpreter.Level level) {
        this.level = level;
    }

    public boolean isStopped() {
        return stopped;
    }

    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }
    
    public void setWorldWidth(int worldWidth) {
        this.worldWidth = worldWidth;
    }

    public void setWorldHeight(int worldHeight) {
        this.worldHeight = worldHeight;
    }
    
    public int getWorldWidth() {
        return worldWidth;
    }

    public int getWorldHeight() {
        return worldHeight;
    }
    
    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    public StatementBlockTree getScript() {
        return script;
    }

    public void setScript(StatementBlockTree script) {
        this.script = script;
    }

    public DuckFrame getFrame() {
        return frame;
    }

    public void setFrame(DuckFrame frame) {
        this.frame = frame;
    }

    public Duck getDuck() {
        return duck;
    }

    public void setDuck(Duck duck) {
        this.duck = duck;
    }
    
    public void start() {
        animationLength = frame.getSimulationSpeed();
        
        Interpreter interpreter = new Interpreter(script,true);
        
        Pair<String,JavaFunction> printFunction = new Pair<>("print", new Print(frame));
        Pair<String,JavaFunction> scanForwardFunction = new Pair<>("scanForward", new ScanForward(this));
        Pair<String,JavaFunction> moveForwardFunction = new Pair<>("moveForward", new MoveForward(this));
        Pair<String,JavaFunction> turnLeftFunction = new Pair<>("turnLeft", new TurnLeft(this));
        Pair<String,JavaFunction> turnRightFunction = new Pair<>("turnRight", new TurnRight(this));
        Pair<String,JavaFunction> waitFunction = new Pair<>("wait", new Wait(this));
        Pair<String,JavaFunction> pickUpFunction = new Pair<>("pickUp", new PickUp(this));
        Pair<String,JavaFunction> putDownFunction = new Pair<>("putDown", new PutDown(this));
        
        interpreter.getAPI().getJavaFunctions().push(printFunction);
        interpreter.getAPI().getJavaFunctions().push(scanForwardFunction);
        interpreter.getAPI().getJavaFunctions().push(moveForwardFunction);
        interpreter.getAPI().getJavaFunctions().push(turnLeftFunction);
        interpreter.getAPI().getJavaFunctions().push(turnRightFunction);
        interpreter.getAPI().getJavaFunctions().push(waitFunction);
        interpreter.getAPI().getJavaFunctions().push(pickUpFunction);
        interpreter.getAPI().getJavaFunctions().push(putDownFunction);
        
        executionThread = new Thread(){
            public void run() {
                try {
                    interpreter.execute();
                } catch (InterpreterException ex) {
                    frame.print("Interpreter exception while trying to execute input script: "+ex.getMessage()+"\n");
                } catch (InterruptedException ex) {
                    // Oh well :)
                }
                
                frame.scriptEnded();
            }
        };
        
        executionThread.start();
    }
    
    public void spawnGameObject(GameObject gameObject) {
        // To allow game objects to be added during a game tick without a concurrent modification exception
        if(ticking) {
            gameObjects_temp.add(gameObject);
        } else {
            gameObjects.add(gameObject);
        }
    }
    
    public void removeGameObject(GameObject gameObject ){
        if(ticking) {
            gameObjects_destroy.add(gameObject);
        } else {
            gameObjects.remove(gameObject);
        }
    }
    
    public void tick() throws InterruptedException {
        // Run all animations
        //System.out.println("Game tick");
        ticking = true;
        
        level.onTick();
        
        for(GameObject gameObject : gameObjects) {
            gameObject.setOldX(gameObject.getX());
            gameObject.setOldY(gameObject.getY());
            gameObject.setOldRotation(gameObject.getRotation());
        }
        
        for(GameObject gameObject : gameObjects) {
            gameObject.tick();
        }
        
        long currentTime = System.currentTimeMillis();
        long lastTime = currentTime;
        long progress = 0;
        while(true) {
            TimeUnit.MILLISECONDS.sleep(16);

            currentTime = System.currentTimeMillis();
            long diffTime = currentTime - lastTime;
            progress += diffTime;

            if(progress > animationLength) {
                progress = animationLength;
            }

            //quackers.setX(quackers.getX() + ((double) diffTime / 1000));
            for(GameObject gameObject : gameObjects) {
                gameObject.setX(gameObject.getOldX() + ( (gameObject.getNextX() - gameObject.getOldX()) * (((double) progress) / ((double) animationLength)) ));
                gameObject.setY(gameObject.getOldY() + ( (gameObject.getNextY() - gameObject.getOldY()) * (((double) progress) / ((double) animationLength)) ));
                
                // Works fine for 
                if(Math.abs(gameObject.getOldRotation() - gameObject.getNextRotation()) < 180) {
                    gameObject.setRotation(gameObject.getOldRotation() + ( (gameObject.getNextRotation() - gameObject.getOldRotation()) * (((double) progress) / ((double) animationLength)) ));
                } else {
                    
                }
                //System.out.println(gameObject.getRotation());
            }

            frame.repaint();

            if(progress >= animationLength) {
                break;
            }

            lastTime = currentTime;
        }
        
        for(GameObject gameObject : gameObjects_temp) {
            gameObjects.add(gameObject);
        }
        
        for(GameObject gameObject : gameObjects_destroy) {
            gameObjects.remove(gameObject);
        }
        
        gameObjects_temp.clear();
        gameObjects_destroy.clear();
        
        ticking = false;
    }

    public boolean loadLevel(StatementBlockTree levelScript) {
        // Deprecated
        
        /*Interpreter interpreter = new Interpreter(levelScript,true);
        
        Pair<String,JavaFunction> printFunction = new Pair<>("print", new Print(frame));
        Pair<String,JavaFunction> setGameSizeFunction = new Pair<>("setGameSize", new SetGameSize(this));
        Pair<String,JavaFunction> spawnDuckFunction = new Pair<>("spawnDuck", new SpawnDuck(this));
        Pair<String,JavaFunction> spawnItemFunction = new Pair<>("spawnItem", new SpawnItem(this));
        Pair<String,JavaFunction> instructionFunction = new Pair<>("instruction", new Instruction(frame));
        
        interpreter.getAPI().getJavaFunctions().push(printFunction);
        interpreter.getAPI().getJavaFunctions().push(setGameSizeFunction);
        interpreter.getAPI().getJavaFunctions().push(spawnDuckFunction);
        interpreter.getAPI().getJavaFunctions().push(spawnItemFunction);
        interpreter.getAPI().getJavaFunctions().push(instructionFunction);
        
        try {
            interpreter.execute();
        } catch (InterpreterException ex) {
            frame.print("Interpreter exception while trying to execute input script: "+ex.getMessage()+"\n");
            return false;
        }
        
        return true;*/
        
        return false;
    }
    
    public void stop() {
        stopped = true;
        if(executionThread != null) {
            executionThread.interrupt();
            try {
                executionThread.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
            executionThread = null;
        }
        
        gameObjects.clear();
        gameObjects_temp.clear();
        gameObjects_destroy.clear();
    }
    
    public boolean loadLevel(edu.tarleton.welborn.duckcodeinterpreter.Level level) {
        stopped = false;
        level.loadLevel(this);
        this.level = level;
        
        return true;
    }

    public void playerWon(String msg) {
        frame.showMessageBox(msg);
    }

    public void playerLost(String msg) {
        frame.showMessageBox(msg);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.tarleton.welborn.duckcodeinterpreter;

import edu.tarleton.welborn.duckcodeinterpreter.execution.InterpreterException;
import edu.tarleton.welborn.duckcodeinterpreter.levels.Level3;
import edu.tarleton.welborn.duckcodeinterpreter.parser.ParsingException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Ethan Welborn - ethan.welborn@go.tarleton.edu
 */
public class DuckCode {
    /*private static void printTokensFromStringList(List<String> strings) {
        for(Token token : Lexer.getTokensFromStringList(strings)) {
            System.out.println(token);
        }
    }*/
    
    public static void main(String[] args) throws ParsingException, InterpreterException, InterruptedException {
        /*List<String> test1 = new ArrayList<>();
        test1.add("var a = 5");
        test1.add("if scanForward() is \"aaGreenBox\":");
        test1.add("  print(\"Green box ahead!\")");
        //test1.add("");
        test1.add("else if scanForward() is \"bbGreenBox\":");
        test1.add("  print(\"Something else ahead!\")");
        test1.add("else if scanForward() is \"ccGreenBox\":");
        test1.add("  print(\"wwewa\")");
        test1.add("else:");
        test1.add("  var b = 2");
        test1.add("  print(a)");
        test1.add("  print(b)");
        test1.add("  print(\"Dvorzak\")");
        test1.add("print(\"Hello, world!\", 12 + 3 * 5 / 4 - 1)");
        test1.add("print(a)");
        List<String> test2 = new ArrayList<>();
        test2.add("print(\"Hello, world!\")");
        test2.add("");
        test2.add("print(\"Hi mom!\", \" yee yaw\", 20 * 3 * 2 - 4)");
        test2.add("print(\"Feelin' you holding me tight, tight yeah\")");
        test2.add("");
        test2.add("print(3 is greater than 3, 4 is greater than 3, 5 is less than 2*4)");
        List<String> test3 = new ArrayList<>();
        test3.add("define foo():");
        test3.add("  print(\"Hello!\")");
        test3.add("define bar(i):");
        test3.add("  print(i)");
        test3.add("var j = 0");
        test3.add("repeat 2 times:");
        test3.add("  var i = 0");
        test3.add("  repeat 10 times:");
        test3.add("    i = i + 1");
        test3.add("    bar(i)");
        test3.add("  j = j + 1");
        test3.add("print(\"Goodbye, world!\")");
        
        List<Token> tokens = Lexer.getTokensFromStringList(test3);
        printTokensFromStringList(test3);
        StatementBlockTree AST = Parser.getStatementBlockTreeFromTokens(tokens);
        AST.printTree(0);
        
        Interpreter interpreter = new Interpreter(AST,true);
        
        Pair<String,JavaFunction> printFunction = new Pair<>("print", new Print());
        Pair<String,JavaFunction> scanForwardFunction = new Pair<>("scanForward", new ScanForward());
        
        interpreter.getAPI().getJavaFunctions().push(printFunction);
        interpreter.getAPI().getJavaFunctions().push(scanForwardFunction);
        
        interpreter.execute();*/
        
        final BufferedImage duck;
        final BufferedImage blueBox;
        final BufferedImage redBox;
        final BufferedImage conveyor;
        final BufferedImage boxSpawner;
        final BufferedImage boxRemover;
        final BufferedImage bluePainter;
        final BufferedImage redPainter;
        try {
            duck = ImageIO.read(new File("src/main/resources/duck.png"));
            blueBox = ImageIO.read(new File("src/main/resources/blueBox.png"));
            redBox = ImageIO.read(new File("src/main/resources/redBox.png"));
            conveyor = ImageIO.read(new File("src/main/resources/conveyor.png"));
            boxSpawner = ImageIO.read(new File("src/main/resources/boxSpawn.png"));
            boxRemover = ImageIO.read(new File("src/main/resources/boxRemover.png"));
            bluePainter = ImageIO.read(new File("src/main/resources/bluePainter.png"));
            redPainter = ImageIO.read(new File("src/main/resources/redPainter.png"));

            try {
                javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
                java.util.logging.Logger.getLogger(DuckFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }

            Duck.setDuckSprite(duck);
            Box.setBlueBoxSprite(blueBox);
            Box.setRedBoxSprite(redBox);
            Conveyor.setConveyorSprite(conveyor);
            BoxSpawner.setBoxSpawnerSprite(boxSpawner);
            BoxRemover.setBoxRemoverSprite(boxRemover);
            Painter.setBluePainterSprite(bluePainter);
            Painter.setRedPainterSprite(redPainter);
            
            Game game = new Game();
            
            game.loadLevel(new Level3());
            
            /*for(int i=1;i<=4;i++) {
                game.getGameObjects().add(new Conveyor(game,1,i,0));
            }
            
            for(int i=1;i<=8;i++) {
                game.getGameObjects().add(new Conveyor(game,i,0,90));
            }
            
            BoxSpawner spawner = new BoxSpawner(game,1,5,0,"spawner");
            BoxRemover remover = new BoxRemover(game,9,0,"remover");
            Painter painter = new Painter(game,4,2,Box.BoxColor.BLUE);
            
            game.getGameObjects().add(spawner);
            game.getGameObjects().add(remover);
            game.getGameObjects().add(painter);
            
            Duck quackers = new Duck(game,3,4,270);
            game.setDuck(quackers);
            game.getGameObjects().add(quackers);*/
            
            //Box blue = new Box(game,1,1,Box.BoxColor.BLUE);
            //Box red = new Box(game,1,3,Box.BoxColor.RED);
            //game.getGameObjects().add(blue);
            //game.getGameObjects().add(red);
            
            
            
            /*for(int y=1;y<6;y++) {
                game.getGameObjects().add(new Duck(1,y,duck));
            }*/
            
            final DuckFrame frame = new DuckFrame(game);
            
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    frame.setVisible(true);
                }
            });
            
            
            
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}

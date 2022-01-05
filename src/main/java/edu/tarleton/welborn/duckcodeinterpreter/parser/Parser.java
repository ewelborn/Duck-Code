/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.tarleton.welborn.duckcodeinterpreter.parser;

import edu.tarleton.welborn.duckcodeinterpreter.lexer.Token;
import edu.tarleton.welborn.duckcodeinterpreter.lexer.TokenType;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 *
 * @author Ethan Welborn - ethan.welborn@go.tarleton.edu
 */
public class Parser {
    
    // power -> ^ exp power
    // power -> epsilon
    private static PowerTree parsePowerTree(TokenManager tokenManager) throws ParsingException {
        return null;
    }
    
    // multiply -> optionalSpace * factor optionalSpace multiply
    // multiply -> optionalSpace / factor optionalSpace multiply
    // multiply -> optionalSpace AND factor optionalSpace multiply
    // multiply -> epsilon
    private static MultiplyTree parseMultiplyTree(TokenManager tokenManager) throws ParsingException {
        if(tokenManager.peek() == null) {
            return null;
        }
        
        tokenManager.consumeOptionalToken(TokenType.SPACE);
        
        TokenType operator = null;
        
        switch(tokenManager.peek().getTokenType()) {
            case MULTIPLY:
                tokenManager.consumeToken(TokenType.MULTIPLY);
                operator = TokenType.MULTIPLY;
                break;
            case DIVIDE:
                tokenManager.consumeToken(TokenType.DIVIDE);
                operator = TokenType.DIVIDE;
                break;
            case AND:
                tokenManager.consumeToken(TokenType.AND);
                operator = TokenType.AND;
                break;
            default:
                return null;
        }
        
        tokenManager.consumeOptionalToken(TokenType.SPACE);
        
        FactorTree factor = parseFactorTree(tokenManager);
        MultiplyTree multiply = parseMultiplyTree(tokenManager);
        
        return new MultiplyTree(operator,factor,multiply);
    }
    
    // add -> optionalSpace + optionalSpace term add
    // add -> optionalSpace - optionalSpace term add
    // add -> optionalSpace OR optionalSpace term add
    // add -> epsilon
    private static AddTree parseAddTree(TokenManager tokenManager) throws ParsingException {
        if(tokenManager.peek() == null) {
            return null;
        }
        
        tokenManager.consumeOptionalToken(TokenType.SPACE);
        
        TokenType operator = null;
        
        switch(tokenManager.peek().getTokenType()) {
            case PLUS:
                tokenManager.consumeToken(TokenType.PLUS);
                operator = TokenType.PLUS;
                break;
            case MINUS:
                tokenManager.consumeToken(TokenType.MINUS);
                operator = TokenType.MINUS;
                break;
            case OR:
                tokenManager.consumeToken(TokenType.OR);
                operator = TokenType.OR;
                break;
            default:
                return null;
        }
        
        tokenManager.consumeOptionalToken(TokenType.SPACE);
        
        TermTree term = parseTermTree(tokenManager);
        AddTree add = parseAddTree(tokenManager);
        
        return new AddTree(operator,term,add);
    }
    
    // moreLogic -> optionalSpace is optionalSpace logic moreLogic
    // moreLogic -> optionalSpace is optionalSpace less optionalSpace than optionalSpace logic moreLogic
    // moreLogic -> optionalSpace is optionalSpace greater optionalSpace than optionalSpace logic moreLogic
    // moreLogic -> epsilon
    private static MoreLogicTree parseMoreLogicTree(TokenManager tokenManager) throws ParsingException {
        if(tokenManager.peek() == null) {
            return null;
        }
        
        tokenManager.consumeOptionalToken(TokenType.SPACE);
        
        TokenType operator = TokenType.IS;
        
        if(tokenManager.peek() == null || tokenManager.peek().getTokenType() != TokenType.IS) {
            return null;
        }
        tokenManager.consumeToken(TokenType.IS);
        
        tokenManager.consumeOptionalToken(TokenType.SPACE);
        
        if(tokenManager.peek() == null) {
            throw new ParsingException(tokenManager.peek().getLine(),tokenManager.peek().getColumn(),"Expected less, greater, string, or integer, found EOF");
        }
        
        switch(tokenManager.peek().getTokenType()) {
            case LESS:
                tokenManager.consumeToken(TokenType.LESS);
                tokenManager.consumeOptionalToken(TokenType.SPACE);
                tokenManager.consumeToken(TokenType.THAN);
                operator = TokenType.LESS;
                break;
            case GREATER:
                tokenManager.consumeToken(TokenType.GREATER);
                tokenManager.consumeOptionalToken(TokenType.SPACE);
                tokenManager.consumeToken(TokenType.THAN);
                operator = TokenType.GREATER;
                break;
            // Not less than or greater than conditional. Could just be a regular is condition. Is the next token a valid atom?
            case STRING:
            case INTEGER:
            case TRUE:
            case FALSE:
                break;
            default:
                throw new ParsingException(tokenManager.peek().getLine(),tokenManager.peek().getColumn(),"Expected less, greater, string, or integer, found " + tokenManager.peek().getTokenType());
        }
        
        tokenManager.consumeOptionalToken(TokenType.SPACE);
        
        LogicTree logic = parseLogicTree(tokenManager);
        MoreLogicTree moreLogic = parseMoreLogicTree(tokenManager);
        
        return new MoreLogicTree(operator,logic,moreLogic);
    }
    
    // exp -> unaryOperator atomValue
    // exp -> atomValue
    private static ExpTree parseExpTree(TokenManager tokenManager) throws ParsingException {
        // No unary operator
        
        Variable variable = null;
        
        switch(tokenManager.peek().getTokenType()) {
            case STRING:
                variable = new Variable(VariableType.STRING,tokenManager.consumeToken(TokenType.STRING).getValue());
                break;
            case INTEGER:
                variable = new Variable(VariableType.INT,Integer.parseInt(tokenManager.consumeToken(TokenType.INTEGER).getValue()));
                break;
            case IDENT:
                if(tokenManager.peekIgnoreSpace(2) != null && tokenManager.peekIgnoreSpace(2).getTokenType() == TokenType.LEFT_PARENTHESES) {
                    // Function call
                    FunctionCallTree functionCall = parseFunctionCallTree(null,tokenManager);
                    return new ExpTree(functionCall);
                } else {
                    // Variable
                    Token ident = tokenManager.consumeToken(TokenType.IDENT);
                    return new ExpTree(ident.getValue());
                }
            case TRUE:
                variable = new Variable(VariableType.BOOLEAN,true);
                tokenManager.consumeToken(TokenType.TRUE);
                break;
            case FALSE:
                variable = new Variable(VariableType.BOOLEAN,false);
                tokenManager.consumeToken(TokenType.FALSE);
                break;
            default:
                throw new ParsingException(tokenManager.peek().getLine(),tokenManager.peek().getColumn(),"Expected string, integer, ident, true, or false, got "+tokenManager.peek().getTokenType());
        }
        
        return new ExpTree(variable);
    }
    
    // factor -> exp power
    private static FactorTree parseFactorTree(TokenManager tokenManager) throws ParsingException {
        ExpTree expTree = parseExpTree(tokenManager);
        PowerTree powerTree = parsePowerTree(tokenManager);
        return new FactorTree(expTree,powerTree);
    }
    
    // term -> factor multiply
    private static TermTree parseTermTree(TokenManager tokenManager) throws ParsingException {
        FactorTree factor = parseFactorTree(tokenManager);
        MultiplyTree multiply = parseMultiplyTree(tokenManager);
        return new TermTree(factor,multiply);
    }
    
    /*
    
        Order of operations (highest layer goes first)
        Atom Tree       - Parentheses / Negation / not / Type conversion
        Exp Tree        - Exponents
        Factor Tree     - Multiplication / Division / and
        Term Tree       - Addition / Subtraction / or
        Logic Tree      - is / is less than / is greater than
    
    */
    
    // logic -> term add
    private static LogicTree parseLogicTree(TokenManager tokenManager) throws ParsingException {
        TermTree term = parseTermTree(tokenManager);
        AddTree add = parseAddTree(tokenManager);
        return new LogicTree(term,add);
    }
    
    // expression -> logic moreLogic
    private static ExpressionTree parseExpressionTree(TokenManager tokenManager) throws ParsingException {
        LogicTree logic = parseLogicTree(tokenManager);
        MoreLogicTree moreLogic = parseMoreLogicTree(tokenManager);
        return new ExpressionTree(logic,moreLogic);
    }
    
    // functionCall -> ident ( optionalSpace )
    // functionCall -> ident ( optionalSpace expression optionalSpace )
    // functionCall -> ident ( optionalSpace expression optionalSpace , optionalSpace )
    // etc.
    private static FunctionCallTree parseFunctionCallTree(Deque<Integer> previousIndentations, TokenManager tokenManager) throws ParsingException {
        Token functionName = tokenManager.consumeToken(TokenType.IDENT);
        tokenManager.consumeOptionalToken(TokenType.SPACE);
        tokenManager.consumeToken(TokenType.LEFT_PARENTHESES);
        
        FunctionCallTree function = new FunctionCallTree(functionName.getValue());
        
        while(true) {
            if(tokenManager.peek() == null) {
                throw new ParsingException(functionName.getLine(),functionName.getColumn()+1,"Expected function arguments, got EOF");
            }
            
            tokenManager.consumeOptionalToken(TokenType.SPACE);
            
            if(tokenManager.peek().getTokenType() == TokenType.STRING || tokenManager.peek().getTokenType() == TokenType.INTEGER || tokenManager.peek().getTokenType() == TokenType.IDENT || tokenManager.peek().getTokenType() == TokenType.TRUE || tokenManager.peek().getTokenType() == TokenType.FALSE) {
                function.getFunctionArguments().add(parseExpressionTree(tokenManager));
            }
            
            tokenManager.consumeOptionalToken(TokenType.SPACE);
            
            if(tokenManager.peek() != null && tokenManager.peek().getTokenType() == TokenType.COMMA) {
                tokenManager.consumeToken(TokenType.COMMA);
            } else {
                break;
            }
            
            tokenManager.consumeOptionalToken(TokenType.SPACE);
        }
        
        tokenManager.consumeToken(TokenType.RIGHT_PARENTHESES);
        return function;
    }
    
    // ifTree -> if optionalSpace expression optionalSpace : optionalSpace newLine statementBlock
    // ifTree -> if optionalSpace expression optionalSpace : optionalSpace newLine statementBlock
    private static IfTree parseIfTree(Deque<Integer> previousIndentations, TokenManager tokenManager) throws ParsingException {
        tokenManager.consumeToken(TokenType.IF);
        tokenManager.consumeOptionalToken(TokenType.SPACE);
        ExpressionTree expression = parseExpressionTree(tokenManager);
        tokenManager.consumeOptionalToken(TokenType.SPACE);
        tokenManager.consumeToken(TokenType.COLON);
        tokenManager.consumeOptionalToken(TokenType.SPACE);
        tokenManager.consumeToken(TokenType.NEW_LINE);
        StatementBlockTree statementBlock = parseStatementBlockTree(previousIndentations, tokenManager);
        
        // Check for else / elseif
        tokenManager.consumeOptionalToken(TokenType.SPACE);
        if(tokenManager.peek(1) != null && tokenManager.peek(1).getTokenType() == TokenType.NEW_LINE) {
            int lookAhead = 2;
            if(tokenManager.peek(2) != null && tokenManager.peek(2).getTokenType() == TokenType.SPACE) {
                lookAhead = lookAhead + 1;
            }
            
            if(tokenManager.peek(lookAhead) != null && tokenManager.peek(lookAhead).getTokenType() == TokenType.ELSE) {
                if(tokenManager.peek(lookAhead+1) != null && tokenManager.peek(lookAhead+1).getTokenType() == TokenType.SPACE && tokenManager.peek(lookAhead+2) != null && tokenManager.peek(lookAhead+2).getTokenType() == TokenType.IF) {
                    // else if
                    tokenManager.consumeToken(TokenType.NEW_LINE);
                    tokenManager.consumeOptionalToken(TokenType.SPACE);
                    tokenManager.consumeToken(TokenType.ELSE);
                    tokenManager.consumeToken(TokenType.SPACE);
                    
                    IfTree elseIfTree = parseIfTree(previousIndentations, tokenManager);
                    
                    return new IfTree(expression,statementBlock,elseIfTree);
                } else {
                    // else
                    tokenManager.consumeToken(TokenType.NEW_LINE);
                    tokenManager.consumeOptionalToken(TokenType.SPACE);
                    tokenManager.consumeToken(TokenType.ELSE);
                    tokenManager.consumeToken(TokenType.COLON);
                    tokenManager.consumeOptionalToken(TokenType.SPACE);
                    tokenManager.consumeToken(TokenType.NEW_LINE);
                    StatementBlockTree elseStatementBlock = parseStatementBlockTree(previousIndentations, tokenManager);
                    //return null;

                    return new IfTree(expression,statementBlock,elseStatementBlock);
                }
            }
        }
        
        // Just a plain ol' if statement :)
        return new IfTree(expression,statementBlock);
    }
    
    // variableDeclaration -> var ident
    // variableDeclaration -> var ident = expression
    private static VariableDeclarationTree parseVariableDeclarationTree(Deque<Integer> previousIndentations, TokenManager tokenManager) throws ParsingException {
        tokenManager.consumeToken(TokenType.VAR);
        tokenManager.consumeToken(TokenType.SPACE);
        Token ident = tokenManager.consumeToken(TokenType.IDENT);
        
        if(tokenManager.peekIgnoreSpace(1) != null && tokenManager.peekIgnoreSpace(1).getTokenType() == TokenType.EQUALS) {
            // We're assigning the variable
            tokenManager.consumeOptionalToken(TokenType.SPACE);
            tokenManager.consumeToken(TokenType.EQUALS);
            tokenManager.consumeOptionalToken(TokenType.SPACE);
            ExpressionTree assignment = parseExpressionTree(tokenManager);
            
            return new VariableDeclarationTree(ident.getValue(),assignment);
        } else {
            // This is just a declaration to get the variable in scope
            
            return new VariableDeclarationTree(ident.getValue());
        }
    }
    
    // variableAssignment -> ident = expression
    private static VariableAssignmentTree parseVariableAssignmentTree(Deque<Integer> previousIndentations, TokenManager tokenManager) throws ParsingException {
        Token ident = tokenManager.consumeToken(TokenType.IDENT);
        
        tokenManager.consumeOptionalToken(TokenType.SPACE);
        tokenManager.consumeToken(TokenType.EQUALS);
        tokenManager.consumeOptionalToken(TokenType.SPACE);
        ExpressionTree assignment = parseExpressionTree(tokenManager);

        return new VariableAssignmentTree(ident.getValue(),assignment);
    }
    
    // repeat -> repeat : statementBlock
    // repeat -> repeat expression times: statementBlock
    private static RepeatTree parseRepeatTree(Deque<Integer> previousIndentations, TokenManager tokenManager) throws ParsingException {
        tokenManager.consumeToken(TokenType.REPEAT);
        tokenManager.consumeOptionalToken(TokenType.SPACE);
        
        if(tokenManager.peek() != null && tokenManager.peek().getTokenType() == TokenType.COLON) {
            tokenManager.consumeToken(TokenType.COLON);
            tokenManager.consumeOptionalToken(TokenType.SPACE);
            tokenManager.consumeToken(TokenType.NEW_LINE);
            StatementBlockTree statementBlock = parseStatementBlockTree(previousIndentations, tokenManager);
            
            return new RepeatTree(statementBlock);
        } else {
            tokenManager.consumeOptionalToken(TokenType.SPACE);
            ExpressionTree iterations = parseExpressionTree(tokenManager);
            tokenManager.consumeOptionalToken(TokenType.SPACE);
            tokenManager.consumeToken(TokenType.TIMES);
            tokenManager.consumeOptionalToken(TokenType.SPACE);
            tokenManager.consumeToken(TokenType.COLON);
            tokenManager.consumeOptionalToken(TokenType.SPACE);
            tokenManager.consumeToken(TokenType.NEW_LINE);
            StatementBlockTree statementBlock = parseStatementBlockTree(previousIndentations, tokenManager);
            
            return new RepeatTree(iterations,statementBlock);
        }
    }
    
    private static BreakTree parseBreakTree(Deque<Integer> previousIndentations, TokenManager tokenManager) throws ParsingException {
        tokenManager.consumeToken(TokenType.BREAK);
        return new BreakTree();
    }
    
    // functionDefinition -> define ident ( listOfIdents ) : statementBlock
    private static FunctionDefinitionTree parseFunctionDefinitionTree(Deque<Integer> previousIndentations, TokenManager tokenManager) throws ParsingException {
        tokenManager.consumeToken(TokenType.DEFINE);
        tokenManager.consumeToken(TokenType.SPACE);
        Token functionName = tokenManager.consumeToken(TokenType.IDENT);
        tokenManager.consumeOptionalToken(TokenType.SPACE);
        tokenManager.consumeToken(TokenType.LEFT_PARENTHESES);
        
        List<String> functionArguments = new ArrayList<>();
        
        while(true) {
            if(functionArguments.isEmpty()) {
                if(tokenManager.peekIgnoreSpace(1) != null && tokenManager.peekIgnoreSpace(1).getTokenType() == TokenType.IDENT) {
                    tokenManager.consumeOptionalToken(TokenType.SPACE);
                    functionArguments.add(tokenManager.consumeToken(TokenType.IDENT).getValue());
                    tokenManager.consumeOptionalToken(TokenType.SPACE);
                } else {
                    break;
                }
            } else {
                if(tokenManager.peekIgnoreSpace(1) != null && tokenManager.peekIgnoreSpace(1).getTokenType() == TokenType.COMMA && tokenManager.peekIgnoreSpace(2) != null && tokenManager.peekIgnoreSpace(2).getTokenType() == TokenType.IDENT) {
                    tokenManager.consumeOptionalToken(TokenType.SPACE);
                    tokenManager.consumeToken(TokenType.COMMA);
                    tokenManager.consumeOptionalToken(TokenType.SPACE);
                    functionArguments.add(tokenManager.consumeToken(TokenType.IDENT).getValue());
                    tokenManager.consumeOptionalToken(TokenType.SPACE);
                } else {
                    break;
                }
            }
        }
        
        tokenManager.consumeOptionalToken(TokenType.SPACE);
        tokenManager.consumeToken(TokenType.RIGHT_PARENTHESES);
        tokenManager.consumeOptionalToken(TokenType.SPACE);
        tokenManager.consumeToken(TokenType.COLON);
        tokenManager.consumeOptionalToken(TokenType.SPACE);
        tokenManager.consumeToken(TokenType.NEW_LINE);
        
        StatementBlockTree statementBlock = parseStatementBlockTree(previousIndentations, tokenManager);
        
        return new FunctionDefinitionTree(functionName.getValue(),functionArguments,statementBlock);
    }
    
    private static StatementTree parseStatementTree(Deque<Integer> previousIndentations, TokenManager tokenManager) throws ParsingException {
        if(tokenManager.peek(1) == null) {
            // Return empty statement
            // This is just a blank line
        }
        
        if(tokenManager.peek(1).getTokenType() == TokenType.IDENT && tokenManager.peekIgnoreSpace(2) != null && tokenManager.peekIgnoreSpace(2).getTokenType() == TokenType.LEFT_PARENTHESES) {
            return parseFunctionCallTree(previousIndentations, tokenManager);
        } else if(tokenManager.peek(1).getTokenType() == TokenType.IF) {
            return parseIfTree(previousIndentations, tokenManager);
        } else if(tokenManager.peek(1).getTokenType() == TokenType.IDENT && tokenManager.peekIgnoreSpace(2) != null && tokenManager.peekIgnoreSpace(2).getTokenType() == TokenType.EQUALS) {
            return parseVariableAssignmentTree(previousIndentations, tokenManager);
        } else if(tokenManager.peek(1).getTokenType() == TokenType.VAR && tokenManager.peekIgnoreSpace(2) != null && tokenManager.peekIgnoreSpace(2).getTokenType() == TokenType.IDENT) {
            return parseVariableDeclarationTree(previousIndentations, tokenManager);
        } else if(tokenManager.peek(1).getTokenType() == TokenType.REPEAT) {
            return parseRepeatTree(previousIndentations, tokenManager);
        } else if(tokenManager.peek(1).getTokenType() == TokenType.BREAK) {
            return parseBreakTree(previousIndentations, tokenManager);
        } else if(tokenManager.peek(1).getTokenType() == TokenType.DEFINE) {
            return parseFunctionDefinitionTree(previousIndentations, tokenManager);
        } else if(tokenManager.peekIgnoreSpace(1).getTokenType() == TokenType.NEW_LINE) {
            return null;
        } else {
            throw new ParsingException(tokenManager.peek(1).getLine(),tokenManager.peek(1).getColumn(),"Unrecognized statement starting with token "+tokenManager.peek(1).getTokenType());
        }
    }
    
    private static StatementBlockTree parseStatementBlockTree(Deque<Integer> previousIndentations, TokenManager tokenManager) throws ParsingException {
        StatementBlockTree statementBlock = new StatementBlockTree(previousIndentations.peek());
        
        while(true) {
            if(tokenManager.peek(1) == null) {
                return statementBlock;
            }
            
            if(statementBlock.getStatements().size() > 0 && tokenManager.peek(1).getTokenType() != TokenType.NEW_LINE) {
                throw new ParsingException(tokenManager.peek().getLine(),tokenManager.peek().getColumn(),"New line expected");
            }

            int indentation = 0;
            int lookAhead = 1;
            boolean spaceToken = false;
            
            if(statementBlock.getStatements().size() > 0) {
                lookAhead = 2;
            }

            if(tokenManager.peek(lookAhead) != null && tokenManager.peek(lookAhead).getTokenType() == TokenType.SPACE) {
                indentation = tokenManager.peek(lookAhead).getValue().length();
                spaceToken = true;
            }

            if(statementBlock.getCurrentIndentationLevel() < 0) {
                // This is the first statement in the block
                if(indentation > previousIndentations.peek()) {
                    statementBlock.setCurrentIndentationLevel(indentation);
                    previousIndentations.push(indentation);
                } else {
                    throw new ParsingException(tokenManager.peek().getLine(),tokenManager.peek().getColumn(),"Empty statement block encountered");
                }
            } else {
                // This is *not* the first statement in the block
                if(indentation == statementBlock.getCurrentIndentationLevel()) {
                    // All good :)
                } else {
                    // This statement must exist outside of this statement block
                    previousIndentations.pop();
                    return statementBlock;
                }
            }
            
            if(statementBlock.getStatements().size() > 0) {
                tokenManager.consumeToken(TokenType.NEW_LINE);
            }
            
            if(spaceToken) {
                tokenManager.consumeToken(TokenType.SPACE);
            }
            
            StatementTree statement = parseStatementTree(previousIndentations,tokenManager);
            if(statement != null) {
                statementBlock.getStatements().add(statement);
            }
        }
    }
    
    public static StatementBlockTree getStatementBlockTreeFromTokens(List<Token> tokens) throws ParsingException {
        TokenManager tokenManager = new TokenManager(tokens);
        Deque previousIndentations = new ArrayDeque<Integer>();
        previousIndentations.push(-1);
        return parseStatementBlockTree(previousIndentations,tokenManager);
    }
    
}

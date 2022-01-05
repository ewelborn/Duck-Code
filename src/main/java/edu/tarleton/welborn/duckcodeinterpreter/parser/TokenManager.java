/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.tarleton.welborn.duckcodeinterpreter.parser;

import edu.tarleton.welborn.duckcodeinterpreter.lexer.Token;
import edu.tarleton.welborn.duckcodeinterpreter.lexer.TokenType;
import java.util.List;

/**
 *
 * @author Ethan Welborn - ethan.welborn@go.tarleton.edu
 */
public class TokenManager {

    private List<Token> tokens;

    public TokenManager(List<Token> tokens) {
        this.tokens = tokens;
    }
    
    public Token peek() {
        return peek(1);
    }
    
    public Token peek(int forward) {
        if(tokens.size() < forward) {
            return null;
        }
        return tokens.get(forward - 1);
    }
    
    public Token peekIgnoreSpace(int forward) {
        // Step through the token list and count down for every non-space token we find
        int currentToken = -1;
        while(forward > 0) {
            currentToken++;
            
            if(currentToken >= tokens.size()) {
                return null;
            }
            
            if(tokens.get(currentToken).getTokenType() != TokenType.SPACE) {
                forward--;
            }
        }
        
        return tokens.get(currentToken);
    }
    
    public Token consumeToken(TokenType tokenType) throws ParsingException {
        if(tokens.size() < 0) {
            throw new ParsingException(-1,-1,"Expected token of type "+tokenType+", got EOF");
        }
        
        if(tokens.get(0).getTokenType() == tokenType) {
            Token token = tokens.get(0);
            tokens.remove(0);
            return token;
        } else {
            throw new ParsingException(tokens.get(0).getLine(),tokens.get(0).getColumn(),"Expected token of type "+tokenType+", got "+tokens.get(0).getTokenType());
        }
    }
    
    public Token consumeOptionalToken(TokenType tokenType) {
        if(tokens.isEmpty()) {
            return null;
        }
        
        if(tokens.get(0).getTokenType() == tokenType) {
            Token token = tokens.get(0);
            tokens.remove(0);
            return token;
        } else {
            return null;
        }
    }
}

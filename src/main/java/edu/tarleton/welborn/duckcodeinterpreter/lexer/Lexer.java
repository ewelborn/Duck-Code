/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.tarleton.welborn.duckcodeinterpreter.lexer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Ethan Welborn - ethan.welborn@go.tarleton.edu
 */
public class Lexer {
    
    private static final Pattern pattern_space = Pattern.compile(" +");
    private static final Pattern pattern_leftParentheses = Pattern.compile("\\(");
    private static final Pattern pattern_rightParentheses = Pattern.compile("\\)");
    private static final Pattern pattern_colon = Pattern.compile(":");
    private static final Pattern pattern_string = Pattern.compile("\"[^\"\\n]*\"");
    //private static final Pattern pattern_tab = Pattern.compile("(\\t|  )");
    private static final Pattern pattern_ident = Pattern.compile("[a-zA-Z]\\w*");
    private static final Pattern pattern_comma = Pattern.compile(",");
    private static final Pattern pattern_plus = Pattern.compile("\\+");
    private static final Pattern pattern_minus = Pattern.compile("-");
    private static final Pattern pattern_multiply = Pattern.compile("\\*");
    private static final Pattern pattern_divide = Pattern.compile("/");
    private static final Pattern pattern_integer = Pattern.compile("\\d+");
    private static final Pattern pattern_equals = Pattern.compile("=");

    private static final Pattern pattern_keyword_if = Pattern.compile("if");
    private static final Pattern pattern_keyword_is = Pattern.compile("is");
    private static final Pattern pattern_keyword_else = Pattern.compile("else");
    private static final Pattern pattern_keyword_or = Pattern.compile("or");
    private static final Pattern pattern_keyword_and = Pattern.compile("and");
    private static final Pattern pattern_keyword_not = Pattern.compile("not");
    private static final Pattern pattern_keyword_less = Pattern.compile("less");
    private static final Pattern pattern_keyword_greater = Pattern.compile("greater");
    private static final Pattern pattern_keyword_than = Pattern.compile("than");
    private static final Pattern pattern_keyword_var = Pattern.compile("var");
    private static final Pattern pattern_keyword_repeat = Pattern.compile("repeat");
    private static final Pattern pattern_keyword_times = Pattern.compile("times");
    private static final Pattern pattern_keyword_break = Pattern.compile("break");
    private static final Pattern pattern_keyword_define = Pattern.compile("define");
    private static final Pattern pattern_keyword_true = Pattern.compile("true");
    private static final Pattern pattern_keyword_false = Pattern.compile("false");
    
    private static boolean doesMatchExist(Matcher matcher) {
        return matcher.find() && matcher.start() == 0;
    }
    
    private static int advanceColumnByMatcher(Matcher matcher, int column) {
        return column + (matcher.end() - matcher.start());
    }
    
    public static List<Token> getTokensFromStringList(List<String> strings) throws LexerException {
        List<Token> tokenList = new ArrayList<>();
        
        int line = 0;
        for(String string : strings) {
            if(line > 0) {
                tokenList.add(new Token(TokenType.NEW_LINE,line + 1,0,"\\n"));
            }
            
            line++;
            int column = 1;
            while(column <= string.length()) {
                String input = string.substring(column - 1);
                
                Matcher matcher_space = pattern_space.matcher(input);
                Matcher matcher_leftParentheses = pattern_leftParentheses.matcher(input);
                Matcher matcher_rightParentheses = pattern_rightParentheses.matcher(input);
                Matcher matcher_colon = pattern_colon.matcher(input);
                Matcher matcher_string = pattern_string.matcher(input);
                //Matcher matcher_tab = pattern_tab.matcher(input);
                Matcher matcher_ident = pattern_ident.matcher(input);
                Matcher matcher_comma = pattern_comma.matcher(input);
                Matcher matcher_plus = pattern_plus.matcher(input);
                Matcher matcher_minus = pattern_minus.matcher(input);
                Matcher matcher_multiply = pattern_multiply.matcher(input);
                Matcher matcher_divide = pattern_divide.matcher(input);
                Matcher matcher_integer = pattern_integer.matcher(input);
                Matcher matcher_equals = pattern_equals.matcher(input);
                
                Matcher matcher_keyword_if = pattern_keyword_if.matcher(input);
                Matcher matcher_keyword_is = pattern_keyword_is.matcher(input);
                Matcher matcher_keyword_else = pattern_keyword_else.matcher(input);
                Matcher matcher_keyword_or = pattern_keyword_or.matcher(input);
                Matcher matcher_keyword_and = pattern_keyword_and.matcher(input);
                Matcher matcher_keyword_not = pattern_keyword_not.matcher(input);
                Matcher matcher_keyword_less = pattern_keyword_less.matcher(input);
                Matcher matcher_keyword_greater = pattern_keyword_greater.matcher(input);
                Matcher matcher_keyword_than = pattern_keyword_than.matcher(input);
                Matcher matcher_keyword_var = pattern_keyword_var.matcher(input);
                Matcher matcher_keyword_repeat = pattern_keyword_repeat.matcher(input);
                Matcher matcher_keyword_times = pattern_keyword_times.matcher(input);
                Matcher matcher_keyword_break = pattern_keyword_break.matcher(input);
                Matcher matcher_keyword_define = pattern_keyword_define.matcher(input);
                Matcher matcher_keyword_true = pattern_keyword_true.matcher(input);
                Matcher matcher_keyword_false = pattern_keyword_false.matcher(input);
                
                Map<Matcher,TokenType> matcherToTokenType = new HashMap<>();
                
                Map<Matcher,TokenType> matcherToTokenType_keywords = new HashMap<>();
                
                matcherToTokenType.put(matcher_space, TokenType.SPACE);
                matcherToTokenType.put(matcher_leftParentheses, TokenType.LEFT_PARENTHESES);
                matcherToTokenType.put(matcher_rightParentheses, TokenType.RIGHT_PARENTHESES);
                matcherToTokenType.put(matcher_colon, TokenType.COLON);
                matcherToTokenType.put(matcher_string, TokenType.STRING);
                matcherToTokenType.put(matcher_ident, TokenType.IDENT);
                matcherToTokenType.put(matcher_comma, TokenType.COMMA);
                matcherToTokenType.put(matcher_plus, TokenType.PLUS);
                matcherToTokenType.put(matcher_minus, TokenType.MINUS);
                matcherToTokenType.put(matcher_multiply, TokenType.MULTIPLY);
                matcherToTokenType.put(matcher_divide, TokenType.DIVIDE);
                matcherToTokenType.put(matcher_integer, TokenType.INTEGER);
                matcherToTokenType.put(matcher_equals, TokenType.EQUALS);
                
                matcherToTokenType_keywords.put(matcher_keyword_if, TokenType.IF);
                matcherToTokenType_keywords.put(matcher_keyword_is, TokenType.IS);
                matcherToTokenType_keywords.put(matcher_keyword_else, TokenType.ELSE);
                matcherToTokenType_keywords.put(matcher_keyword_or, TokenType.OR);
                matcherToTokenType_keywords.put(matcher_keyword_and, TokenType.AND);
                matcherToTokenType_keywords.put(matcher_keyword_not, TokenType.NOT);
                matcherToTokenType_keywords.put(matcher_keyword_less, TokenType.LESS);
                matcherToTokenType_keywords.put(matcher_keyword_greater, TokenType.GREATER);
                matcherToTokenType_keywords.put(matcher_keyword_than, TokenType.THAN);
                matcherToTokenType_keywords.put(matcher_keyword_var, TokenType.VAR);
                matcherToTokenType_keywords.put(matcher_keyword_repeat, TokenType.REPEAT);
                matcherToTokenType_keywords.put(matcher_keyword_times, TokenType.TIMES);
                matcherToTokenType_keywords.put(matcher_keyword_break, TokenType.BREAK);
                matcherToTokenType_keywords.put(matcher_keyword_define, TokenType.DEFINE);
                matcherToTokenType_keywords.put(matcher_keyword_true, TokenType.TRUE);
                matcherToTokenType_keywords.put(matcher_keyword_false, TokenType.FALSE);
                
                boolean matchFound = false;
                // Check for keywords first (We don't want "and" or "if" to be mistaken for idents)
                for(Map.Entry<Matcher,TokenType> pair : matcherToTokenType_keywords.entrySet()) {
                    if(doesMatchExist(pair.getKey())) {
                        tokenList.add(new Token(pair.getValue(),line,column,pair.getKey().group()));
                        column = advanceColumnByMatcher( pair.getKey(), column );
                        matchFound = true;
                        break;
                    }
                }
                
                if(!matchFound) {
                    // If we didn't find a keyword, then try the other patterns.
                    for(Map.Entry<Matcher,TokenType> pair : matcherToTokenType.entrySet()) {
                        if(doesMatchExist(pair.getKey())) {
                            tokenList.add(new Token(pair.getValue(),line,column,pair.getKey().group()));
                            column = advanceColumnByMatcher( pair.getKey(), column );
                            matchFound = true;
                            break;
                        }
                    }
                }
                
                if(!matchFound) {
                    throw new LexerException("Unknown token found at the beginning of input string: \""+input+"\"");
                }
            }
        }
        
        return tokenList;
    }
    
}

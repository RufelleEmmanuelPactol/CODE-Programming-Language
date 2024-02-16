package com.code.tokenizer;

import com.code.data.CodeInteger;
import com.code.data.CodeString;
import com.code.tokenizer.tokens.*;

import java.util.ArrayList;
import java.util.HashMap;

public class TokenFactory {

    protected HashMap<String, Class<? extends Token>> factoryMaster;
    protected ArrayList<Token> tokens;
    

    protected void initializeOperators() {
        addBinaryOperator(">");
        addBinaryOperator(">=");
        addBinaryOperator("<");
        addBinaryOperator("<=");
        addBinaryOperator("==");
        addBinaryOperator("+");
        addUnaryOperator("++");
        addBinaryOperator("-");
        addUnaryOperator("--");
        addBinaryOperator("/");
        addBinaryOperator("*");
        addBinaryOperator("%");
        addBinaryOperator("&&");
        addBinaryOperator("||");
        addUnaryOperator("!");
        addUnaryOperator("&");
        factoryMaster.put("[", LeftBracket.class);
        factoryMaster.put("]", RightBracket.class);
        factoryMaster.put("FUNCTION", FunctionBlock.class);
        factoryMaster.put("IF", IfBlock.class);
        factoryMaster.put("CODE", CodeBlock.class);
        factoryMaster.put(";", Separator.class);
        factoryMaster.put(".", DotOperator.class);
        factoryMaster.put("=", AssignmentToken.class);
        factoryMaster.put("(", OpenParenthesis.class);
        factoryMaster.put(")", CloseParenthesis.class);
        factoryMaster.put("WHILE", WhileToken.class);
        factoryMaster.put("BEGIN", BeginStatement.class);
        factoryMaster.put("END", EndStatement.class);
        factoryMaster.put(",", Separator.class);
        addDataType("INT");
        addDataType("STRING");
        addDataType("BOOL");
        addDataType("FLOAT");
        addDataType("LET");
    }

    protected void addDataType(String type) {
        factoryMaster.put(type, DataType.class);
    }

    protected Token terminalExpressionMatch(String str) {
        if (str.matches("\\d+")) {
            return new ValueToken(new CodeInteger(str));
        } if (str.matches("\".*\"")) {
            return new ValueToken(new CodeString(str));
        } else {
            return null;
        }
    }


    private void addBinaryOperator(String operator) {
        factoryMaster.put(operator, BinaryOperator.class);
    }

    protected void addUnaryOperator(String operator) {
        factoryMaster.put(operator, UnaryOperator.class);
    }

    protected TokenFactory(){
        factoryMaster = new HashMap<>();
        initializeOperators();
        tokens = new ArrayList<>();
    }

    public static final TokenFactory PRODUCE = new TokenFactory();

    public  ArrayList<Token> tokenize(LeximCursor lexims)  {
        try {
            ArrayList<Token> tokens = new ArrayList<>();
            for (String lexim : lexims) {
                Token token = terminalExpressionMatch(lexim);
                if (token != null) {
                    tokens.add(token);
                    continue;
                }

                Class<? extends Token> maybe = factoryMaster.get(lexim);

                if (maybe != null) {
                    tokens.add(maybe.newInstance());
                    continue;
                }

                tokens.add(new NonTerminals(lexim));
            }
            return tokens;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }



}

package com.code.tokenizer;

import com.code.data.*;
import com.code.tokenizer.tokens.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

public class TokenFactory {



    protected static HashMap<String, Class<? extends Token>> factoryMaster = new HashMap<>();
    protected ArrayList<Token> tokens;
    

    // This is the static initializer block. This block is used to initialize the operators
    // and the data types that are used in the language. This is a good practice to use
    // static initializer blocks to initialize static variables.

    // This allows the class to function as a singleton, but not necessarily locked as a singleton.
    protected static void initializeOperators() {
        addBinaryOperator(">");
        addBinaryOperator("<");
        addBinaryOperator(">=");
        addBinaryOperator("<");
        addBinaryOperator("<=");
        addBinaryOperator("==");
        addBinaryOperator("+=");
        addBinaryOperator("<>");
        addBinaryOperator("AND");
        addBinaryOperator("OR");
        addUnaryOperator("NOT");
        addUnaryOperator("CAST_BOOL");
        addUnaryOperator("CAST_STRING");
        addUnaryOperator("CAST_INT");
        addUnaryOperator("CAST_FLOAT");
        factoryMaster.put("@NATIVE", ImportNativeToken.class);
        factoryMaster.put("$", FlushToken.class);
        factoryMaster.put("*", TermToken.class);
        factoryMaster.put("/", TermToken.class);
        factoryMaster.put("&", BinaryOperator.class);
        factoryMaster.put("+", MultiTypeOperator.class);
        factoryMaster.put("%", ModOperator.class);
        factoryMaster.put("-", MultiTypeOperator.class);
        factoryMaster.put("FUNCTION", FunctionBlock.class);
        factoryMaster.put("IF", IfBlock.class);
        factoryMaster.put("CODE", CodeBlock.class);
        factoryMaster.put("DISPLAY", IntrinsicDisplay.class);
        factoryMaster.put("SCAN", IntrinsicScan.class);
        factoryMaster.put(";", SemicolonToken.class);
        factoryMaster.put(".", DotOperator.class);
        factoryMaster.put("=", AssignmentToken.class);
        factoryMaster.put("(", LeftParen.class);
        factoryMaster.put(")", RightParen.class);
        factoryMaster.put("WHILE", WhileToken.class);
        factoryMaster.put("BEGIN", BeginStatement.class);
        factoryMaster.put("END", EndStatement.class);
        factoryMaster.put("ELSE", ElseToken.class);
        factoryMaster.put("CONTINUE", ContinueToken.class);
        factoryMaster.put("BREAK", BreakToken.class);
        factoryMaster.put("FOR", ForToken.class);
        factoryMaster.put("create", NewToken.class);
        factoryMaster.put(",", Separator.class);
        factoryMaster.put(":", Colon.class);
        factoryMaster.put("{", LeftBrace.class);
        factoryMaster.put("}", RightBrace.class);
        factoryMaster.put("\n", NewLine.class);
        factoryMaster.put("THREADED", ThreadedToken.class);
        factoryMaster.put("RETURN", ReturnToken.class);
        addDataType("INT");
        addDataType("STRING");
        addDataType("BOOL");
        addDataType("FLOAT");
        addDataType("OBJECT");
        addDataType("CHAR");
    }

    protected static void addDataType(String type) {
        factoryMaster.put(type, DataType.class);
    }

    protected Token terminalExpressionMatch(String str) {
        // Handle integer literals
        if (str.matches("\\d+")) {
            return new ValueToken(new CodeInteger(Integer.parseInt(str)));
        }
        // Handle double literals, including optional sign and exponential notation
        else if (str.matches("[+-]?\\d*\\.\\d+([eE][+-]?\\d+)?")) {
            return new ValueToken(new CodeFloat(Double.parseDouble(str)));
        }
        // Handle string literals
        else if (str.matches("\".*\"")) {
            return new ValueToken(new CodeString(str));
        } else if (str.matches("'.*'")) {
            return new ValueToken(new CodeChar(str.replaceAll("'", "")));
        }
        // Handle boolean literals
        else if (str.matches("TRUE|FALSE")) {
            return new ValueToken(new CodeBoolean(Boolean.parseBoolean(str)));
        }
        // These brackets allow the display of one character.
        else if (str.matches("\\[.\\]")) {
            return new ValueToken(new CodeString(str));
        }
        else {
            return null;
        }
    }

    /**
     * Extracts the inner content of a bracketed expression, handling nested or escaped brackets.
     * @param str The input string with outer brackets.
     * @param start The start index (after the first bracket).
     * @param end The end index (before the last bracket).
     * @return The extracted inner content as a string.
     */
    private String extractInnerContent(String str, int start, int end) {
        StringBuilder contentBuilder = new StringBuilder();
        int level = 0; // Level of nested brackets
        for (int i = start; i < end; i++) {
            char currentChar = str.charAt(i);
            if (currentChar == '[') {
                level++;
            } else if (currentChar == ']') {
                if (level == 0) {
                    // Matching closing bracket for the initial level, add as escaped
                    contentBuilder.append("\\]");
                    continue;
                } else {
                    level--;
                }
            }
            contentBuilder.append(currentChar);
        }
        return contentBuilder.toString().replaceAll("\\\\\\[", "[").replaceAll("\\\\\\]", "]"); // Unescape escaped brackets
    }




    private static void addBinaryOperator(String operator) {
        factoryMaster.put(operator, BinaryOperator.class);
    }

    protected static void addUnaryOperator(String operator) {
        factoryMaster.put(operator, UnaryOperator.class);
    }

    public TokenFactory(){
        tokens = new ArrayList<>();
        initializeOperators();
    }



    private static final Class<?>[] genericParameterTypes = new Class<?>[]{String.class};

    /**
     * This method is used to tokenize the lexims that are passed to it.
     * Lexims are strings, but they need to be converted to tokens so that
     * the virtual machine can understand them.
     */
    public TokenCursor tokenize(LeximCursor lexims)  {
        try {
                // Here, we try to match the lexims to the terminal expressions
                // typically, these are numbers and strings, ergo, R-values.
                ArrayList<Token> tokens = new ArrayList<>();
                for (String lexim : lexims) {
                    if (lexim.equals("#")) break;
                    Token token = terminalExpressionMatch(lexim);
                    if (token != null) {
                        tokens.add(token);
                        continue;
                    }
                    
                    // If the lexim is not an r-value, we try to match it to the factory master
                    // which contains the classes that are used to create the tokens.
                    Class<? extends Token> maybe = factoryMaster.get(lexim);
                    // Here, we can check if it is a match.
                    if (maybe != null) {
                        // See this code here? We are basically creating a new instance of the token
                        // by using reflection. Here, we get the constructor of the class, set it to accessible
                        // and then create a new instance of the token. This is a bit slower than the traditional
                        // way of creating objects, but it is more flexible.

                        Constructor<? extends Token> constructor = maybe.getDeclaredConstructor(genericParameterTypes);
                        constructor.setAccessible(true);
                        tokens.add(constructor.newInstance(lexim));
                        continue;
                    }

                    // If not, we can assume that it is a non-terminal. However,
                    // this will be rechecked via the symbol table if it exists.
                    // since the symbol table only accepts valid declarations, such as:

                    // `INT x = 10`
                    // `STRING y = "Hello, World!"`
                    // `BOOL z = TRUE`
                    // `BEGIN FUNCTION MAIN`, here `MAIN` is a non-terminal.
                    tokens.add(new Variable(lexim));
            }
            return new TokenCursor(tokens);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }



}

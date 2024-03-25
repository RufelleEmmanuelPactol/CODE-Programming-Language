package com.code.tokenizer;

import com.code.data.CodeBoolean;
import com.code.data.CodeFloat;
import com.code.data.CodeInteger;
import com.code.data.CodeString;
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
        addBinaryOperator("%");
        addBinaryOperator("AND");
        addBinaryOperator("OR");
        addUnaryOperator("NOT");
        addUnaryOperator("CAST_BOOL");
        addUnaryOperator("CAST_STRING");
        addUnaryOperator("CAST_INT");
        addUnaryOperator("CAST_FLOAT");
        factoryMaster.put("$", FlushToken.class);
        factoryMaster.put("*", TermToken.class);
        factoryMaster.put("/", TermToken.class);
        factoryMaster.put("&", TermToken.class);
        factoryMaster.put("+", MultiTypeOperator.class);
        factoryMaster.put("-", MultiTypeOperator.class);
        factoryMaster.put("[", LeftBracket.class);
        factoryMaster.put("]", RightBracket.class);
        factoryMaster.put("FUNCTION", FunctionBlock.class);
        factoryMaster.put("IF", IfBlock.class);
        factoryMaster.put("CODE", CodeBlock.class);
        factoryMaster.put("DISPLAY", IntrinsicDisplay.class);
        factoryMaster.put("SCAN", IntrinsicScan.class);
        factoryMaster.put(";", Separator.class);
        factoryMaster.put(".", DotOperator.class);
        factoryMaster.put("=", AssignmentToken.class);
        factoryMaster.put("(", LeftParen.class);
        factoryMaster.put(")", RightParen.class);
        factoryMaster.put("WHILE", WhileToken.class);
        factoryMaster.put("BEGIN", BeginStatement.class);
        factoryMaster.put("END", EndStatement.class);
        factoryMaster.put(",", Separator.class);
        factoryMaster.put(":", Colon.class);
        factoryMaster.put("{", LeftBrace.class);
        factoryMaster.put("}", RightBrace.class);
        factoryMaster.put("\n", NewLine.class);
        addDataType("INT");
        addDataType("STRING");
        addDataType("BOOL");
        addDataType("FLOAT");
        addDataType("OBJECT");
        addDataType("MAP");
        addDataType("LIST");
    }

    protected static void addDataType(String type) {
        factoryMaster.put(type, DataType.class);
    }

    protected Token terminalExpressionMatch(String str) {
        // Handle integer literals
        if (str.matches("\\d+")) {
            return new ValueToken(new CodeInteger(str));
        }
        // Handle double literals, including optional sign and exponential notation
        else if (str.matches("[+-]?\\d*\\.\\d+([eE][+-]?\\d+)?")) {
            CodeFloat fl = new CodeFloat(str);
            return new ValueToken(fl);
        }
        // Handle string literals
        else if (str.matches("\".*\"")) {
            var data = str;
            if (data.length() == 3) {
                data = data.substring(1, data.length() - 1);
                return new ValueToken(new CodeString(data));
            }
            // Update the data to remove the escape characters
            data = data.substring(1, data.length() - 1).replaceAll("\\\\(.)", "$1");
            return new ValueToken(new CodeString(data));
        }
        // Handle boolean literals
        else if (str.matches("TRUE|FALSE")) {
            return new ValueToken(new CodeBoolean(str.equals("TRUE")));
        }
        else {
            return null;
        }
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
                    tokens.add(new NonTerminals(lexim));
            }
            return new TokenCursor(tokens);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }



}

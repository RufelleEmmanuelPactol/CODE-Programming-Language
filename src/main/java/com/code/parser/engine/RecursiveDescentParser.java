package com.code.parser.engine;

import com.code.exceptions.compile.ParseError;
import com.code.parser.nodes.*;
import com.code.tokenizer.TokenCursor;
import com.code.tokenizer.tokens.*;
import com.code.virtualmachine.CodeRuntime;

import java.util.ArrayList;
import java.util.List;

public class RecursiveDescentParser {
    protected TokenCursor currentCursor;


    public RecursiveDescentParser() {
        this.currentCursor=null;
    }

    public void appendCursor(TokenCursor cursor) {
        if (currentCursor == null) {
            currentCursor = cursor;
        } else {
            for (Token token : cursor) {
                currentCursor.append(token);
            }
        }
    }

    public void parseCompletely(){
        while (currentCursor.hasNext()) {
            CodeBlockNode stmt = (CodeBlockNode) parseBlock();
            if (stmt == null) {
                eat(NewLine.class);
                continue;
            }
            CodeRuntime.getRuntime().runtimeSymbolTable.add(stmt.getName().getTokenAsString(), stmt);
        }
    }



    public ASTNode parseStatement() {
        ASTNode expr = null;
        Token current = currentCursor.current();
        if (current instanceof DataType) {
            expr = parseDeclaration();
        } else {
            expr = parseExpression();
        } if (expr instanceof BlockEndNode){
            return expr;
        }
        eat(NewLine.class);
        return expr;
    }

    protected void eat(Class<? extends Token> tokenType) {
        Token token = currentCursor.current();
        if (!tokenType.isInstance(token)) {
            throw new ParseError("Expected " + tokenType.getSimpleName() + " token but got " + token.getClass().getSimpleName() + ".");
        } currentCursor.next();
    }

    protected ASTNode parseDeclaration() {
        Token type = currentCursor.current(); // Assuming this is the data type token
        eat(DataType.class); // Consuming the data type token

        List<VariableDeclarationNode.VarDeclaration> declarations = new ArrayList<>();
        do {
            Token varName = currentCursor.current();
            eat(NonTerminals.class); // Consuming the variable name token

            ASTNode initializer = null;
            if (currentCursor.current() instanceof AssignmentToken) {
                eat(AssignmentToken.class); // Consuming the assignment '=' token
                initializer = parseExpression(); // Parsing the initializer expression
            }

            declarations.add(new VariableDeclarationNode.VarDeclaration(varName, initializer));

            if (currentCursor.current() instanceof Separator) {
                eat(Separator.class); // Consuming the comma, moving to the next variable if present
            } else {
                break; // Exiting the loop if there's no comma, meaning no more variables in this declaration
            }
        } while (true);

        return new VariableDeclarationNode(type, declarations);
    }


    protected ASTNode parseExpression() {
        ASTNode left = parseSimpleExpression();
        while (currentCursor.current() instanceof BinaryOperator) {
            Token current = currentCursor.current();
            eat(BinaryOperator.class);
            left = new BinaryNode(left, current , parseSimpleExpression());
        } return left;
    }

    protected ASTNode parseSimpleExpression() {
        ASTNode left = parseTerm();
        while (currentCursor.current() instanceof MultiTypeOperator) {
            Token operator = currentCursor.current();
            eat(MultiTypeOperator.class);
            left = new FactorNode(left, operator, parseTerm());
        }
        return left;
    }

    protected ASTNode parseTerm() {
        ASTNode left = parseFactor();
        while (currentCursor.current() instanceof TermToken) {

            Token operator = currentCursor.current();
            eat(TermToken.class);
            left = new FactorNode(left, operator, parseFactor());
        } return left;
    }

    protected ASTNode parseFactor() {
        Token current = currentCursor.current();
        if (current instanceof ValueToken) {
            eat(ValueToken.class);
            return new FactorNode(null, current, null);
        } else if (current instanceof LeftParen) {
            eat(LeftParen.class);
            ASTNode result = parseExpression();
            eat(RightParen.class);
            return result;
        } else if (current instanceof MultiTypeOperator) {
            eat(MultiTypeOperator.class);
            return new UnaryNode(current, parseFactor());
        } else if (current instanceof NonTerminals) {
            eat(NonTerminals.class);
            return new NonTerminalFactorNode(null, current, null);
        }
        else if (current instanceof EndStatement) {
            return new BlockEndNode();
        } else if (current instanceof NewLine) {
            return null;
        }
        else
        throw new RuntimeException("[ParseError]: Expected ValueToken or LeftParen but got " + current.getClass().getSimpleName() +".");
    }

    protected ASTNode parseBlock() {
        ASTNode blockTypeNode = null;
        while (currentCursor.current() instanceof BeginStatement) {
            eat(BeginStatement.class);
            Token blockType = currentCursor.current();
            switch (blockType) {
                case FunctionBlock functionBlock -> {
                    ASTNode function = parseFunction();
                    eat(EndStatement.class);
                    eat(FunctionBlock.class);
                    currentCursor.next();
                    return function;
                }
                case CodeBlock codeBlock -> {
                    ASTNode function = parseFunction();
                    eat(EndStatement.class);
                    eat(CodeBlock.class);
                    currentCursor.next();
                    return function;
                }
                case IfBlock ifBlock -> eat(IfBlock.class);
                case WhileToken whileToken -> eat(WhileToken.class);
                case null, default -> {
                    assert blockType != null;
                    throw new RuntimeException("Expected FunctionBlock, CodeBlock, IfBlock, WhileToken, but got " +
                            blockType.getClass().getSimpleName());
                }
            }



        }
        return null;
    }

    protected ASTNode parseFunction(){
        Token functionType = currentCursor.current();
        Token name = null;
        ArrayList<ASTNode> statements = new ArrayList<>();
        ArrayList<ASTNode> args = null;
        if (functionType instanceof FunctionBlock) {
            eat(FunctionBlock.class);
            name = currentCursor.current();
            eat(NonTerminals.class);
            args = parseVarArgs();
        } else if (functionType instanceof CodeBlock) {
            eat(CodeBlock.class);
            name = new NonTerminals("CODE");
            args = new ArrayList<>(); // CODE is a zero-argument function

        } else {
            throw new RuntimeException("Expected FunctionBlock or CodeBlock but got " + functionType.getClass().getSimpleName() +".");
        }

        eat(NewLine.class);

        // Here, we parse the statements.
        while (true) {
            ASTNode stmt = parseStatement();
            if (stmt == null) {
                continue;
            }
            else if (stmt instanceof BlockEndNode) {
                break;
            }
            statements.add(stmt);
        }



        return new CodeBlockNode(functionType, name, args, statements, CodeRuntime.getRuntime().runtimeSymbolTable);
    }

    protected ArrayList<ASTNode> parseVarArgs() {
        ArrayList<ASTNode> parameters = new ArrayList<>();

        // Check if the next token indicates the start of parameter declarations
        // This check is necessary to handle functions without parameters.
        if (!(currentCursor.current() instanceof Colon)) {
            return parameters; // Return an empty list if no parameters are present
        } eat(Colon.class); // Consuming the colon separator

        while (true) {
            Token dataType = currentCursor.current();
            eat(DataType.class); // Consuming the data type token

            Token paramName = currentCursor.current();
            eat(NonTerminals.class); // Consuming the parameter name token

            // Create a new ParameterNode and add it to the list
            parameters.add(new ParameterNode(dataType, paramName));

            // If the next token is a comma, eat it and continue to the next parameter
            if (currentCursor.current() instanceof Separator) {
                eat(Separator.class); // Consuming the comma separator
            } else {
                break;
            }
        }

        return parameters;
    }

}

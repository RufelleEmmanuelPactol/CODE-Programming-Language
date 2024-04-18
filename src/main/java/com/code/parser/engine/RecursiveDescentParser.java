package com.code.parser.engine;

import com.code.Main;
import com.code.data.CodeString;
import com.code.errors.compile.ParseError;
import com.code.frames.StackFrame;
import com.code.parser.nodes.*;
import com.code.tokenizer.TokenCursor;
import com.code.tokenizer.tokens.*;
import com.code.virtualmachine.CodeRuntime;

import java.util.ArrayList;
import java.util.List;

public class RecursiveDescentParser {
    protected TokenCursor currentCursor;


    /**
     * Constructs a new RecursiveDescentParser without an initial cursor.
     * This setup implies that before parsing can start, a cursor must be set using appendCursor.
     */
    public RecursiveDescentParser() {
        this.currentCursor=null;
    }

    /**
     * Integrates a new TokenCursor with the current one. If there's no existing cursor,
     * the new cursor is set as the current. Otherwise, tokens from the new cursor are appended
     * to the existing cursor, effectively merging their sequences.
     *
     * @param cursor the token cursor to append
     */
    public void appendCursor(TokenCursor cursor) {
        if (currentCursor == null) {
            currentCursor = cursor;
        } else {
            for (Token token : cursor) {
                currentCursor.append(token);
            }
        }
    }

    /**
     * Initiates the complete parsing process. It continues to parse code blocks as long as
     * there are tokens available. Parsed blocks are added to the runtime's symbol table.
     * This method serves as the entry point for parsing the entire input provided to the parser.
     */
    public void parseCompletely(){
        while (currentCursor.hasNext()) {

            // parse top-line imports
             if (currentCursor.current() instanceof ImportNativeToken inT) {
                eat(ImportNativeToken.class);
                var node = new NativeNode(this.currentCursor.current());
                eat(Variable.class);
                node.execute();
                continue;
            }

            CodeBlockNode stmt = (CodeBlockNode) parseBlock();
            if (stmt == null) {
                eat(NewLine.class);
                continue;
            }
            CodeRuntime.getRuntime().runtimeSymbolTable.add(stmt.getName().getTokenAsString(), stmt);
        }
    }

    /**
     * Parses a single statement from the current cursor position. It identifies the type of statement
     * based on the current token and then calls the appropriate parsing method. This method can handle
     * different statement types including declarations, intrinsic operations like display and scan, and
     * expressions.
     *
     * @return the ASTNode representing the parsed statement
     */
    public ASTNode parseStatement() {
        ASTNode expr = null;
        Token current = currentCursor.current();
        switch (current) {
            case DataType dataType -> expr = parseDeclaration();
            case ForToken forToken -> expr = parseFor();
            case IntrinsicDisplay intrinsicDisplay -> {
                eat(IntrinsicDisplay.class);
                eat(Colon.class);
                expr = new DisplayNode(current, parseExpression());
                eat(NewLine.class);
            }
            case IntrinsicScan intrinsicScan -> {
                eat(IntrinsicScan.class);
                eat(Colon.class);
                expr = new ScanNode(current, parseVariadic());
                eat(NewLine.class);
            }
            case ReturnToken returnToken -> {
                eat(ReturnToken.class);
                ASTNode retExpr = parseExpression();
                expr = new ReturnNode(retExpr);
                eat(NewLine.class);
            }
            case Variable variable -> {
                if (currentCursor.hasNext() && currentCursor.lookAhead() instanceof AssignmentToken) {
                    expr = parseAssignment();
                } else {
                    expr = parseExpression();
                }
            } case ThreadedToken threadedToken -> {
                eat(ThreadedToken.class);
                expr = new ThreadedNode(parseStatement());
            }
            case IfBlock ifBlock -> expr = parseIfBlock();
            case BreakToken breakToken -> {
                eat(BreakToken.class);
                expr = new BreakNode();
                eat(NewLine.class);
            }
            case ContinueToken continueToken -> {
                eat(ContinueToken.class);
                expr = new ContinueNode();
                eat(NewLine.class);
            }
            case WhileToken whileToken -> expr = parseWhile();
            case null, default -> expr = parseExpression();
        }
        if (expr instanceof BlockEndNode){
            return expr;
        }
        return expr;
    }




    protected ASTNode parseWhile(){
        eat(WhileToken.class);
        eat(LeftParen.class);
        ASTNode condition = parseExpression();
        eat(RightParen.class);
        eat(NewLine.class);
        while (currentCursor.current() instanceof NewLine) {
            currentCursor.next();
        }
        eat(BeginStatement.class);
        eat(WhileToken.class);
        eat(NewLine.class);
        while (currentCursor.current() instanceof NewLine) {
            currentCursor.next();
        }
        ArrayList<ASTNode> statements = new ArrayList<>();
        while (true) {
            ASTNode node = parseStatement();
            if (node instanceof BlockEndNode) {
                eat(EndStatement.class);
                eat(WhileToken.class);
                eat(NewLine.class);
                break;
            } else if (node == null) {
                eat(NewLine.class);
            }else {
                statements.add(node);
            }
        }
        return new WhileNode(condition, statements);
    }
    protected ASTNode parseIfBlock() {
        eat(IfBlock.class);
        eat(LeftParen.class);
        ASTNode condition = parseExpression();
        eat(RightParen.class);
        eat(NewLine.class);
        while (currentCursor.current() instanceof NewLine) {
            currentCursor.next();
        }
        ArrayList<IfNode> innerIfs = new ArrayList<>();
        innerIfs.add(parseInnerIfBlock(condition));

        ArrayList<ASTNode> elseStatements = new ArrayList<>();
        while (currentCursor.current() instanceof NewLine) {
            currentCursor.next();
        }
        while (currentCursor.current() instanceof ElseToken) {
            eat(ElseToken.class);
            if (!(currentCursor.current() instanceof IfBlock)) {
                // ELSE PARSING
                while (currentCursor.current() instanceof NewLine) {
                    currentCursor.next();
                }
                eat(BeginStatement.class);
                eat(IfBlock.class);
                eat(NewLine.class);
                while (currentCursor.current() instanceof NewLine) {
                    currentCursor.next();
                }
                while (true) {
                    ASTNode node = parseStatement();
                    if (node instanceof BlockEndNode) {
                        eat(EndStatement.class);
                        eat(IfBlock.class);
                        break;
                    } else {
                        if (node == null) {
                            eat(NewLine.class);
                            continue;
                        }
                        if (currentCursor.current() instanceof NewLine) {
                            currentCursor.next();
                        }
                        elseStatements.add(node);
                    }
                } break;
            }
            eat(IfBlock.class);
            eat(LeftParen.class);
            condition = parseExpression();
            eat(RightParen.class);
            eat(NewLine.class);
            while (currentCursor.current() instanceof NewLine) {
                currentCursor.next();
            }
            innerIfs.add(parseInnerIfBlock(condition));
            if (currentCursor.current() instanceof NewLine) {
                currentCursor.next();
            }
          //  currentCursor.emergencyInsert(new NewLine("\n"));
        }
        while (currentCursor.current() instanceof NewLine) {
            currentCursor.next();
        }
        return new IfBlockNode(innerIfs, elseStatements);
    }

    protected IfNode parseInnerIfBlock(ASTNode expression){
        eat(BeginStatement.class);
        eat(IfBlock.class);
        eat(NewLine.class);
        while (currentCursor.current() instanceof NewLine) {
            currentCursor.next();
        }
        ArrayList<ASTNode> statements = new ArrayList<>();
        while (true) {
            ASTNode node = parseStatement();
            if (node instanceof BlockEndNode) {
                eat(EndStatement.class);
                eat(IfBlock.class);
                eat(NewLine.class);
                break;
            } else {
                if (node == null) {
                    eat(NewLine.class);
                    continue;
                }
                statements.add(node);
            }
        }
        return new IfNode(expression, statements);
    }

    /**
     * Consumes a token of the specified type from the current cursor. If the current token does not
     * match the expected type, a ParseError is thrown. This method ensures that the parser's
     * current position is at the expected type of token, moving forward in the token sequence.
     *
     * @param tokenType the Class object of the expected token type
     */
    protected void eat(Class<? extends Token> tokenType) {
        Token token = currentCursor.current();
        if (!tokenType.isInstance(token)) {
            // Assuming the current line is correctly obtained from some storage of code lines
            int currentLineNumber = CodeRuntime.getRuntime().GLOBAL_THREAD.getCurrentLineNumber();
            String currentLine = "";
            if (currentLineNumber > Main.rawCode.size()) {
                throw new ParseError("Reached the end of the code unexpectedly. Introspected token: " + token.getTokenAsString() + "\n\t\t\tToken mismatch: expected " + tokenType.getSimpleName() + " but got " + token.getClass().getSimpleName() + ".");
            } else {
                currentLine = Main.rawCode.get(currentLineNumber - 1);
            }
            String errorTokenAsString = token.getTokenAsString();

            // Find the first occurrence of the error token in the current line.
            int errorTokenStartIndex = currentLine.indexOf(errorTokenAsString);

            // Generate a string of carets (^) equal in length to the error token.
            String carets = "^".repeat(errorTokenAsString.length());

            // Create an indicator string with spaces leading up to the caret string.
            String indicator = " ".repeat(Math.max(0, errorTokenStartIndex)) + carets;

            throw new ParseError("On line "+ CodeRuntime.getRuntime().GLOBAL_THREAD.getCurrentLineNumber() +  ", expected " + tokenType.getSimpleName() + " token but got " + token.getClass().getSimpleName() + ".\n" +
                    "\t\t\tThis was detected in the following line: \n\t\t\t>>>\t" + currentLine +
                    "\n\t\t\t\t" + indicator);// Shows the indicator under the error token
        }
        currentCursor.next();
    }



    /**
     * Parses a declaration statement from the current cursor position. This method handles
     * variable declarations which may include multiple variables and optional initializers.
     * Declarations are separated by commas.
     *
     * @return an ASTNode representing the variable declaration
     */
    protected ASTNode parseDeclaration() {
        Token type = currentCursor.current(); // Assuming this is the data type token
        eat(DataType.class); // Consuming the data type token

        List<VariableDeclarationNode.VarDeclaration> declarations = new ArrayList<>();
        do {
            Token varName = currentCursor.current();
            eat(Variable.class); // Consuming the variable name token

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


    /**
     * Parses an expression from the current cursor position. This method handles the parsing of
     * complex expressions involving binary and unary operators, parentheses, and function calls.
     * The expression is parsed using the precedence climbing algorithm.
     *
     * @return an ASTNode representing the parsed expression
     */
    public ASTNode parseExpression() {
        ASTNode left = parseSimpleExpression();
        while (currentCursor.current() instanceof BinaryOperator || currentCursor.current() instanceof Colon) {
            Token current = currentCursor.current();
            if (!(current instanceof Colon)) {
                eat(BinaryOperator.class);
            } else{
                left =  parseFunctionCall(left.getValue());
                if (!(currentCursor.current() instanceof SemicolonToken)) {
                    return left;
                }
                eat(SemicolonToken.class);
                return left;
            } if (current instanceof DotOperator) {
                return parseMethodCall(left);
            }
            left = new BinaryNode(left, current , parseSimpleExpression());
        } return left;
    }



    /**
     * Parses a simple expression from the current cursor position. This method handles the parsing of
     * simple expressions that do not involve complex operators like binary or unary operators. It
     * calls the parseTerm method to parse the terms in the expression.
     *
     * @return an ASTNode representing the parsed simple expression
     */
    protected ASTNode parseSimpleExpression() {
        ASTNode left = parseTerm();

        while (currentCursor.current() instanceof MultiTypeOperator) {
            Token operator = currentCursor.current();
            eat(MultiTypeOperator.class);
            left = new TermNode(left, operator, parseTerm());
        }
        return left;
    }

    /**
     * Parses a term from the current cursor position. This method handles the parsing of terms in an
     * expression, which may include factors and term tokens. It calls the parseFactor method to parse
     * the factors in the term.
     *
     * @return an ASTNode representing the parsed term
     */
    protected ASTNode parseTerm() {
        ASTNode left = parseFactor();
        while (currentCursor.current() instanceof TermToken) {
            Token operator = currentCursor.current();
            eat(TermToken.class);
            left = new TermNode(left, operator, parseFactor());
        } return left;
    }

    /**
     * Parses a factor from the current cursor position. This method handles the parsing of factors in
     * an expression, which may include value tokens, parentheses, unary operators, and function calls.
     *
     * @return an ASTNode representing the parsed factor
     */
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
        }

        else if (current instanceof MultiTypeOperator) {
            if (current instanceof ModOperator) throw new RuntimeException("[ParseError]: Expression cannot lead with token '%'.");
            eat(MultiTypeOperator.class);
            return new UnaryNode(current, parseFactor());
        } else if (current instanceof Variable) {
            eat(Variable.class);
            return new NonTerminalFactorNode(null, current, null);
        }
        else if (current instanceof EndStatement) {
            return new BlockEndNode();
        } else if (current instanceof NewLine) {
            return null;
        } else if (current instanceof LeftBracket) {
            eat(LeftBracket.class);
            ASTNode node = parseExpression();
            eat(RightBracket.class);
            return node;
        } else if (current instanceof UnaryOperator un) {
            eat(UnaryOperator.class);
            return new UnaryNode(un, parseFactor());
        } else if (current instanceof NewToken) {
            eat(NewToken.class);
            Token s = currentCursor.current();
            eat(Variable.class);
            if (currentCursor.hasNext() && currentCursor.current() instanceof Colon) {
                eat(Colon.class);
                return new NewNode(s, parseFunctionArgs());
            } return new NewNode(s, new ArrayList<>());

        }
        else
        throw new RuntimeException("[ParseError]: Expected ValueToken or LeftParen but got " + current.getClass().getSimpleName() +".");
    }




    /**
     * Parses a block from the current cursor position. This method handles the parsing of blocks in the
     * code, which may include function definitions, code blocks, if-else blocks, and while loops.
     *
     * @return an ASTNode representing the parsed block
     */
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
                case null, default -> {
                    assert blockType != null;
                    throw new RuntimeException("Expected Function Block. Cannot parse top-line statements and expressions. Instead, got  " +
                            blockType.getClass().getSimpleName());
                }
            }



        }
        return null;
    }

    /**
     * Parses a function from the current cursor position. This method handles the parsing of function
     * definitions, including the function type, name, arguments, and statements within the function body.
     *
     * @return an ASTNode representing the parsed function
     */
    protected ASTNode parseFunction(){
        Token functionType = currentCursor.current();
        Token name = null;
        ArrayList<ASTNode> statements = new ArrayList<>();
        ArrayList<ASTNode> args = null;
        if (functionType instanceof FunctionBlock) {
            eat(FunctionBlock.class);
            name = currentCursor.current();
            eat(Variable.class);
            args = parseVarArgs();
        } else if (functionType instanceof CodeBlock) {
            eat(CodeBlock.class);
            name = new Variable("CODE");
            args = new ArrayList<>(); // CODE is a zero-argument function

        } else {
            throw new RuntimeException("Expected FunctionBlock or CodeBlock but got " + functionType.getClass().getSimpleName() +".");
        }

        eat(NewLine.class);

        // Here, we parse the statements.
        while (true) {
            ASTNode stmt = parseStatement();

            if (stmt == null) {
                eat(NewLine.class);
                continue;
            }
            else if (stmt instanceof BlockEndNode) {
                break;
            }
            statements.add(stmt);
        }



        return new CodeBlockNode(functionType, name, args, statements);
    }

    protected ASTNode parseAssignment() {
        Token varName = currentCursor.current();
        eat(Variable.class); // Consuming the variable name token
        eat(AssignmentToken.class); // Consuming the assignment '=' token
        ASTNode expr = parseStatement(); // Parsing the expression to assign
        return new AssignmentNode(new NonTerminalFactorNode(null, varName, null), expr);
    }

    /**
     * Parses the variable arguments of a function from the current cursor position. This method handles
     * the parsing of function parameters, including the data type and name of each parameter.
     *
     * @return a list of ASTNodes representing the parsed function parameters
     */
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
            eat(Variable.class); // Consuming the parameter name token

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

    protected VariadicNode parseVariadic() {
        List<NonTerminalFactorNode> args = new ArrayList<>();

        do {
            Token current = currentCursor.current();
            if (current instanceof Variable) {
                args.add(new NonTerminalFactorNode(null, current, null));
                currentCursor.next(); // Move to the next token

                // Check if there's a separator, indicating more identifiers to come
                if (currentCursor.current() instanceof Separator) {
                    currentCursor.next(); // Consume the separator and continue
                } else {
                    break; // No more identifiers to parse
                }
            } else {
                // If the current token is not a NonTerminals, it's an unexpected token in this context
                throw new ParseError("Expected an identifier but got " + current.getClass().getSimpleName() + ".");
            }
        } while (true);

        return new VariadicNode(args);
    }

    /**
     * Parses the arguments of a function from the current cursor position. This method handles the parsing
     * of function arguments, which may include identifiers, expressions, and variadic arguments.
     *
     * @return a list of ASTNodes representing the parsed function arguments
     */
    protected List<ASTNode> parseFunctionArgs() {
        List<ASTNode> args = new ArrayList<>();
        while (true) {
            Token current = currentCursor.current();
            // If the argument is a non-terminal (identifier)
            if (current instanceof Variable) {
                args.add(new NonTerminalFactorNode(null, current, null));
                currentCursor.next(); // Move to the next token
            }
            // If the argument is an expression
            else {
                ASTNode expr = parseExpression();
                if (expr != null) {
                    args.add(expr);
                } else {
                    // If parsing expression returned null, it means the current token didn't start an expression
                    // This could be a parse error or a specific case where the argument list is empty or ends.
                    break;
                }
            }

            if (currentCursor.current() instanceof SemicolonToken) {
                break;
            }

            // After parsing an argument, check if the next token is a separator (comma), indicating more arguments
            if (currentCursor.current() instanceof Separator) {
                currentCursor.next(); // Consume the separator and continue to the next argument
            } else {
                // If there's no separator, it means we've reached the end of the arguments list
                break;
            }
        }

        return args;
    }

    private ASTNode parseMethodCall(ASTNode obj){
        FactorNode objInstance = (FactorNode) obj;
        Token methodName = currentCursor.current();
        eat(Variable.class);
        if (currentCursor.hasNext() && currentCursor.current() instanceof Colon) {
            eat(Colon.class);
            if (currentCursor.hasNext()) {
                if (currentCursor.lookAhead() instanceof SemicolonToken) {
                    eat(SemicolonToken.class);
                    return new MethodCallNode(objInstance, methodName, new ArrayList<>(), false);
                }
            }
            List<ASTNode> parameters = parseFunctionArgs(); // Parse the function arguments
            if (currentCursor.hasNext() && !( currentCursor.current() instanceof NewLine)) {
                eat(SemicolonToken.class);
            } return new MethodCallNode(objInstance, methodName, parameters, false);
        } return new MethodCallNode(objInstance, methodName, new ArrayList<>(), true);
    }

    private ASTNode parseFunctionCall(Token functionName) {// Consume the function name token
        eat(Colon.class);
        if (currentCursor.hasNext() && currentCursor.current() instanceof SemicolonToken) {
            eat(SemicolonToken.class);
            return new FunctionCallNode(functionName, new ArrayList<>());
        }
        List<ASTNode> parameters = parseFunctionArgs(); // Parse the function arguments
        return new FunctionCallNode(functionName, parameters);
    }

    private ASTNode parseFor(){
        eat(ForToken.class);
        eat(LeftParen.class);
        ASTNode init = parseStatement();
        eat(SemicolonToken.class);
        ASTNode condition = parseExpression();
        eat(SemicolonToken.class);
        ASTNode increment = parseStatement();
        eat(RightParen.class);
        eat(NewLine.class);
        while (currentCursor.current() instanceof NewLine) {
            currentCursor.next();
        }
        eat(BeginStatement.class);
        eat(ForToken.class);
        eat(NewLine.class);
        while (currentCursor.current() instanceof NewLine) {
            currentCursor.next();
        }
        ArrayList<ASTNode> statements = new ArrayList<>();
        while (true) {
            ASTNode node = parseStatement();
            if (node instanceof BlockEndNode) {
                eat(EndStatement.class);
                eat(ForToken.class);
                break;
            } else {
                if (node == null) {
                    eat(NewLine.class);
                    continue;
                }
                statements.add(node);
            }
        }
        return new ForNode(init, condition, increment, statements);
    }


}

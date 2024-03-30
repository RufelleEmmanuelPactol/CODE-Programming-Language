package com.code.parser.nodes;

import com.code.parser.engine.SymbolTable;
import com.code.tokenizer.tokens.Token;

import java.util.List;

public class CodeBlockNode extends BlockNode {
    protected final List<ASTNode> args;
    protected final Token name;
    protected final List<ASTNode> statements;

    public List<ASTNode> getStatements() {
        return statements;
    }

    public Token getName() {
        return name;
    }

    public List<ASTNode> getArgs() {
        return args;
    }

    public CodeBlockNode(Token value, Token name, List<ASTNode> args, List<ASTNode> statements) {
        this.value = value;
        this.name = name;
        this.args = args;
        this.statements = statements;
    }


}

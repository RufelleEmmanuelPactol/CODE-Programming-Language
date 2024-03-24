package com.code.parser.nodes;

import com.code.parser.engine.SymbolTable;
import com.code.tokenizer.tokens.Token;

import java.util.List;

public class CodeBlockNode extends BlockNode {
    protected final List<ASTNode> args;
    protected final Token name;
    protected final List<ASTNode> statements;
    protected SymbolTable symbolTable;

    public Token getName() {
        return name;
    }

    public CodeBlockNode(Token value, Token name, List<ASTNode> args, List<ASTNode> statements, SymbolTable parent) {
        this.value = value;
        this.name = name;
        this.args = args;
        this.statements = statements;
    }
}

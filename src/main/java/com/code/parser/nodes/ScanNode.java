package com.code.parser.nodes;

import com.code.tokenizer.tokens.Token;

import java.util.List;

public class ScanNode extends ASTNode{
    protected final List<ASTNode> args;

    public ScanNode(Token value, List<ASTNode> args) {
        this.value = value;
        this.args = args;
    }
}

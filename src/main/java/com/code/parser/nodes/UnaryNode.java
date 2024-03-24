package com.code.parser.nodes;

import com.code.tokenizer.tokens.Token;

public class UnaryNode extends ASTNode{
    protected ASTNode child;

    public UnaryNode(Token value, ASTNode child){
        this.child = child;
    }

    public ASTNode getChild() {
        return child;
    }
}

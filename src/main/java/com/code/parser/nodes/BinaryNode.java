package com.code.parser.nodes;

import com.code.tokenizer.tokens.Token;

public class BinaryNode extends ASTNode{
    protected ASTNode left;
    protected ASTNode right;

    public BinaryNode(ASTNode left, Token value, ASTNode right) {
        this.left = left;
        this.right = right;
        this.value = value;
    }

    public ASTNode getLeft() {
        return left;
    }

    public ASTNode getRight() {
        return right;
    }
}

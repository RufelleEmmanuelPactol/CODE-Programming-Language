package com.code.parser.nodes;

import com.code.tokenizer.tokens.Token;

public class TermNode extends BinaryNode{

    public TermNode(ASTNode left, Token thisToken, ASTNode right) {
        super(left, thisToken, right);
    }
}

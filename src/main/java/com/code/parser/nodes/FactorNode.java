package com.code.parser.nodes;

import com.code.tokenizer.tokens.Token;

public class FactorNode extends BinaryNode{
    public FactorNode(ASTNode left, Token value, ASTNode right) {
        super(left, value, right);
    }
}

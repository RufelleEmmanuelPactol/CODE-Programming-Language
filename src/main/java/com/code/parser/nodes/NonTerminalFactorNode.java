package com.code.parser.nodes;

import com.code.tokenizer.tokens.Token;

public class NonTerminalFactorNode extends FactorNode{
    public NonTerminalFactorNode(ASTNode left, Token value, ASTNode right) {
        super(left, value, right);
    }
}

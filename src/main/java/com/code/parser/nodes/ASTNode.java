package com.code.parser.nodes;

import com.code.tokenizer.tokens.Token;

public abstract class ASTNode {
    protected Token value;

    @Override
    public String toString() {
        return value.getTokenAsString();
    }



    public Token getValue() {
        return value;
    }
}

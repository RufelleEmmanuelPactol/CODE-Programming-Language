package com.code.parser.nodes;

import com.code.tokenizer.tokens.Token;

public class ParameterNode extends ASTNode{
    private final Token paramName;

    public ParameterNode(Token dataType, Token paramName) {
        this.value = dataType;
        this.paramName = paramName;
    }
}

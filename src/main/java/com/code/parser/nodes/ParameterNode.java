package com.code.parser.nodes;

import com.code.tokenizer.tokens.Token;
import com.code.virtualmachine.CodeObject;

public class ParameterNode extends ASTNode{
    private final Token paramName;

    public Token getParamName() {
        return paramName;
    }

    public ParameterNode(Token dataType, Token paramName) {
        this.value = dataType;
        this.paramName = paramName;
    }

    @Override
    public CodeObject execute() {
        return null;
    }
}

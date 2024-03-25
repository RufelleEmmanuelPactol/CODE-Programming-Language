package com.code.parser.nodes;

import com.code.tokenizer.tokens.Token;
import com.code.tokenizer.tokens.ValueToken;
import com.code.virtualmachine.CodeObject;

public class FactorNode extends BinaryNode{
    public FactorNode(ASTNode left, Token value, ASTNode right) {
        super(left, value, right);
    }

    @Override
    public CodeObject execute() {
        return ((ValueToken)value).getValue();
    }
}

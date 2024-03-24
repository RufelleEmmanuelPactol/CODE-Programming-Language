package com.code.parser.nodes;

import com.code.tokenizer.tokens.Token;

public class DisplayNode extends UnaryNode{

    public DisplayNode(Token token, ASTNode thisChild) {
        super(token, thisChild);
    }
}

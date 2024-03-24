package com.code.parser.nodes;

import com.code.tokenizer.tokens.Token;

public class BeginNode extends UnaryNode {

    public BeginNode(Token beginToken, ASTNode child) {
        super(beginToken, child);
    }
}

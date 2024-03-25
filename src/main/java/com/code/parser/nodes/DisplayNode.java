package com.code.parser.nodes;

import com.code.tokenizer.tokens.Token;
import com.code.virtualmachine.CodeObject;

public class DisplayNode extends UnaryNode{

    public DisplayNode(Token token, ASTNode thisChild) {
        super(token, thisChild);
    }


    @Override
    public CodeObject execute() {
        CodeObject childValue = child.execute();
        System.out.print(childValue.getInstance());
        return childValue;
    }
}

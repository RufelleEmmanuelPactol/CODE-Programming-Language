package com.code.parser.nodes;

import com.code.data.CodeNil;
import com.code.tokenizer.tokens.Token;
import com.code.virtualmachine.CodeObject;
import com.code.virtualmachine.CodeRuntime;

public class DisplayNode extends UnaryNode{

    public DisplayNode(Token token, ASTNode thisChild) {
        super(token, thisChild);
    }


    @Override
    public CodeObject execute() {
        sync();
        CodeObject childValue = child.execute();
        CodeRuntime.print(childValue.getInstance());
        return childValue;
    }
}

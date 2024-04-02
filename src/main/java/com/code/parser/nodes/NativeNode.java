package com.code.parser.nodes;

import com.code.tokenizer.tokens.Token;
import com.code.virtualmachine.CodeClass;
import com.code.virtualmachine.CodeObject;

public class NativeNode extends ASTNode{

    public NativeNode(Token libName) {
        this.value = libName;
    }
    @Override
    public CodeObject execute() {
        sync();
        return CodeClass.getNull();
    }
}

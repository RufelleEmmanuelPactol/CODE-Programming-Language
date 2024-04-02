package com.code.parser.nodes;

import com.code.virtualmachine.CodeClass;
import com.code.virtualmachine.CodeObject;

public class BlockEndNode extends ASTNode{

    @Override
    public CodeObject execute() {
        sync();
        return CodeClass.getNull();
    }
}

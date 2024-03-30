package com.code.parser.nodes;

import com.code.tokenizer.tokens.Token;
import com.code.virtualmachine.CodeClass;
import com.code.virtualmachine.CodeObject;

public class AssignmentNode extends ASTNode{

    private final ASTNode rValue;
    private final NonTerminalFactorNode lValue;

    public AssignmentNode(NonTerminalFactorNode lValue, ASTNode rValue) {
        this.lValue = lValue;
        this.rValue = rValue;
    }
    @Override
    public CodeObject execute() {
        CodeObject obj = rValue.execute();
        lValue.execute().assign(obj);
        return CodeClass.getNull();
    }
}

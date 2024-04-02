package com.code.parser.nodes;

import com.code.tokenizer.tokens.Token;
import com.code.virtualmachine.CodeObject;
import com.code.virtualmachine.CodeRuntime;

public class NonTerminalFactorNode extends FactorNode{
    public NonTerminalFactorNode(ASTNode left, Token value, ASTNode right) {
        super(left, value, right);
    }

    @Override
    public CodeObject execute() {
        sync();
        return  (CodeObject) CodeRuntime.getRuntime().runtimeSymbolTable.searchAssert(value.getTokenAsString());
    }
}

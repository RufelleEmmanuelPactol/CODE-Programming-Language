package com.code.parser.nodes;

import com.code.tokenizer.tokens.ReturnToken;
import com.code.virtualmachine.CodeClass;
import com.code.virtualmachine.CodeObject;
import com.code.virtualmachine.CodeRuntime;

public class ReturnNode extends ASTNode{
    private ASTNode returnedExpression;

    public ReturnNode (ASTNode returnedExpression) {
        this.returnedExpression = returnedExpression;
    }
    @Override
    public CodeObject execute() {
        sync();
        CodeRuntime.getRuntime().runtimeSymbolTable.setReturnValue(returnedExpression.execute());
        CodeRuntime.getRuntime().returnInterrupt();
        return CodeClass.getNull();
    }
}

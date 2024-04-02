package com.code.parser.nodes;

import com.code.tokenizer.tokens.Token;
import com.code.virtualmachine.CodeObject;
import com.code.virtualmachine.CodeRuntime;

public abstract class ASTNode {
    protected Token value;
    protected int lineCreated;

    public ASTNode(){
        lineCreated = CodeRuntime.getRuntime().GLOBAL_THREAD.getCurrentLineNumber();
    }



    public Token getValue() {
        return value;
    }


    public abstract CodeObject execute();

    @Override
    public String toString() {
        return "ASTNode{" +
                "value=" + value +
                ", lineCreated=" + lineCreated +
                '}';
    }

    protected void sync(){
        CodeRuntime.getRuntime().GLOBAL_THREAD.syncLineNumber(this.lineCreated);
    }
}

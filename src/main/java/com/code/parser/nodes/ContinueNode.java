package com.code.parser.nodes;

import com.code.errors.runtime.GotoOutsideLoopError;
import com.code.virtualmachine.CodeClass;
import com.code.virtualmachine.CodeObject;
import com.code.virtualmachine.CodeRuntime;

public class ContinueNode extends ASTNode{
    @Override
    public CodeObject execute() {
        sync();
        if (CodeRuntime.getRuntime().retrieveLoopInterrupt()) {
            CodeRuntime.getRuntime().continueInterrupt();
        } else {
            throw new GotoOutsideLoopError();
        } return CodeClass.getNull();
    }
}

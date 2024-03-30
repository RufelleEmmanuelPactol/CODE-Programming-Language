package com.code.parser.nodes;

import com.code.tokenizer.tokens.Token;
import com.code.virtualmachine.CodeClass;
import com.code.virtualmachine.CodeObject;
import com.code.virtualmachine.CodeRuntime;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ThreadedFunctionCallNode extends FunctionCallNode{
    public ThreadedFunctionCallNode(FunctionCallNode node) {
        super(node.value, node.parameters);
    }

    @Override
    public CodeObject execute() {
        Thread t = new Thread(super::execute);
        t.start();
        return CodeRuntime.getRuntime().runtimeSymbolTable.getReturnedValue();
    }
}

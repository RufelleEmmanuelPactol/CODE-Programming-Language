package com.code.parser.nodes;

import com.code.virtualmachine.CodeObject;

import java.util.concurrent.atomic.AtomicReference;

public class ThreadedNode extends ASTNode{

    private final ASTNode node;
    public ThreadedNode(ASTNode node) {
        this.node = node;
    }

    @Override
    public CodeObject execute() {
        sync();
        AtomicReference<CodeObject> nodeRef = new AtomicReference<>();
        Thread t = new Thread(() -> {
            nodeRef.set(node.execute());
        });
        t.start();
        return nodeRef.get();
    }
}

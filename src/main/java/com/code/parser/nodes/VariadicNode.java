package com.code.parser.nodes;

import com.code.virtualmachine.CodeClass;
import com.code.virtualmachine.CodeObject;

import java.util.List;

public class VariadicNode extends ASTNode{
    private final List<NonTerminalFactorNode> args;

    public VariadicNode(List<NonTerminalFactorNode> args) {
        this.value = null;
        this.args = args;
    }

    protected List<NonTerminalFactorNode> getArgs() {
        return args;
    }

    @Override
    public CodeObject execute() {
        sync();
        return CodeClass.getNull();
    }


}

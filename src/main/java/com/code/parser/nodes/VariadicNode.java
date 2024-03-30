package com.code.parser.nodes;

import com.code.tokenizer.tokens.NonTerminals;
import com.code.tokenizer.tokens.Token;
import com.code.virtualmachine.CodeObject;
import com.code.virtualmachine.CodeRuntime;

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
        return null;
    }


}

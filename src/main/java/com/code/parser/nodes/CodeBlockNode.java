package com.code.parser.nodes;

import com.code.parser.engine.SymbolTable;
import com.code.tokenizer.tokens.Token;
import com.code.tokenizer.tokens.Variable;
import com.code.virtualmachine.CodeClass;
import com.code.virtualmachine.CodeObject;

import java.util.List;

public class CodeBlockNode extends BlockNode {
    protected final List<ASTNode> args;
    protected final Token name;
    protected final List<ASTNode> statements;

    public List<ASTNode> getStatements() {
        return statements;
    }

    public Token getName() {
        return name;
    }

    public List<ASTNode> getArgs() {
        return args;
    }

    public CodeBlockNode(Token value, Token name, List<ASTNode> args, List<ASTNode> statements) {
        this.value = value;
        this.name = name;
        this.args = args;
        this.statements = statements;
    }

    @Override
    public CodeObject execute() {
        for (int i=0; i<statements.size(); i++) {
            if (i == 0) VariableDeclarationNode.isDeclared = false;
            statements.get(i).execute();
            VariableDeclarationNode.isDeclared = true;
        }
        return CodeClass.getNull();
    }
}

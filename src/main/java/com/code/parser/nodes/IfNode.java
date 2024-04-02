package com.code.parser.nodes;

import com.code.Main;
import com.code.data.CodeBoolean;
import com.code.data.CodePrimitive;
import com.code.errors.runtime.NotAnExpressionError;
import com.code.virtualmachine.CodeClass;
import com.code.virtualmachine.CodeObject;
import com.code.virtualmachine.CodeRuntime;

import java.util.List;

public class IfNode extends ASTNode{
    private final ASTNode condition;
    private final List<ASTNode> statements;

    public IfNode(ASTNode condition, List<ASTNode> statements) {
        this.condition = condition;
        this.statements = statements;
    }
    @Override
    public CodeObject execute() {
        sync();
        CodeObject condition = this.condition.execute();
        if (condition.getInstance() instanceof CodePrimitive tv) {
            if (tv.bool().equals(CodeBoolean.TRUE)) {
                for (ASTNode statement : statements) {
                    statement.execute();
                } return CodeBoolean.TRUE_OBJECT;
            }
        } else throw new NotAnExpressionError(Main.rawCode.get(CodeRuntime.getRuntime().GLOBAL_THREAD.getCurrentLineNumber() - 1));

        return CodeBoolean.FALSE_OBJECT;
    }
}

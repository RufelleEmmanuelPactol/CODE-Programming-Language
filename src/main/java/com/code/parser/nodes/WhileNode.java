package com.code.parser.nodes;

import com.code.data.CodeBoolean;
import com.code.virtualmachine.CodeClass;
import com.code.virtualmachine.CodeObject;
import com.code.virtualmachine.CodeRuntime;

import java.util.List;

public class WhileNode extends ASTNode{

    private ASTNode condition;
    private List<ASTNode> body;

    public WhileNode(ASTNode condition, List<ASTNode> body) {
        this.condition = condition;
        this.body = body;
    }
    @Override
    public CodeObject execute() {
        sync();

        boolean breaking = false;
        while (((CodeBoolean)condition.execute().getInstance()).getData()) {
            CodeRuntime.getRuntime().loopInterrupt();
            CodeRuntime.getRuntime().pushSymbolTable();
            for (ASTNode statement : body) {
                statement.execute();
                if (CodeRuntime.getRuntime().retrieveContinueInterrupt()) {
                    break;
                } else if (CodeRuntime.getRuntime().retrieveBreakInterrupt()) {
                    breaking = true;
                }
            }
            CodeRuntime.getRuntime().popSymbolTable();
            if (breaking) {
                break;
            }
        }
        CodeRuntime.getRuntime().retrieveLoopInterrupt();
        return CodeClass.getNull();
    }
}

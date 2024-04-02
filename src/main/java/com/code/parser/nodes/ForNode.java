package com.code.parser.nodes;

import com.code.data.CodeBoolean;
import com.code.data.CodeChar;
import com.code.data.CodePrimitive;
import com.code.virtualmachine.CodeClass;
import com.code.virtualmachine.CodeObject;
import com.code.virtualmachine.CodeRuntime;

import java.util.List;

public class ForNode extends ASTNode{

    ASTNode init;
    ASTNode condition;
    ASTNode increment;
    List<ASTNode> block;

    public ForNode(ASTNode init, ASTNode condition, ASTNode increment, List<ASTNode> block) {
        this.init = init;
        this.condition = condition;
        this.increment = increment;
        this.block = block;
    }
    @Override
    public CodeObject execute() {
        sync();
        CodeObject obj = init.execute();
        boolean breaking = false;
        while (((CodePrimitive) condition.execute().getInstance()).bool().getData()) {
            CodeRuntime.getRuntime().loopInterrupt();
            for (ASTNode node : block) {
                CodeObject result = node.execute();
                if (CodeRuntime.getRuntime().retrieveContinueInterrupt()) {
                    break;
                }
                if (CodeRuntime.getRuntime().retrieveBreakInterrupt()) {
                    breaking = true;
                    break;
                }
            }
            if (breaking) {
                break;
            }
            increment.execute();
        }
        CodeRuntime.getRuntime().retrieveLoopInterrupt();
        return CodeClass.getNull();
    }
}

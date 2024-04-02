package com.code.parser.nodes;

import com.code.data.CodeBoolean;
import com.code.virtualmachine.*;
import com.code.virtualmachine.CodeClass;
import com.code.virtualmachine.CodeObject;
import com.code.virtualmachine.CodeRuntime;

import java.util.List;

public class IfBlockNode extends ASTNode{
    List<IfNode> conditionals;
    List<ASTNode> elseBlock;

    public IfBlockNode(List<IfNode> conditionals, List<ASTNode> elseBlock) {
        this.conditionals = conditionals;
        this.elseBlock = elseBlock;
    }

    @Override
    public CodeObject execute() {
        sync();
        for (IfNode conditional : conditionals) {
            CodeObject result = conditional.execute();
            if (((CodeBoolean)result.getInstance()).getData()) {
                return CodeClass.getNull();
            }
        }
        CodeRuntime.getRuntime().pushSymbolTable();
        for (ASTNode statement : elseBlock) {
            statement.execute();
        }
        CodeRuntime.getRuntime().popSymbolTable();


        return CodeClass.getNull();
    }
}

package com.code.parser.nodes;

import com.code.data.CodeBoolean;
import com.code.data.CodeString;
import com.code.tokenizer.tokens.Token;
import com.code.virtualmachine.CodeClass;
import com.code.virtualmachine.CodeObject;
import com.code.virtualmachine.CodeRuntime;

public class AssignmentNode extends ASTNode{

    private final ASTNode rValue;
    private final NonTerminalFactorNode lValue;

    public AssignmentNode(NonTerminalFactorNode lValue, ASTNode rValue) {
        this.lValue = lValue;
        this.rValue = rValue;
    }
    @Override
    public CodeObject execute() {
        sync();
        CodeObject obj = rValue.execute();
        CodeObject obj2 = lValue.execute();
        if (obj2.getInstance() instanceof CodeBoolean) {
            if (obj.getInstance() instanceof CodeString str) {
                if (str.equals("TRUE") || str.equals("FALSE")) {
                    var primitive = CodeClass.initializePrimitive("BOOL", str.equals("TRUE"));
                    obj2.assign(primitive);
                    return CodeClass.getNull();
                }
            }
        }
       obj2.assign(obj);
        CodeRuntime.getRuntime().runtimeSymbolTable.assign(lValue.getValue().getTokenAsString(), obj2);
        return obj2;
    }
}

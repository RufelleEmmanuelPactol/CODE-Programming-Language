package com.code.parser.nodes;

import com.code.parser.engine.SymbolTable;
import com.code.tokenizer.tokens.Token;
import com.code.virtualmachine.CodeClass;
import com.code.virtualmachine.CodeObject;
import com.code.virtualmachine.CodeRuntime;

import java.util.ArrayList;
import java.util.List;

public class MethodCallNode extends ASTNode{
    private ASTNode objectInstance;
    private Token methodName;
    private List<ASTNode> parameters;
    private boolean isAttribute = false;


    public MethodCallNode(ASTNode objectInstance, Token methodName, List<ASTNode> parameters, boolean isAttribute) {
        this.objectInstance = objectInstance;
        this.methodName = methodName;
        this.parameters = parameters;
        this.isAttribute = isAttribute;
    }


    @Override
    public CodeObject execute() {
        sync();
        if (isAttribute) {
            return objectInstance.execute().getAttribute(methodName.getTokenAsString());
        }
        CodeObject[] tunedParams = new CodeObject[parameters.size()];
        int index = 0;
        for (var param : parameters) {
            tunedParams[index] = param.execute();
            index +=1;
        }
        CodeObject o = (CodeObject) objectInstance.execute().invokeMethod(methodName.getTokenAsString(), tunedParams);
        return o;
    }
}

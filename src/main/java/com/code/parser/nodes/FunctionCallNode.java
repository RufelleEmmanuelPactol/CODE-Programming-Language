package com.code.parser.nodes;

import com.code.errors.runtime.InvalidArguments;
import com.code.tokenizer.tokens.Token;
import com.code.virtualmachine.CodeClass;
import com.code.virtualmachine.CodeObject;
import com.code.virtualmachine.CodeRuntime;

import java.util.List;

public class FunctionCallNode extends ASTNode{
    protected final List<ASTNode> parameters;
    public FunctionCallNode(Token functionName, List<ASTNode> parameters) {
        this.value = functionName;
        this.parameters = parameters;
    }


    @Override
    public CodeObject execute() {
        var functionName = value;
        CodeBlockNode function = (CodeBlockNode) CodeRuntime.getRuntime().runtimeSymbolTable.searchAssert(functionName.getTokenAsString());
        List<ASTNode> args = function.getArgs();
        CodeRuntime.getRuntime().pushSymbolTable();
        if (args.size() != parameters.size()) {
            throw new InvalidArguments(functionName.getTokenAsString(), args.size(), parameters.size());
        }
        for (int i = 0; i < args.size(); i++) {
            CodeObject obj = parameters.get(i).execute();
            CodeRuntime.getRuntime().runtimeSymbolTable.add(((ParameterNode) args.get(i)).getParamName().getTokenAsString(), obj);
        }
        for (ASTNode statement : function.getStatements()) {
            statement.execute();
        }
        // NO RETURN KEYWORD YET

        CodeObject returnedV = CodeRuntime.getRuntime().runtimeSymbolTable.getReturnedValue();
        CodeRuntime.getRuntime().popSymbolTable();
        return returnedV;
    }
}

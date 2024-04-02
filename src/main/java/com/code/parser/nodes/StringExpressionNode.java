package com.code.parser.nodes;

import com.code.data.CodePrimitive;
import com.code.tokenizer.tokens.Token;
import com.code.virtualmachine.CodeClass;
import com.code.virtualmachine.CodeObject;

public class StringExpressionNode extends UnaryNode{

    public StringExpressionNode(Token value, ASTNode child) {
        super(value, child);
    }

    @Override
    public CodeObject execute() {
        sync();
        CodePrimitive impl = CodePrimitive.fromNativeImplementation(child.execute().getInstance().toString());
        return CodeClass.initializePrimitive("STRING", impl);
    }
}

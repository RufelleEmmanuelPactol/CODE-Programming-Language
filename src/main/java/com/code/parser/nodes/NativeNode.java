package com.code.parser.nodes;

import com.code.tokenizer.tokens.Token;
import com.code.virtualmachine.CodeClass;
import com.code.virtualmachine.CodeObject;
import com.code.virtualmachine.CodeRuntime;
import com.code.virtualmachine.CodeStandardLibrary;

public class NativeNode extends ASTNode{

    public NativeNode(Token libName) {
        this.value = libName;
    }
    @Override
    public CodeObject execute() {
        sync();
        Class<?> c = CodeStandardLibrary.getStandardNative((value).getTokenAsString());
        CodeRuntime.getRuntime().runtimeSymbolTable.registerNativeInterface(value.getTokenAsString(), c);
        return CodeClass.getNull();
    }
}

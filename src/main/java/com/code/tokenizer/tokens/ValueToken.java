package com.code.tokenizer.tokens;


import com.code.data.CodePrimitive;
import com.code.data.CodeString;
import com.code.virtualmachine.CodeClass;
import com.code.virtualmachine.CodeObject;
import com.code.virtualmachine.CodeRuntime;

public class ValueToken extends Token {
    private CodePrimitive internalData;


    public ValueToken(CodePrimitive<?> data) {
        super(data.toString());
        this.internalData = data;
    }

    public CodeObject getValue(){
        Object data = internalData.getData();
        CodeRuntime.getRuntime().runtimeSymbolTable.registerIfNotExists(data);
        CodeClass clazz = CodeRuntime.getRuntime().runtimeSymbolTable.getClassFromSymbols(data);
        return clazz.fromInstance(data);
    }
}

package com.code.tokenizer.tokens;


import com.code.data.*;
import com.code.virtualmachine.CodeClass;
import com.code.virtualmachine.CodeObject;
import com.code.virtualmachine.CodeRuntime;

public class ValueToken extends Token {
    private CodePrimitive internalData;


    public ValueToken(CodePrimitive<?> data) {
        super(data.toString());
        this.internalData = data;
    }

    private CodeClass getFromPrimitive(String name) {
        return CodeRuntime.getRuntime().runtimeSymbolTable.getClassFromSymbols(name);
    }

    private CodeClass evaluateClass(){
        switch(this.internalData) {
            case CodeString s: {
                return getFromPrimitive("STRING");
            } case CodeFloat flat: {
                return getFromPrimitive("FLOAT");
            } case CodeBoolean bool: {
                return getFromPrimitive("BOOL");
            } case CodeInteger i: {
                return getFromPrimitive("INT");
            }
            default:
                throw new IllegalStateException("Unexpected value: " + this.internalData);
        }
    }
    public CodeObject getValue(){
        CodeClass representativeClass = evaluateClass();
        return representativeClass.cloneRef(internalData);
    }
}

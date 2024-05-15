package com.code.errors.runtime;

import com.code.data.CodePrimitive;
import com.code.virtualmachine.CodeObject;
import com.code.virtualmachine.CodeRuntime;

public class TypeError extends VMRuntimeError {

    @SuppressWarnings("rawtypes")
    public TypeError(CodePrimitive type1, CodePrimitive type2, String operator) {
        super("type_error\n" +
                "Type Error: " + type1.getTypeStrRepresenation() + " and " + type2.getTypeStrRepresenation() + " are not compatible when using operator '" + operator + "'. Found in line " + CodeRuntime.getRuntime().GLOBAL_THREAD.getCurrentLineNumber() + ".");
    }

    public TypeError(String type1, String type2, String operator) {
        super("type_error\n" +
                "Type Error: " + type1 + " and " + type2 + " are not compatible when using operator '" + operator + "'. Found in line " + CodeRuntime.getRuntime().GLOBAL_THREAD.getCurrentLineNumber() + ".");
    }

    public TypeError(CodeObject type1, CodeObject type2, String operator) {
        super("type_error\n" +
                "Type Error: " + type1.getCodeClass().getDataTypeName() + " and " + type2.getCodeClass().getDataTypeName() + " are not compatible when using operator '" + operator + "'. Found in line " + CodeRuntime.getRuntime().GLOBAL_THREAD.getCurrentLineNumber() + ".");
    }

    public TypeError(CodeObject type, String operator) {
        super("type_error\n" +
                "Type Error: " + type.getCodeClass().getDataTypeName() + " is not compatible when using operator '" + operator + "'. Found in line " + CodeRuntime.getRuntime().GLOBAL_THREAD.getCurrentLineNumber() + ".");
    }


    @Override
    public String hashcode() {
        return "308";
    }
}

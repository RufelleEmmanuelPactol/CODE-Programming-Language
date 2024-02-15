package com.code.data;

import static com.code.data.CodeBoolean.FALSE;
import static com.code.data.CodeBoolean.TRUE;

public class CodeFloat extends CodePrimitive<Float>{
    public CodeFloat(String data) {
        super(data, Float.parseFloat(data));
    }

    @Override
    public String getTypeStrRepresenation() {
        return "FLOAT";
    }

    @Override
    public String toString() {
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }

    @Override
    public CodePrimitive add(CodePrimitive other) {
        return null;
    }

    @Override
    public CodePrimitive subtract(CodePrimitive other) {
        return null;
    }

    @Override
    public CodePrimitive multiply(CodePrimitive other) {
        return null;
    }

    @Override
    public CodePrimitive divide(CodePrimitive other) {
        return null;
    }

    @Override
    public CodePrimitive modulo(CodePrimitive other) {
        return null;
    }

    @Override
    public CodePrimitive increment(CodePrimitive other) {
        return null;
    }

    @Override
    public CodePrimitive decrement(CodePrimitive other) {
        return null;
    }

    @Override
    public CodeBoolean and(CodePrimitive other) {
        return null;
    }

    @Override
    public CodeBoolean or(CodePrimitive other) {
        return null;
    }

    @Override
    public CodeBoolean not() {
        return null;
    }

    @Override
    public CodeBoolean bool() {
        return data == 0 ? FALSE : TRUE;
    }
}

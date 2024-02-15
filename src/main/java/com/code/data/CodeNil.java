package com.code.data;

public class CodeNil extends CodePrimitive<Object>{

    public static final CodeNil nil = new CodeNil("nil");

    protected CodeNil(String tokenRepresentation) {
        super(tokenRepresentation, null);
    }

    @Override
    public String getTypeStrRepresenation() {
        return "NIL";
    }

    @Override
    public String toString() {
        return "NIL";
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
        return CodeBoolean.FALSE;
    }
}

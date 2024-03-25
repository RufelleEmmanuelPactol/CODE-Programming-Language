package com.code.data;

import com.code.errors.runtime.TypeError;

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
    public CodePrimitive add(CodePrimitive other) {
        throw new TypeError(this, other, "+");
    }

    @Override
    public CodePrimitive subtract(CodePrimitive other) {
        throw new TypeError(this, other, "-");
    }

    @Override
    public CodePrimitive multiply(CodePrimitive other) {
        throw new TypeError(this, other, "*");
    }

    @Override
    public CodePrimitive divide(CodePrimitive other) {
        throw new TypeError(this, other, "/");
    }

    @Override
    public CodePrimitive modulo(CodePrimitive other) {
        throw new TypeError(this, other, "%");
    }



    @Override
    public CodeBoolean and(CodePrimitive other) {
        throw new TypeError(this, other, "AND");
    }

    @Override
    public CodeBoolean or(CodePrimitive other) {
        throw new TypeError(this, other, "OR");
    }

    @Override
    public CodeBoolean not() {
        return CodeBoolean.TRUE;
    }

    @Override
    public CodeBoolean bool() {
        return CodeBoolean.FALSE;
    }

    @Override
    public CodeBoolean lessThan(CodePrimitive other) {
        throw new TypeError(this, other, "<");
    }

    @Override
    public CodeBoolean greaterThan(CodePrimitive other) {
        throw new TypeError(this, other, ">");
    }

    @Override
    public CodeBoolean lessThanEqualTo(CodePrimitive other) {
        throw new TypeError(this, other, "<=");
    }

    @Override
    public CodeBoolean greaterThanEqualTo(CodePrimitive other) {
        throw new TypeError(this, other, ">=");
    }

    @Override
    public CodeBoolean equalTo(CodePrimitive other) {
        return other instanceof CodeNil ? CodeBoolean.TRUE : CodeBoolean.FALSE;
    }
}

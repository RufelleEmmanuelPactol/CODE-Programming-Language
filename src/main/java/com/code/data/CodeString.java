package com.code.data;

import com.code.errors.runtime.TypeError;

import java.util.HashSet;

public class CodeString extends CodePrimitive<String>{
    public CodeString(String data) {
        super(data, data);

        this.tokenRepresentation = data;

    }

    public CodeString(){
        super("", "");
    }

    @Override
    public String getTypeStrRepresenation() {
        return "STRING";
    }

    @Override
    public String toString() {
        return tokenRepresentation;
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public CodePrimitive add(CodePrimitive other) {
            if (other instanceof CodeString) {
                return new CodeString(this.data + other.data);
            }
        throw new TypeError(this, other, "&");
    }

    @Override
    @SuppressWarnings("rawtypes")
    public CodePrimitive subtract(CodePrimitive other) {
        throw new TypeError(this, other, "-");
    }

    @Override
    @SuppressWarnings("rawtypes")
    public CodePrimitive multiply(CodePrimitive other) {
        throw new TypeError(this, other, "*");
    }

    @Override
    @SuppressWarnings("rawtypes")
    public CodePrimitive divide(CodePrimitive other) {
        throw new TypeError(this, other, "/");
    }

    @Override
    @SuppressWarnings("rawtypes")
    public CodePrimitive modulo(CodePrimitive other) {
        throw new TypeError(this, other, "%");
    }


    @Override
    @SuppressWarnings("rawtypes")
    public CodeBoolean and(CodePrimitive other) {
        return bool().and(other.bool());
    }

    @Override
    @SuppressWarnings("rawtypes")
    public CodeBoolean or(CodePrimitive other) {
        return bool().or(other.bool());
    }

    @Override
    public CodeBoolean not() {
        return bool().not();
    }

    @Override
    public CodeBoolean bool() {
        return this.data.isEmpty() ? CodeBoolean.FALSE : CodeBoolean.TRUE;
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
        return other.getData().equals(this.data) ? CodeBoolean.TRUE : CodeBoolean.FALSE;
    }
}

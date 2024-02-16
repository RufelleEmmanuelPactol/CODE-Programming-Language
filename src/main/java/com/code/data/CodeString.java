package com.code.data;

import com.code.exceptions.runtime.TypeError;

import java.util.HashSet;

public class CodeString extends CodePrimitive<String>{
    public CodeString(String data) {
        super(data, data);
        this.allowedClasses = new HashSet<>();
        this.allowedClasses.add(CodeString.class);
        // update the data to remove the escape characters
        // escape characters are basically /, and retain the character after it
        // also remove the starting and trailing ""
        this.data = data.substring(1, data.length() - 1).replaceAll("\\\\(.)", "$1");
        this.tokenRepresentation = data;

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
        if (allowedClasses.contains(other.getClass())){
            if (other instanceof CodeString) {
                return new CodeString(this.data + (String)other.data);
            }
        }
        throw new TypeError(this, other, "+");
    }

    @Override
    @SuppressWarnings("rawtypes")
    public CodePrimitive subtract(CodePrimitive other) {
        return null;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public CodePrimitive multiply(CodePrimitive other) {
        return null;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public CodePrimitive divide(CodePrimitive other) {
        return null;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public CodePrimitive modulo(CodePrimitive other) {
        return null;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public CodePrimitive increment(CodePrimitive other) {
        return null;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public CodePrimitive decrement(CodePrimitive other) {
        return null;
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
        if (this.data.isEmpty()) return CodeBoolean.TRUE;
        return CodeBoolean.FALSE;
    }

    @Override
    public CodeBoolean bool() {
        return this.not();
    }
}

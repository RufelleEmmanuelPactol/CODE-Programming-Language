package com.code.data;

import com.code.exceptions.runtime.TypeError;

public class CodeInteger extends CodePrimitive<Integer>{

    public CodeInteger(String data) {
        super(data, Integer.getInteger(data));
        this.allowedClasses.add(CodeInteger.class);
        this.allowedClasses.add(CodeFloat.class);
    }

    @Override
    public String getTypeStrRepresenation() {
        return "INTEGER";
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
    public CodePrimitive add(CodePrimitive other) {
        if (allowedClasses.contains(other.getClass())){
            if (other instanceof CodeInteger) {
                return new CodeInteger(Integer.toString(this.data + (Integer)other.data));
            } else if (other instanceof CodeFloat) {
                return new CodeFloat(Float.toString(this.data + (Float)other.data));
            }
        }
        throw new TypeError(this, other, "+");
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
        return data == 0 ? CodeBoolean.FALSE : CodeBoolean.TRUE;
    }
}

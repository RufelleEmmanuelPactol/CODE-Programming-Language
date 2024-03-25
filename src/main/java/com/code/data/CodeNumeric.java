package com.code.data;

import com.code.errors.runtime.TypeError;

import static com.code.data.CodeBoolean.FALSE;
import static com.code.data.CodeBoolean.TRUE;

public abstract class CodeNumeric<T> extends CodePrimitive<T> {
    public CodeNumeric(String tokenRepresentation, T data) {
        super(tokenRepresentation, data);
    }



    @Override
    public CodeBoolean equalTo(CodePrimitive other) {
        if (other instanceof CodeNumeric<?>) {
            return this.data == ((CodeNumeric<?>) other).getData() ? TRUE : FALSE;
        } throw new TypeError(this, other, "==");
    }


    @Override
    public CodeBoolean and(CodePrimitive other) {
        return other.bool().and(this.bool());
    }

    @Override
    public CodeBoolean or(CodePrimitive other) {
        return other.bool().or(this.bool());
    }

    @Override
    public CodeBoolean not() {
        return this.bool().not();
    }
}

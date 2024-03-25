package com.code.data;

import com.code.errors.runtime.TypeError;

public class CodeInteger extends CodeNumeric<Integer>{

    public CodeInteger(String data) {
        super(data, Integer.parseInt(data));
    }

    public CodeInteger(){
        super("0", 0);
    }

    public CodeInteger(Integer data) {
        super(String.valueOf(data), data);
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
    public CodePrimitive add(CodePrimitive other) {
        if (other instanceof CodeNumeric<?>) {
            if (other instanceof CodeInteger i) {
                return new CodeInteger(this.data + i.data);
            } else if (other instanceof CodeFloat f) {
                return new CodeFloat(this.data + f.data);
            }
        } throw new TypeError(this, other, "+");
    }

    @Override
    public CodePrimitive subtract(CodePrimitive other) {
        if (other instanceof CodeNumeric<?>) {
            if (other instanceof CodeInteger i) {
                return new CodeInteger(this.data - i.data);
            } else if (other instanceof CodeFloat f) {
                return new CodeFloat(this.data - f.data);
            }
        } throw new TypeError(this, other, "-");
    }

    @Override
    public CodePrimitive multiply(CodePrimitive other) {
        if (other instanceof CodeNumeric<?>) {
            if (other instanceof CodeInteger i) {
                return new CodeInteger(this.data * i.data);
            } else if (other instanceof CodeFloat f) {
                return new CodeFloat(this.data * f.data);
            }
        } throw new TypeError(this, other, "*");
    }

    @Override
    public CodePrimitive divide(CodePrimitive other) {
        if (other instanceof CodeNumeric<?>) {
            if (other instanceof CodeInteger i) {
                return new CodeInteger(this.data / i.data);
            } else if (other instanceof CodeFloat f) {
                return new CodeFloat(this.data / f.data);
            }
        } throw new TypeError(this, other, "/");
    }


    @Override
    public CodePrimitive modulo(CodePrimitive other) {
        if (other instanceof CodeNumeric<?>) {
            if (other instanceof CodeInteger i) {
                return new CodeInteger(this.data % i.data);
            }
        } throw new TypeError(this, other, "%");
    }


    @Override
    public CodeBoolean bool() {
        return data == 0 ? CodeBoolean.FALSE : CodeBoolean.TRUE;
    }

    @Override
    public CodeBoolean lessThan(CodePrimitive other) {
        if (other instanceof CodeNumeric<?>) {
            if (other instanceof CodeInteger i) {
                return this.data < i.data ? CodeBoolean.TRUE : CodeBoolean.FALSE;
            } else if (other instanceof CodeFloat f) {
                return this.data < f.data ? CodeBoolean.TRUE : CodeBoolean.FALSE;
            }
        } throw new TypeError(this, other, "<");
    }

    @Override
    public CodeBoolean greaterThan(CodePrimitive other) {
        if (other instanceof CodeNumeric<?>) {
            if (other instanceof CodeInteger i) {
                return this.data > i.data ? CodeBoolean.TRUE : CodeBoolean.FALSE;
            } else if (other instanceof CodeFloat f) {
                return this.data > f.data ? CodeBoolean.TRUE : CodeBoolean.FALSE;
            }
        } throw new TypeError(this, other, ">");
    }

    @Override
    public CodeBoolean lessThanEqualTo(CodePrimitive other) {
        if (other instanceof CodeNumeric<?>) {
            if (other instanceof CodeInteger i) {
                return this.data <= i.data ? CodeBoolean.TRUE : CodeBoolean.FALSE;
            } else if (other instanceof CodeFloat f) {
                return this.data <= f.data ? CodeBoolean.TRUE : CodeBoolean.FALSE;
            }
        } throw new TypeError(this, other, "<=");
    }

    @Override
    public CodeBoolean greaterThanEqualTo(CodePrimitive other) {
        if (other instanceof CodeNumeric<?>) {
            if (other instanceof CodeInteger i) {
                return this.data >= i.data ? CodeBoolean.TRUE : CodeBoolean.FALSE;
            } else if (other instanceof CodeFloat f) {
                return this.data >= f.data ? CodeBoolean.TRUE : CodeBoolean.FALSE;
            }
        } throw new TypeError(this, other, ">=");
    }


}

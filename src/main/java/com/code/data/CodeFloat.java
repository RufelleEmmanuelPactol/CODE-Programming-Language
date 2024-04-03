package com.code.data;

import com.code.errors.runtime.TypeError;

import java.net.Socket;

public class CodeFloat extends CodeNumeric<Double> {
    public CodeFloat(String data) {
        super(data, Double.parseDouble(data));
    }

    public CodeFloat() {
        super("0", 0.0);
    }

    public CodeFloat(double data) {
        super(Double.toString(data), data);
    }

    @Override
    public String getTypeStrRepresenation() {
        return "FLOAT";
    }

    @Override
    public String toString() {
        return tokenRepresentation;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CodeFloat) {
            CodeFloat other = (CodeFloat) obj;
            return this.data.equals(other.data);
        }
        return false;
    }

    @Override
    public CodePrimitive add(CodePrimitive other) {
        if (other instanceof CodeNumeric<?>) {
            if (other instanceof CodeInteger i) {
                return new CodeFloat(this.data + i.data);
            } else if (other instanceof CodeFloat f) {
                return new CodeFloat(this.data + f.data);
            }
        }
        throw new TypeError(this, other, "+");
    }

    @Override
    public CodePrimitive subtract(CodePrimitive other) {
        if (other instanceof CodeNumeric<?>) {
            if (other instanceof CodeInteger i) {
                return new CodeFloat(this.data - i.data);
            } else if (other instanceof CodeFloat f) {
                return new CodeFloat(this.data - f.data);
            }
        }
        throw new TypeError(this, other, "-");
    }

    @Override
    public CodePrimitive multiply(CodePrimitive other) {
        if (other instanceof CodeNumeric<?>) {
            if (other instanceof CodeInteger i) {
                return new CodeFloat(this.data * i.data);
            } else if (other instanceof CodeFloat f) {
                return new CodeFloat(this.data * f.data);
            }
        }
        throw new TypeError(this, other, "*");
    }

    @Override
    public CodePrimitive divide(CodePrimitive other) {
        if (other instanceof CodeNumeric<?>) {
            if (other instanceof CodeInteger i) {
                return new CodeFloat(this.data / i.data);
            } else if (other instanceof CodeFloat f) {
                return new CodeFloat(this.data / f.data);
            }
        }
        throw new TypeError(this, other, "/");
    }

    @Override
    public CodePrimitive modulo(CodePrimitive other) {
        if (other instanceof CodeNumeric<?>) {
            if (other instanceof CodeInteger i) {
                return new CodeFloat(this.data % i.data);
            } else if (other instanceof CodeFloat f) {
                return new CodeFloat(this.data % f.data);
            }
        }
        throw new TypeError(this, other, "%");
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
        }
        throw new TypeError(this, other, "<");
    }

    @Override
    public CodeBoolean greaterThan(CodePrimitive other) {
        if (other instanceof CodeNumeric<?>) {
            if (other instanceof CodeInteger i) {
                return this.data > i.data ? CodeBoolean.TRUE : CodeBoolean.FALSE;
            } else if (other instanceof CodeFloat f) {
                return this.data > f.data ? CodeBoolean.TRUE : CodeBoolean.FALSE;
            }
        }
        throw new TypeError(this, other, ">");
    }

    @Override
    public CodeBoolean lessThanEqualTo(CodePrimitive other) {
        if (other instanceof CodeNumeric<?>) {
            if (other instanceof CodeInteger i) {
                return this.data <= i.data ? CodeBoolean.TRUE : CodeBoolean.FALSE;
            } else if (other instanceof CodeFloat f) {
                return this.data <= f.data ? CodeBoolean.TRUE : CodeBoolean.FALSE;
            }
        }
        throw new TypeError(this, other, "<=");
    }

    @Override
    public CodeBoolean greaterThanEqualTo(CodePrimitive other) {
        if (other instanceof CodeNumeric<?>) {
            if (other instanceof CodeInteger i) {
                return this.data >= i.data ? CodeBoolean.TRUE : CodeBoolean.FALSE;
            } else if (other instanceof CodeFloat f) {
                return this.data >= f.data ? CodeBoolean.TRUE : CodeBoolean.FALSE;
            }
        }
        throw new TypeError(this, other, ">=");
    }
}

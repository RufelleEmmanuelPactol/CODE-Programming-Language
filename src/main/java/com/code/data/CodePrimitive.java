package com.code.data;

import com.code.errors.compile.PrimitiveInitializationError;
import com.code.errors.runtime.TypeError;

import java.util.HashSet;
import java.util.Set;

/***
 * The primitive data type of the CODE programming language.
 * This class is the base class for all primitive data types in CODE.
 * It provides the basic operations that can be performed on all primitive data types.
 * Throws @TypeError if the operation is not supported for the given data type.
 */


public abstract class CodePrimitive<T> {
    protected String tokenRepresentation;
    protected T data;

    public abstract String getTypeStrRepresenation();

    public T getData() {
        return data;
    }



    public CodePrimitive (String tokenRepresentation, T data) {
        this.tokenRepresentation = tokenRepresentation;
        this.data = data;
    }



    public abstract String toString();

    public abstract CodePrimitive add(CodePrimitive other);
    public abstract CodePrimitive subtract(CodePrimitive other);
    public abstract CodePrimitive multiply(CodePrimitive other);
    public abstract CodePrimitive divide(CodePrimitive other);
    public abstract CodePrimitive modulo(CodePrimitive other);
    public abstract CodeBoolean and(CodePrimitive other);
    public abstract CodeBoolean or(CodePrimitive other);
    public abstract CodeBoolean not();
    public abstract CodeBoolean bool();

    public abstract CodeBoolean lessThan(CodePrimitive other);
    public abstract CodeBoolean greaterThan(CodePrimitive other);
    public abstract CodeBoolean lessThanEqualTo(CodePrimitive other);
    public abstract CodeBoolean greaterThanEqualTo(CodePrimitive other);
    public abstract CodeBoolean equalTo(CodePrimitive other);


    /**
     * Converts a native implementation of a primitive data type to a CODE primitive data type.
     * Can only convert Integer, Double, String, and Boolean.
     */
    public static final CodePrimitive fromNativeImplementation(Object o) {
        if (o instanceof Integer i) {
            return new CodeInteger(i);
        } else if (o instanceof Double d) {
            return new CodeFloat(d);
        } else if (o instanceof String s) {
            return new CodeString(s);
        } else if (o instanceof Boolean b) {
            return b ? CodeBoolean.TRUE : CodeBoolean.FALSE;
        } throw new PrimitiveInitializationError(o.getClass().getSimpleName());
    }

    public CodePrimitive notEqualTo(CodePrimitive other) {
        return this.equalTo(other).not();
    }

    /**
     * Replaces the internal data.
     * @param o
     */
    public void replaceInside(Object o) {
        if (this.data.getClass().isInstance(o)) {
            this.data = (T)o;
            this.tokenRepresentation = data.toString();
        } else {
            throw new TypeError(this.getClass().getSimpleName(), o.getClass().getSimpleName(), "__internal_replace_inside__");
        }
    }

}

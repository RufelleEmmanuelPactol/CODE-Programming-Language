package com.code.data;

/***
 * The primitive data type of the CODE programming language.
 * This class is the base class for all primitive data types in CODE.
 * It provides the basic operations that can be performed on all primitive data types.
 * Throws @TypeError if the operation is not supported for the given data type.
 */

public abstract class CodePrimitive {

    private Runtime runtime;
    private String value;


    public CodePrimitive (Runtime runtime, String value) {
        this.runtime = runtime;
        this.value = value;
    }


    public abstract String toString();
    public abstract boolean equals(Object obj);
    public abstract CodePrimitive add(CodePrimitive other);
    public abstract CodePrimitive subtract(CodePrimitive other);
    public abstract CodePrimitive multiply(CodePrimitive other);
    public abstract CodePrimitive divide(CodePrimitive other);
    public abstract CodePrimitive modulo(CodePrimitive other);
    public abstract CodePrimitive increment(CodePrimitive other);
    public abstract CodePrimitive decrement(CodePrimitive other);
    public abstract CodePrimitive and(CodePrimitive other);
    public abstract CodePrimitive or(CodePrimitive other);
    public abstract CodePrimitive not();

}

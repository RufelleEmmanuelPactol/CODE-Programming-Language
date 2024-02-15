package com.code.data;

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



    public CodePrimitive (String tokenRepresentation, T data) {
        this.tokenRepresentation = tokenRepresentation;
        this.allowedClasses = new HashSet<>();
        this.data = data;
    }

    protected Set<Class<? extends CodePrimitive>> allowedClasses;


    public abstract String toString();
    public abstract boolean equals(Object obj);
    public abstract CodePrimitive add(CodePrimitive other);
    public abstract CodePrimitive subtract(CodePrimitive other);
    public abstract CodePrimitive multiply(CodePrimitive other);
    public abstract CodePrimitive divide(CodePrimitive other);
    public abstract CodePrimitive modulo(CodePrimitive other);
    public abstract CodePrimitive increment(CodePrimitive other);
    public abstract CodePrimitive decrement(CodePrimitive other);
    public abstract CodeBoolean and(CodePrimitive other);
    public abstract CodeBoolean or(CodePrimitive other);
    public abstract CodeBoolean not();
    public abstract CodeBoolean bool();

}

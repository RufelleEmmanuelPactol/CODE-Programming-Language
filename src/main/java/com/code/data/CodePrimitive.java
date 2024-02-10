package com.code.data;

public abstract class CodePrimitive {
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

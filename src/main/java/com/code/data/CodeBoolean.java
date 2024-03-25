package com.code.data;

public class CodeBoolean extends CodePrimitive<Boolean>{

    private CodeBoolean(String tokenRepresentation, Boolean data) {
        super(tokenRepresentation, data);
    }

    public static final CodeBoolean TRUE = new CodeBoolean("TRUE", true);
    public static final CodeBoolean FALSE = new CodeBoolean("FALSE", false);

    public CodeBoolean (Boolean b) {
        super(b ? "TRUE" : "FALSE", b ? true : false);
    }

    public CodeBoolean() {
        super("FALSE", false);
    }

    @Override
    public String getTypeStrRepresenation() {
        return null;
    }

    @Override
    public String toString() {
        return this.data ? "TRUE" : "FALSE";
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }

    @Override
    public CodePrimitive add(CodePrimitive other) {
        return null;
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
    @SuppressWarnings("rawtypes")
    public CodeBoolean and(CodePrimitive other) {
        return this == other.bool() ? this : FALSE;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public CodeBoolean or(CodePrimitive other) {
        return other.not().not() == this ? TRUE : FALSE;
    }

    @Override
    public CodeBoolean not() {
        return this == TRUE ? FALSE : TRUE;
    }

    @Override
    public CodeBoolean bool() {
        return this;
    }

    @Override
    public CodeBoolean lessThan(CodePrimitive other) {
        return null;
    }

    @Override
    public CodeBoolean greaterThan(CodePrimitive other) {
        return null;
    }

    @Override
    public CodeBoolean lessThanEqualTo(CodePrimitive other) {
        return null;
    }

    @Override
    public CodeBoolean greaterThanEqualTo(CodePrimitive other) {
        return null;
    }

    @Override
    public CodeBoolean equalTo(CodePrimitive other) {
        return null;
    }
}

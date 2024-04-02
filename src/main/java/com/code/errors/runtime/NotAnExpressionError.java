package com.code.errors.runtime;

public class NotAnExpressionError extends VMRuntimeError{
    public NotAnExpressionError(String message) {
        super("The expression `" + message + "` cannot be evaluated as an expression.");
    }

    @Override
    public String hashcode() {
        return "310";
    }
}

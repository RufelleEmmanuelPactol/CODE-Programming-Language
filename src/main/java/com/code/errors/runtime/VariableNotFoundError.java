package com.code.errors.runtime;

public class VariableNotFoundError extends VMRuntimeError{
    public VariableNotFoundError(String variableName) {
        super("Variable `" + variableName + "` not found in the current scope.");
    }

    @Override
    public String hashcode() {
        return "309";
    }
}

package com.code.errors.runtime;

public class ClassDoesNotExistError extends VMRuntimeError{
    public ClassDoesNotExistError(String message) {
        super("[Class Does Not Exist Error]: Class " + message + " does not exist in the current scope.");
    }

    @Override
    public String hashcode() {
        return "302";
    }
}

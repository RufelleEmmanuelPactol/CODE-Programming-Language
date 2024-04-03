package com.code.errors.runtime;

public class FieldDoesNotExistError extends VMRuntimeError{
    public FieldDoesNotExistError(String message, String className, String explicit) {
        super("[Field Does Not Exist Error] " + "The field \"" +  message + "\" does not exist in the class \"" + className + "\".\n\t\t\tExplicit Error Message: " + explicit);
    }

    @Override
    public String hashcode() {
        return "303";
    }
}

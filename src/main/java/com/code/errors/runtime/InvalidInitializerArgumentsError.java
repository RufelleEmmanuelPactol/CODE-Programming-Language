package com.code.errors.runtime;

public class InvalidInitializerArgumentsError extends VMRuntimeError{
    public InvalidInitializerArgumentsError(String message) {
        super("[Invalid Initializer Arguments]" +message);
    }
}

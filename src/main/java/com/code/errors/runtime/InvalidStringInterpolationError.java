package com.code.errors.runtime;

public class InvalidStringInterpolationError extends VMRuntimeError{
    public InvalidStringInterpolationError(String message) {
        super("[Invalid String Interpolation]: " + message);
    }
}

package com.code.errors.runtime;

import com.code.errors.CodeError;

public class InvalidArguments extends CodeError {
    public InvalidArguments(String functionName, int expected, int actual) {
        super("[Invalid Argument Count]: Invalid number of arguments for function " + functionName + ". Expected " + expected + " but got " + actual + ".");
    }
}

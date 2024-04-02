package com.code.errors.compile;

import com.code.errors.runtime.VMRuntimeError;

public class CompileBugError extends VMRuntimeError {
    public CompileBugError(String message) {
        super("[Compile Error Bug]: There is a bug in the compiler, specific message: " + message);
    }

    @Override
    public String hashcode() {
        return "100";
    }


}

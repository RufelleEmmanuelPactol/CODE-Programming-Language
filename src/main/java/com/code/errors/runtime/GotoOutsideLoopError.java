package com.code.errors.runtime;

public class GotoOutsideLoopError extends VMRuntimeError{
    public GotoOutsideLoopError() {
        super("[Invalid Goto Error]: Cannot use `goto` statements such as `continue` and `break` outside of a loop.");
    }

    @Override
    public String hashcode() {
        return "311";
    }
}

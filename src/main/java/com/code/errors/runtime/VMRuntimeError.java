package com.code.errors.runtime;

import com.code.errors.CodeError;

public abstract class VMRuntimeError extends CodeError {

    public VMRuntimeError(String message) {
        super("runtime_type: com.code.error.vm_runtime.\n\t\tExplicit Error Message: " +  message + "\n\t\tError not caught. Virtual Machine will terminate.");
    }
}

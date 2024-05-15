package com.code.errors.runtime;

import com.code.errors.CodeError;

public abstract class VMRuntimeError extends CodeError {

    public final String originalMessage;

    public VMRuntimeError(String message) {
        super("runtime_type: com.code.error.vm_runtime.\nExplicit Error Message: " +  message + "\nError not caught. Virtual Machine will terminate.");
        originalMessage = message;
    }
}

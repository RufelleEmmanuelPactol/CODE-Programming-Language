package com.code.exceptions.runtime;

import com.code.exceptions.CodeError;

public abstract class VMRuntimeError extends CodeError {

    public VMRuntimeError(String message) {
        super("runtime_type: com.code.error.vm_runtime." +  message + "\n\tError not caught. Virtual Machine will terminate.");
    }
}

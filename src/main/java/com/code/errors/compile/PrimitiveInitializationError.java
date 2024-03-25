package com.code.errors.compile;

import com.code.errors.runtime.VMRuntimeError;

public class PrimitiveInitializationError extends VMRuntimeError {
    public PrimitiveInitializationError(String type) {
        super("[Primitive Initialization Error]: Cannot instantiate type `" + type + "` as a primitive type." );
    }
}

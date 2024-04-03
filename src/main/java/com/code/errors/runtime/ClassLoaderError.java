package com.code.errors.runtime;

public class ClassLoaderError extends VMRuntimeError{
    public ClassLoaderError(String message) {
        super("[Class Loader Error]: Cannot load class `" + message + "` from the  `com.code.lang.native` interface.");
    }

    @Override
    public String hashcode() {
        return "312";
    }
}

package com.code.errors.compile;

import com.code.errors.CodeError;

public class FileNotFoundError extends CodeError {
    public FileNotFoundError(String message) {
        super(message);
    }
}

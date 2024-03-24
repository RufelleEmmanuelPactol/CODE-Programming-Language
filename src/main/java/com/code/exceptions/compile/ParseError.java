package com.code.exceptions.compile;

import com.code.exceptions.CodeError;

public class ParseError extends CodeError {
    public ParseError(String message) {
        super("[Parse Error]: " + message);
    }
}

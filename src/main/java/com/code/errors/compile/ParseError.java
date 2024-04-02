package com.code.errors.compile;

import com.code.errors.CodeError;

public class ParseError extends CodeError {
    public ParseError(String message) {
        super("[Parse Error]: " + message);
    }

    @Override
    public String hashcode() {
        return "202";
    }
}

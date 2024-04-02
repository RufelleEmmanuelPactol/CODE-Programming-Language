package com.code.errors.compile;

import com.code.errors.CodeError;

public class NoStartError extends CodeError {
    public NoStartError() {
        super("[No Entry Point Error]: The code has no start point.\n\t\tThe function `CODE` should be defined somewhere in the code. It should be defined as `BEGIN CODE`.");
    }

    @Override
    public String hashcode() {
        return "201";
    }
}

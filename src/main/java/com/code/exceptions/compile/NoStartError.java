package com.code.exceptions.compile;

import com.code.exceptions.CodeError;

public class NoStartError extends CodeError {
    public NoStartError() {
        super("The code has no start point. `BEGIN CODE` should be defined somewhere in the code. ");
    }
}

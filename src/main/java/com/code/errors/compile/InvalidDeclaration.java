package com.code.errors.compile;

import com.code.errors.runtime.VMRuntimeError;

public class InvalidDeclaration extends VMRuntimeError {
public InvalidDeclaration() {
        super("Your declaration of variable is invalid. Declarations should occur only during after `BEGIN` block.");
    }

    @Override
    public String hashcode() {
        return "100";
    }
}

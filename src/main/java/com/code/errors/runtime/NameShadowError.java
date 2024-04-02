package com.code.errors.runtime;

import com.code.virtualmachine.CodeRuntime;

public class NameShadowError extends VMRuntimeError{
    public NameShadowError(String name) {
        super("[Name Shadow Error] Name " + name + " is already defined in the current scope as a `" + CodeRuntime.getRuntime().runtimeSymbolTable.search(name).toString() + "` type." +
                "\n\t\tNote: shadow errors containing basic primitive types include FUNCTION, INT, CHAR, etc. Types of code.lang.classtype are native types interfaced with the JVM.");
    }

    @Override
    public String hashcode() {
        return "307";
    }
}

package com.code.tokenizer.tokens;

import com.code.virtualmachine.CodeRuntime;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public abstract class Token {

    public final int LINE;






    public Token(String representation) {
        this.tokenAsString = representation;
        LINE = CodeRuntime.getRuntime().GLOBAL_THREAD.getCurrentLineNumber();
    }

    public String getTokenAsString() {
        return tokenAsString;
    }
    private final String tokenAsString;

    @Override
    public boolean equals(Object o) {
        return this.tokenAsString.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tokenAsString);
    }

    public String toString() {
        return this.getClass().getCanonicalName() + "(" + getTokenAsString() + ")";
    }

}

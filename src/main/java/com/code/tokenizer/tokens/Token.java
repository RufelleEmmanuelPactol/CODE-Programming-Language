package com.code.tokenizer.tokens;

import java.util.HashSet;
import java.util.Set;

public abstract class Token {
    protected static Set<String> members = new HashSet<>();

    public static boolean isMember(String keyword) {
        return members.contains(keyword);
    }

    public static Token createMemberIfValid(String keyword) {
        return null;
    }

    public Token(String representation) {
        this.tokenAsString = representation;
    }

    public String getTokenAsString() {
        return tokenAsString;
    }
    private final String tokenAsString;


    public String toString() {
        return this.getClass().getCanonicalName() + "(" + getTokenAsString() + ")";
    }

}

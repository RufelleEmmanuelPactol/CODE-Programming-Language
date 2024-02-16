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

    public Token() {}

    public String toString() {
        return this.getClass().getCanonicalName();
    }

}

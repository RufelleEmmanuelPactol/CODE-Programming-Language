package com.code.tokenizer.tokens;

import com.code.data.CodeString;

public class FlushToken extends ValueToken{
    public FlushToken(String representation) {
        super(new CodeString("\n"));

    }
}

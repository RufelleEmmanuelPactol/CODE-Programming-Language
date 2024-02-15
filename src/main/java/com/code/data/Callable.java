package com.code.data;

import java.util.HashMap;

public interface Callable {

    @SuppressWarnings("rawtypes")
    public CodePrimitive call(HashMap<String, CodePrimitive> args);
}

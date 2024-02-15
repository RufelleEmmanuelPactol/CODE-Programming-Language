package com.code.data;

public abstract class CodeObject<T> extends CodePrimitive<T>{

    public CodeObject(String tokenRepresentation, T data) {
        super(tokenRepresentation, data);
    }


}

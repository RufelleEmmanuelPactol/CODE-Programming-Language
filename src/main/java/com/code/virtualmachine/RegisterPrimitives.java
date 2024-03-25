package com.code.virtualmachine;

import com.code.data.CodeBoolean;
import com.code.data.CodeFloat;
import com.code.data.CodeInteger;
import com.code.data.CodeString;

public class RegisterPrimitives {



    public static void registerPrimitives() {
        registerPrimitive("INT", CodeInteger.class);
        registerPrimitive("FLOAT", CodeFloat.class);
        registerPrimitive("STRING", CodeString.class);
        registerPrimitive("BOOL", CodeBoolean.class);
        registerPrimitive("NULL", NullType.class);
    }

    private static void registerPrimitive(String name, Class<?> value) {
       new CodeClass(name, value);
    }
}

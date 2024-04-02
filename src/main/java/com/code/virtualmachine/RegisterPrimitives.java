package com.code.virtualmachine;

import com.code.data.*;

public class RegisterPrimitives {



    public static void registerPrimitives() {
        registerPrimitive("INT", CodeInteger.class);
        registerPrimitive("FLOAT", CodeFloat.class);
        registerPrimitive("STRING", CodeString.class);
        registerPrimitive("BOOL", CodeBoolean.class);
        registerPrimitive("NULL", NullType.class);
        registerPrimitive("CHAR", CodeChar.class);
    }

    private static void registerPrimitive(String name, Class<?> value) {
       new CodeClass(name, value);
    }
}

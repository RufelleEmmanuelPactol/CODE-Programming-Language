package com.code.virtualmachine;

import com.code.errors.runtime.FieldDoesNotExistError;
import com.code.errors.runtime.TypeError;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * The CodeObject class is a representation of an object in the CODE. It is used to store information about the object
 * and to perform operations on the object.
 */
public class CodeObject {
    private Object instance;
    private String name;
    private final CodeClass codeClass;

    public CodeObject assign(CodeObject value) {
        if (codeClass != value.codeClass) throw new TypeError(value, "assignment");
        instance = value.instance;
        return this;
    }

    // assign from raw value
    public CodeObject assign(Object value) {
        CodeClass clazz = (CodeClass) CodeRuntime.getRuntime().runtimeSymbolTable.searchAssert(value.getClass().getSimpleName());
        CodeObject obj = clazz.fromInstance(value);
        if (!codeClass.getClass().isInstance(obj.codeClass)) {
            throw new TypeError(obj, this, "assignment");
        }
        instance = value;
        return this;
    }


    protected CodeObject(Object instance, CodeClass codeClass) {
        this.instance = instance;
        assert codeClass.getDataType().isInstance(instance);
        this.codeClass = codeClass;
    }

    public CodeObject getAttribute(String name) {
        try {
            Object o = instance.getClass().getField(name);
            CodeRuntime.getRuntime().runtimeSymbolTable.registerIfNotExists(o);
            CodeClass clazz = CodeRuntime.getRuntime().runtimeSymbolTable.getClassFromSymbols(o);
            return clazz.fromInstance(o);
        } catch (NoSuchFieldException e) {
            throw new FieldDoesNotExistError(name, codeClass.getDataTypeName());
        }
    }

    private static CodeObject nullInstance = null;

    public CodeClass getCodeClass() {
        return codeClass;
    }

    public CodeObject getNull(){
        if (nullInstance == null) {
            CodeClass c = CodeRuntime.getRuntime().runtimeSymbolTable.getClassFromSymbols("NULL");
            nullInstance = c.initialize();
        } return nullInstance;
    }


    /**
     * Invoke a method on the object with the given name and arguments.
     * This allows the object to invoke Java methods, acting as an interface between CODELang and the JVM.
     */
    public Object invokeMethod(String methodName, CodeObject... args) {
        try {
            List<Object> argList = new ArrayList<>();
            for (CodeObject arg : args) {
                argList.add(arg.getInstance());
            }




            // Find the method with the given name and a matching argument list
            // This example assumes all methods can be uniquely identified by name and number of arguments
            Method method = null;
            for (Method m : instance.getClass().getMethods()) {
                if (m.getName().equals(methodName) && m.getParameterCount() == args.length) {
                    method = m;
                    break;
                }
            }
            if (method == null) {
                throw new NoSuchMethodException("Method " + methodName + " not found in " + codeClass.getDataTypeName());
            }

            var result =  method.invoke(instance, args);
            CodeRuntime.getRuntime().runtimeSymbolTable.registerIfNotExists(result);
            return new CodeObject(result, CodeRuntime.getRuntime().runtimeSymbolTable.getClassFromSymbols(result));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new FieldDoesNotExistError(methodName, codeClass.getDataTypeName());
        }
    }



    public String toString() {
        return "code.lang.jvm."+ instance.getClass().getSimpleName() +"[" + instance.toString() + "]";
    }

    public Object getInstance() {
        return instance;
    }




}

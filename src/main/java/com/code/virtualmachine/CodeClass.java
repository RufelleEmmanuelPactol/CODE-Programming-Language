package com.code.virtualmachine;

import com.code.data.CodePrimitive;
import com.code.errors.runtime.InvalidInitializerArgumentsError;
import com.code.parser.engine.SymbolTable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class CodeClass {

    private static CodeObject nullType;

    public static CodeObject inferInstance(Object instance) {
        String name = instance.getClass().getSimpleName();
        name = CodeRuntime.getRuntime().runtimeSymbolTable.getCanonicalName(name);
        CodeClass o = (CodeClass)CodeRuntime.getRuntime().runtimeSymbolTable.search(name);
        return o.fromInstance(instance);
    }

    public static final CodeObject getNull(){
        return nullType;
    }

    private String dataTypeName;
    private Class<?> dataType;

    public CodeClass(String dataTypeName, Class<?> dataType) {
        this.dataTypeName = dataTypeName;
        this.dataType = dataType;
        // check if name is already defined in scope.
        CodeRuntime.getRuntime().runtimeSymbolTable.registerClass(dataTypeName, this);
    }

    protected CodeClass (String dataTypeName, Class<?> dataType, boolean optional) {
        this.dataTypeName = dataTypeName;
        this.dataType = dataType;
    }

    public static CodeClass classWithoutTypeChecking(String dataTypeName, Class<?> dataType){
        return new CodeClass(dataTypeName, dataType, true);
    }

    public CodeObject fromInstance(Object instance) {
        String name = instance.getClass().getSimpleName();
        name = CodeRuntime.getRuntime().runtimeSymbolTable.getCanonicalName(name);
        CodeClass o = (CodeClass)CodeRuntime.getRuntime().runtimeSymbolTable.search(name);

        return o.initialize(instance);
    }

    public String getDataTypeName() {
        return dataTypeName;
    }

    public Class<?> getDataType() {
        return dataType;
    }

    // initialize method
    public CodeObject initialize(Object... args) {
        try {
            ArrayList<Class<?>> arguments = new ArrayList<>();
            for (var arg: args){
                arguments.add(arg.getClass());
            }
            Class[] classType = new Class[arguments.size()];
            for (int i=0; i< arguments.size(); i++){
                classType[i] = arguments.get(i);
            }

            return new CodeObject(dataType.getConstructor(classType).newInstance(args), this);
        } catch (Exception e) {
            throw new InvalidInitializerArgumentsError(e.getMessage());
        }
    }

    /**
     * Initializes a primitive data type.
     * Takes a native implementation of a primitive data type and converts it to a CODE Object,
     * which is an instance of a Code Class; which itself, asserts that it is a Code Primitive.
     */
    public static CodeObject initializePrimitive(String nativeName, Object instance) {
        CodeClass clazz = (CodeClass)CodeRuntime.getRuntime().runtimeSymbolTable.searchAssert(nativeName);
        return new CodeObject(CodePrimitive.fromNativeImplementation(instance), clazz);
    }

    @Override
    public String toString() {
        return "code.lang.native.classtype." + dataTypeName + "";
    }
}

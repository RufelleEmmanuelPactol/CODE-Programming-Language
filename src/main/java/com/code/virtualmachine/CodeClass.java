package com.code.virtualmachine;

import com.code.data.*;
import com.code.errors.runtime.InvalidInitializerArgumentsError;
import com.code.errors.runtime.TypeError;
import com.code.parser.engine.SymbolTable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * The CodeClass class is a representation of a class in the CODE. It is used to create instances of classes and
 * to store information about the class.
 */
public class CodeClass {

    private static CodeObject nullType;


    public static CodeObject getNull(){
        nullType = nullType == null ? newNullInstance() : nullType;
        return nullType;
    }

    protected static CodeObject newNullInstance() {
        return CodeRuntime.getRuntime().runtimeSymbolTable.getClassFromSymbols("NULL").cloneRef(CodeNil.nil);
    }

    private String dataTypeName;
    private Class<?> dataType;

    /**
     * Creates a new CodeClass object with the given data type name and data type.
     * @param dataTypeName The name of the data type.
     * @param dataType The data type.
     */
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

    /**
     * This creates a class without type checking. Type checking is defined as
     * the process of verifying and enforcing whether a given data type is already
     * defined in the scope.
     */
    public static CodeClass classWithoutTypeChecking(String dataTypeName, Class<?> dataType){
        return new CodeClass(dataTypeName, dataType, true);
    }

    public static CodeObject fromInstance(Object instance) {
        String name = instance.getClass().getSimpleName();
        name = CodeRuntime.getRuntime().runtimeSymbolTable.getCanonicalName(name);
        CodeClass o = (CodeClass)CodeRuntime.getRuntime().runtimeSymbolTable.search(name);
        return o.initialize(instance);
    }

    public CodeObject cloneRef (Object obj) {
        obj = getPrimitiveTypeIfPrimitive(obj);
        if (!dataType.isInstance(obj)){
            throw new TypeError(dataType.getSimpleName(), obj.getClass().getSimpleName(), "__internal__clone__");
        }
        return new CodeObject(obj, this);
    }

    private Object getPrimitiveTypeIfPrimitive(Object obj) {
        if (obj instanceof Integer i) {
            return new CodeInteger(i);
        } else if (obj instanceof String str) {
            return new CodeString(str);
        } else if (obj instanceof Double dbl) {
            return new CodeFloat(dbl);
        } else if (obj instanceof Boolean bl) {
            return new CodeBoolean(bl);
        } return obj;
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

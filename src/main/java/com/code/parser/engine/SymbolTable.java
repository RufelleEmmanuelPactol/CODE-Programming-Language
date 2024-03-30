package com.code.parser.engine;

import com.code.data.*;
import com.code.errors.runtime.ClassDoesNotExistError;
import com.code.errors.runtime.NameShadowError;
import com.code.errors.runtime.VariableNotFoundError;
import com.code.virtualmachine.CodeClass;
import com.code.virtualmachine.CodeObject;

import java.util.HashMap;

public class SymbolTable {
    protected SymbolTable parent;

    private CodeObject returnedValue;

    public void setReturnValue(CodeObject o) {
        returnedValue = o;
    }

    public CodeObject getReturnedValue () {
        returnedValue = returnedValue == null ? CodeClass.getNull() : returnedValue;
        return returnedValue;
    }



    public SymbolTable getParent(){
        return parent;
    }



    /**
     * Register a class in the symbol table. This method will
     * throw an error if the class already exists in the symbol table.
     */
    public void registerClass(String name, CodeClass instance) {
        String canonicalName = getCanonicalName(name);
        if (canonicalName != null) {
            getCanonicalClass(canonicalName);
            return;
        }
        if (search(name) != null) throw new NameShadowError(name);
        add(name, instance);
    }

    /**
     * Get a class from the symbol table by name.
     */
    public CodeClass getClassFromSymbols(String name) {
        name = getCanonicalName(name);
        Object searched = search(name);
        if (searched == null) throw new ClassDoesNotExistError(name);
        if (searched instanceof CodeClass) return (CodeClass) searched;
        throw new ClassDoesNotExistError(name);
    }

    /**
     * Get a class from the symbol table by object.
     * This method uses the object's class name to search for the class.
     */
    public CodeClass getClassFromSymbols(Object o) {
        String name = o.getClass().getSimpleName();
        String cannon = getCanonicalName(name);
        name = cannon != null ? cannon : name;
        Object searched = search(name);
        if (searched == null) throw new ClassDoesNotExistError(name);
        if (searched instanceof CodeClass) return (CodeClass) searched;
        throw new ClassDoesNotExistError(name);
    }

    /**
     * Register an object in the symbol table if it does not exist.
     */
    public void registerIfNotExists(Object o) {
        String name = o.getClass().getSimpleName();
        String cannon = getCanonicalName(name);
        if (search(cannon) == null){
            new CodeClass(name, o.getClass());
        }
    }



    protected HashMap<String, Object> symbol;

    public SymbolTable() {
        symbol = new HashMap<>();
        initCanonicalNames();
    }

    public SymbolTable(SymbolTable parent) {
        this();
        this.parent = parent;
    }

    /**
     * Search for a key in the symbol table.
     */
    public Object search(String key) {
        if (symbol.containsKey(key)) {
            return symbol.get(key);
        } else if (parent != null) {
            return parent.search(key);
        } else {
            return null;
        }
    }

    /**
     * Search for a key in the symbol table. This differs from
     * search in that it throws an error if the key is not found.
     */
    public Object searchAssert(String key) {
        String name = canonicalNames.get(key);
        key = name != null ? name : key;
        Object result = search(key);
        if (result == null) {
            throw new VariableNotFoundError(key);
        } return result;
    }

    private static HashMap<String, String> canonicalNames = new HashMap<>();

    public String getCanonicalName(String name) {
        if (canonicalNames.containsKey(name)) {
            return canonicalNames.get(name);
        }
        return name;
    }

    private static void initCanonicalNames() {
        canonicalNames.put("Integer", "INT");
        canonicalNames.put("Float", "FLOAT");
        canonicalNames.put("String", "STRING");
        canonicalNames.put("Boolean", "BOOL");
        canonicalNames.put("Null", "NULL");
        canonicalNames.put("null", "NULL");
        canonicalNames.put("Double", "FLOAT");
        canonicalNames.put("CodeNil", "NULL");
        canonicalNames.put("CHAR", "STRING");
    }

    private static HashMap<String, Class<?>> canonicalClasses;

    private static void initCanonicalClass(){
        if (canonicalClasses == null) {
            canonicalClasses = new HashMap<>();
            canonicalClasses.put("INT", CodeInteger.class);
            canonicalClasses.put("FLOAT", CodeFloat.class);
            canonicalClasses.put("STRING", CodeString.class);
            canonicalClasses.put("BOOL", CodeBoolean.class);
            canonicalClasses.put("NULL", CodeNil.class);
        }

    }

    /**
     * Get a canonical class by name. A canonical class is a class that is defined in the language.
     * It replaces Java's class name with the language's class name.
     */
    public Class<?> getCanonicalClass(String name){
        initCanonicalClass();
        Object result = search(name);

        if (canonicalClasses.containsKey(name)){
            Class<?> clazz =canonicalClasses.get(name);
            if (result == null) {
                symbol.put(name, CodeClass.classWithoutTypeChecking(name, clazz));
            }
            return clazz;
        }
        return null;
    }

    /**
     * Add a new object to the symbol table.
     */
    public void add(String key, Object value) {
        if (value instanceof CodeObject) {
            key = getCanonicalName(key);
            Class<?> cannonicalClass = getCanonicalClass(key);
            if (cannonicalClass != null) {
                return;
            }
        }
        if (this.symbol.get(key) != null) {
            throw new NameShadowError(key);
        }
        symbol.put(key, value);
    }
}

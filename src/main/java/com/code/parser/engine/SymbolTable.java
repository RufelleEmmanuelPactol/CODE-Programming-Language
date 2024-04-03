package com.code.parser.engine;

import com.code.data.*;
import com.code.errors.runtime.ClassDoesNotExistError;
import com.code.errors.runtime.NameShadowError;
import com.code.errors.runtime.VariableNotFoundError;
import com.code.virtualmachine.CodeClass;
import com.code.virtualmachine.CodeObject;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class SymbolTable {
    protected SymbolTable parent;

    private CodeObject returnedValue;

    public void setReturnValue(CodeObject o) {
        returnedValue = o;
    }

    public synchronized CodeObject getReturnedValue () {
        returnedValue = returnedValue == null ? CodeClass.getNull() : returnedValue;
        return returnedValue;
    }



    public synchronized SymbolTable getParent(){
        return parent;
    }


    public synchronized void assign(String key, CodeObject value) {
        key = getCanonicalName(key);
        Object result = search(key);
        if (result == null) {
            throw new VariableNotFoundError(key);
        }
        SymbolTable table = this;
        while (table != null) {
            if (table.symbol.containsKey(key)) {
                table.symbol.put(key, value);
                return;
            }
            table = table.parent;
        }
    }



    /**
     * Register a class in the symbol table. This method will
     * throw an error if the class already exists in the symbol table.
     */
    public synchronized void registerClass(String name, CodeClass instance) {
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
    public synchronized CodeClass getClassFromSymbols(String name) {
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
    public synchronized CodeClass getClassFromSymbols(Object o) {
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
    public synchronized CodeObject registerIfNotExists(Object o) {
        String name = o.getClass().getSimpleName();
        String cannon = getCanonicalName(name);
        var searched = search(cannon);
        if (searched == null){
            CodeClass c = new CodeClass(name, o.getClass());
            return c.cloneRef(o);
        } return ((CodeClass)searched).cloneRef(o);
    }

    public synchronized void registerNativeInterface(String name, Class<?> c) {
        CodeClass cd = new CodeClass(name, c);
        this.add(name, cd);
    }



    protected ConcurrentHashMap<String, Object> symbol;

    public SymbolTable() {
        symbol = new ConcurrentHashMap<>();
        initCanonicalNames();
    }

    public SymbolTable(SymbolTable parent) {
        this();
        this.parent = parent;
    }

    /**
     * Search for a key in the symbol table.
     */
    public synchronized Object search(String key) {
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
    public synchronized Object searchAssert(String key) {

        String name = canonicalNames.get(key);
        key = name != null ? name : key;
        Object result = search(key);
        if (result == null) {
            throw new VariableNotFoundError(key);
        } return result;
    }

    private static HashMap<String, String> canonicalNames = new HashMap<>();

    public synchronized String getCanonicalName(String name) {
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
            canonicalClasses.put("CHAR", CodeChar.class);
        }

    }

    /**
     * Get a canonical class by name. A canonical class is a class that is defined in the language.
     * It replaces Java's class name with the language's class name.
     */
    public  synchronized Class<?> getCanonicalClass(String name){
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
    public  synchronized void add(String key, Object value) {
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

    public ConcurrentHashMap<String, Object> unsafeGetSymbolTable() {
        return symbol;
    }

    public SymbolTable(ConcurrentHashMap<String, Object> symbol, SymbolTable parent) {
        this(parent);
        this.symbol = symbol;
    }
}

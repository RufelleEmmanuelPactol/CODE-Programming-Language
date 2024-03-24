package com.code.parser.engine;

import com.code.parser.nodes.ASTNode;

import java.util.HashMap;

public class SymbolTable {
    protected SymbolTable parent;

    protected HashMap<String, ASTNode> symbol;

    public SymbolTable() {
        symbol = new HashMap<>();
    }

    public SymbolTable(SymbolTable parent) {
        this();
        this.parent = parent;
    }

    public ASTNode search(String key) {
        if (symbol.containsKey(key)) {
            return symbol.get(key);
        } else if (parent != null) {
            return parent.search(key);
        } else {
            return null;
        }
    }

    public void add(String key, ASTNode value) {
        symbol.put(key, value);
    }
}

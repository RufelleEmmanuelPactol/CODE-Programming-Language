package com.code.frames;

import com.code.data.CodePrimitive;
import com.code.virtualmachine.VMThread;

import java.util.HashMap;

public class StackFrame {
    private final VMThread currentVMThread;
    public static HashMap<String, CodePrimitive> symbolTable = new HashMap<>();

    public StackFrame(VMThread currentVMThread) {
        this.currentVMThread = currentVMThread;
    }

    public void addSymbol(String symbol, CodePrimitive value) {
        symbolTable.put(symbol, value);
    }

    public CodePrimitive getSymbol(String symbol) {
        return symbolTable.get(symbol);
    }
}

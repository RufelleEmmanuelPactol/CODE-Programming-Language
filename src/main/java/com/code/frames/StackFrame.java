package com.code.frames;

import com.code.data.CodePrimitive;
import com.code.virtualmachine.VMThread;

import java.util.HashMap;

public class StackFrame {
    private VMThread currentVMThread;
    public static HashMap<String, CodePrimitive> symbolTable = new HashMap<>();
}

package com.code.parser.nodes;

import com.code.data.CodeBoolean;
import com.code.data.CodeFloat;
import com.code.data.CodeInteger;
import com.code.data.CodePrimitive;
import com.code.parser.engine.SymbolTable;
import com.code.tokenizer.tokens.Token;
import com.code.virtualmachine.CodeClass;
import com.code.virtualmachine.CodeObject;
import com.code.virtualmachine.CodeRuntime;

public class UnaryNode extends ASTNode{
    protected ASTNode child;

    public UnaryNode(Token value, ASTNode child){
        this.value = value;
        this.child = child;
    }

    public ASTNode getChild() {
        return child;
    }

    @Override
    public CodeObject execute() {
        if (value.equals("+")) {
           Object instance = child.execute().getInstance();
           if (instance instanceof Integer) {
               Integer newVal = Math.abs((Integer) instance);
               return CodeRuntime.getRuntime().runtimeSymbolTable.getClassFromSymbols(newVal).initialize(newVal);
           } else {
               Float newVal = Math.abs((Float) instance);
                return CodeRuntime.getRuntime().runtimeSymbolTable.getClassFromSymbols(newVal).initialize(newVal);
           }
        } else if (value.equals("-")) {
            Object instance = child.execute().getInstance();
            if (instance instanceof CodeInteger r) {
                int i = r.getData();
                Integer newVal = i > 0 ? -i : i;
                return CodeRuntime.getRuntime().runtimeSymbolTable.getClassFromSymbols(newVal).initialize(newVal);
            } else {

                Double newVal = -((Double) ((CodeFloat)instance).getData());
                return CodeRuntime.getRuntime().runtimeSymbolTable.getClassFromSymbols(newVal).initialize(newVal);
            }
        } else if (value.equals("NOT")) {
            CodeBoolean inst = ((CodePrimitive)child.execute().getInstance()).not();
            return CodeClass.initializePrimitive("BOOL", inst.getData());
        }  else if (value.equals("CAST_BOOL")) {
            CodeBoolean inst = ((CodePrimitive)child.execute().getInstance()).bool();
            return CodeClass.initializePrimitive("BOOL", inst.getData());
        } else if (value.equals("CAST_INT")) {
            return null;
        } else if (value.equals("CAST_FLOAT")) {
            return null;
        } else if (value.equals("CAST_STRING")) {
            String inst = (child.execute().getInstance()).toString();
            return CodeClass.initializePrimitive("STRING", inst);
        }

        return null;
    }
}

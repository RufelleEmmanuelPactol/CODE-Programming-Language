package com.code.parser.nodes;

import com.code.data.CodePrimitive;
import com.code.tokenizer.tokens.Token;
import com.code.virtualmachine.CodeClass;
import com.code.virtualmachine.CodeObject;
import com.code.virtualmachine.CodeRuntime;

import java.util.ArrayList;
import java.util.List;

public class NewNode extends ASTNode{

    private String className;
    private List<ASTNode> args;

    public NewNode(Token className, List<ASTNode> args) {
        this.className = className.getTokenAsString();
        this.args = args;
    }
    @Override
    public CodeObject execute() {
        sync();
        CodeClass c = CodeRuntime.getRuntime().runtimeSymbolTable.getClassFromSymbols(className);
        // execute each node in the args
        Object[] args = new Object[this.args.size()];
        for (int i = 0; i < this.args.size(); i++) {
            Object x =this.args.get(i).execute().getInstance();
            args[i] = x instanceof CodePrimitive<?> prim ? (prim.getData()) : x;
        }
        return c.initialize(args);
    }

    public Object wrapIfApplicable(Object obj) {
        if (obj instanceof Integer i) {
            return Integer.valueOf(i);
        } else if (obj instanceof Double d) {
            return Double.valueOf(d);
        } else if (obj instanceof Boolean b) {
            return Boolean.valueOf(b);
        } else if (obj instanceof String s) {
            return String.valueOf(s);
        } else {
            return obj;
        }
    }
}

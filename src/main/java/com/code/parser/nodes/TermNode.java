package com.code.parser.nodes;

import com.code.data.CodeBoolean;
import com.code.data.CodePrimitive;
import com.code.data.CodeString;
import com.code.errors.compile.CompileBugError;
import com.code.tokenizer.tokens.Token;
import com.code.virtualmachine.CodeClass;
import com.code.virtualmachine.CodeObject;
import com.code.virtualmachine.CodeRuntime;

public class TermNode extends BinaryNode{

    public TermNode(ASTNode left, Token thisToken, ASTNode right) {
        super(left, thisToken, right);
    }

    @Override
    public CodeObject execute() {
        sync();

        if (value.getTokenAsString().equals("$")) {
            // Concat with newline operator (flush), has optional left and right operands
            if (left != null && right != null) {
                CodeObject obj = left.execute();
                CodeObject obj2 = right.execute();
                CodePrimitive primitive1 = (CodePrimitive) obj.getInstance();
                CodePrimitive primitive2 = (CodePrimitive) obj2.getInstance();
                CodePrimitive result = primitive1.add(new CodeString("\n")).add(primitive2);
                CodeClass clazz = CodeRuntime.getRuntime().runtimeSymbolTable.getClassFromSymbols(result.getData());
                return clazz.fromInstance(result.getData());
            } else if (left != null) {
                CodeObject obj = left.execute();
                CodePrimitive primitive1 = (CodePrimitive) obj.getInstance();
                CodePrimitive result = primitive1.add(new CodeString("\n"));
                CodeClass clazz = CodeRuntime.getRuntime().runtimeSymbolTable.getClassFromSymbols(result.getData());
                return clazz.fromInstance(result.getData());
            } else if (right != null) {
                CodeObject obj = right.execute();
                CodePrimitive primitive2 = (CodePrimitive) obj.getInstance();
                CodePrimitive result = new CodeString("\n").add(primitive2);
                CodeClass clazz = CodeRuntime.getRuntime().runtimeSymbolTable.getClassFromSymbols(result.getData());
                return clazz.fromInstance(result.getData());
            } else {
               return CodeClass.initializePrimitive("STRING", "\n");
            }
        }


        CodeObject obj = left.execute();
        CodeObject obj2 = right.execute();
        CodePrimitive primitive1 = (CodePrimitive) obj.getInstance();
        CodePrimitive primitive2 = (CodePrimitive) obj2.getInstance();
        switch (value.getTokenAsString()) {
            case "*": {
                CodePrimitive result = primitive1.multiply(primitive2);
                CodeClass clazz = CodeRuntime.getRuntime().runtimeSymbolTable.getClassFromSymbols(result.getData());
                return clazz.fromInstance(result.getData());
            }
            case "/": {
                CodePrimitive result = primitive1.divide(primitive2);
                CodeClass clazz = CodeRuntime.getRuntime().runtimeSymbolTable.getClassFromSymbols(result.getData());
                return clazz.fromInstance(result.getData());
            }
            case "%": {
                CodePrimitive result = primitive1.modulo(primitive2);
                CodeClass clazz = CodeRuntime.getRuntime().runtimeSymbolTable.getClassFromSymbols(result.getData());
                return clazz.fromInstance(result.getData());
            }
            case "+": {
                CodePrimitive result = primitive1.add(primitive2);
                CodeClass clazz = CodeRuntime.getRuntime().runtimeSymbolTable.getClassFromSymbols(result.getData());
                return clazz.fromInstance(result.getData());
            } case "&": {
                String inst = (obj.getInstance()).toString();
                CodeString r = new CodeString(inst);
                CodePrimitive result = r.add((CodePrimitive) obj2.getInstance());
                CodeClass clazz = CodeRuntime.getRuntime().runtimeSymbolTable.getClassFromSymbols(result.getData());
                return clazz.fromInstance(result.getData());
            }
            case "-": {
                CodePrimitive result = primitive1.subtract(primitive2);
                CodeClass clazz = CodeRuntime.getRuntime().runtimeSymbolTable.getClassFromSymbols(result.getData());
                return clazz.fromInstance(result.getData());
            }
            case "$": {
                CodePrimitive result = primitive1.add(new CodeString("\n")).add(primitive2);
                CodeClass clazz = CodeRuntime.getRuntime().runtimeSymbolTable.getClassFromSymbols(result.getData());
                return clazz.fromInstance(result.getData());
            }
            case "==": {
                CodePrimitive result = primitive1.equalTo(primitive2);
                CodeClass clazz = CodeRuntime.getRuntime().runtimeSymbolTable.getClassFromSymbols(result.getData());
                return clazz.fromInstance(result.getData());
            } case "!=": {
                CodePrimitive result = primitive1.notEqualTo(primitive2);
                CodeClass clazz = CodeRuntime.getRuntime().runtimeSymbolTable.getClassFromSymbols(result.getData());
                return clazz.fromInstance(result.getData());
            } case ">": {
                CodePrimitive result = primitive1.greaterThan(primitive2);
                CodeClass clazz = CodeRuntime.getRuntime().runtimeSymbolTable.getClassFromSymbols(result.getData());
                return clazz.fromInstance(result.getData());
            } case "<": {
                CodePrimitive result = primitive1.lessThan(primitive2);
                CodeClass clazz = CodeRuntime.getRuntime().runtimeSymbolTable.getClassFromSymbols(result.getData());
                return clazz.fromInstance(result.getData());
            } case ">=": {
                CodePrimitive result = primitive1.greaterThanEqualTo(primitive2);
                CodeClass clazz = CodeRuntime.getRuntime().runtimeSymbolTable.getClassFromSymbols(result.getData());
                return clazz.fromInstance(result.getData());
            } case "<=": {
                CodePrimitive result = primitive1.lessThanEqualTo(primitive2);
                CodeClass clazz = CodeRuntime.getRuntime().runtimeSymbolTable.getClassFromSymbols(result.getData());
                return clazz.fromInstance(result.getData());
            } case "AND": {
                CodePrimitive result = primitive1.and(primitive2);
                CodeClass clazz = CodeRuntime.getRuntime().runtimeSymbolTable.getClassFromSymbols(result.getData());
                return clazz.fromInstance(result.getData());
            } case "OR": {
                CodePrimitive result = primitive1.or(primitive2);
                CodeClass clazz = CodeRuntime.getRuntime().runtimeSymbolTable.getClassFromSymbols(result.getData());
                return clazz.fromInstance(result.getData());
            } case "NOT": {
                CodePrimitive result = primitive1.not();
                CodeClass clazz = CodeRuntime.getRuntime().runtimeSymbolTable.getClassFromSymbols(result.getData());
                return clazz.fromInstance(result.getData());
            } case "<>" : {
                CodeBoolean result = primitive1.equalTo(primitive2).not()   ;
                CodeClass clazz = CodeRuntime.getRuntime().runtimeSymbolTable.getClassFromSymbols(result.getData());
                return clazz.fromInstance(result.getData());
            }


            default:
                throw new CompileBugError("Cannot find operator for term node, " + value.getTokenAsString() + ".");
        }

    }
}

package com.code.parser.nodes;

import com.code.tokenizer.tokens.Token;
import java.util.List;

public class VariableDeclarationNode extends ASTNode {
    protected Token type; // The data type of the variables
    protected List<VarDeclaration> declarations; // List of variable names and optional initial values

    public VariableDeclarationNode(Token type, List<VarDeclaration> declarations) {
        this.type = type;
        this.declarations = declarations;
    }

    // Inner class to hold each variable's name and optional initializer
    public static class VarDeclaration {
        public final Token name;
        public final ASTNode initializer; // This can be null if there's no initializer

        public VarDeclaration(Token name, ASTNode initializer) {
            this.name = name;
            this.initializer = initializer;
        }
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(type.getTokenAsString());
        sb.append(" ");
        for (int i = 0; i < declarations.size(); i++) {
            sb.append(declarations.get(i).name.getTokenAsString());
            if (declarations.get(i).initializer != null) {
                sb.append(" = ");
                sb.append(declarations.get(i).initializer.toString());
            }
            if (i < declarations.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append(";");
        return sb.toString();
    }
}

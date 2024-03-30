package com.code.parser.nodes;

import com.code.data.CodeString;
import com.code.tokenizer.tokens.Token;
import com.code.tokenizer.tokens.ValueToken;
import com.code.virtualmachine.CodeObject;

public class FactorNode extends BinaryNode{
    public FactorNode(ASTNode left, Token value, ASTNode right) {
        super(left, value, right);
    }

    @Override
    public CodeObject execute() {
        ValueToken inside = (ValueToken) value;
        if (inside.getValue().getInstance() instanceof CodeString cstr) {
            String rawString = cstr.getData();

            // Initialize newString as rawString for cases where no replacement is needed
            String newString = "";

            // Check if the string matches ".*" (double quotes surrounding any characters)
            if (rawString.startsWith("\"") && rawString.endsWith("\"")) {
                // Remove the first and last characters (the double quotes)
                newString = rawString.substring(1, rawString.length() - 1);
                // Replace escaped double slashes with a single slash
                newString = newString.replace("\\\\", "\\");
                cstr.replaceInside(newString);
                cstr.parseExpressionInside();
            }
            // Else, if the string matches [.*] (square brackets surrounding any characters)
            else if (rawString.startsWith("[") && rawString.endsWith("]")) {
                // Remove the first and last characters (the square brackets), leaving the .* behind
                newString = rawString.substring(1, rawString.length() - 1);
                cstr.replaceInside(newString);
            }

            // Set them as the new string inside cstr


        }
        return inside.getValue();
    }

    public Token getValue(){
        return this.value;
    }

}

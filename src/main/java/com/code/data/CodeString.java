package com.code.data;

import com.code.errors.runtime.InvalidStringInterpolationError;
import com.code.errors.runtime.TypeError;
import com.code.parser.engine.RecursiveDescentParser;
import com.code.parser.nodes.ASTNode;
import com.code.tokenizer.LeximCursor;
import com.code.tokenizer.TokenCursor;
import com.code.tokenizer.TokenFactory;

import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodeString extends CodePrimitive<String>{
    public CodeString(String data) {
        super(data, data);

        this.tokenRepresentation = data;

    }

    public int length() {
        return data.length();
    }

    public void enableEscaped() {
        escaped = true;
    }

    public CodeString(){
        super("", "");
    }

    @Override
    public String getTypeStrRepresenation() {
        return "STRING";
    }

    @Override
    public String toString() {
        return tokenRepresentation;
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public CodePrimitive add(CodePrimitive other) {
            if (other instanceof CodeString) {
                return new CodeString(this.data + other.data);
            }
        throw new TypeError(this, other, "&");
    }

    @Override
    @SuppressWarnings("rawtypes")
    public CodePrimitive subtract(CodePrimitive other) {
        throw new TypeError(this, other, "-");
    }

    @Override
    @SuppressWarnings("rawtypes")
    public CodePrimitive multiply(CodePrimitive other) {
        throw new TypeError(this, other, "*");
    }

    @Override
    @SuppressWarnings("rawtypes")
    public CodePrimitive divide(CodePrimitive other) {
        throw new TypeError(this, other, "/");
    }

    @Override
    @SuppressWarnings("rawtypes")
    public CodePrimitive modulo(CodePrimitive other) {
        throw new TypeError(this, other, "%");
    }


    @Override
    @SuppressWarnings("rawtypes")
    public CodeBoolean and(CodePrimitive other) {
        return bool().and(other.bool());
    }

    @Override
    @SuppressWarnings("rawtypes")
    public CodeBoolean or(CodePrimitive other) {
        return bool().or(other.bool());
    }

    @Override
    public CodeBoolean not() {
        return bool().not();
    }

    @Override
    public CodeBoolean bool() {
        return this.data.isEmpty() ? CodeBoolean.FALSE : CodeBoolean.TRUE;
    }

    @Override
    public CodeBoolean lessThan(CodePrimitive other) {
        throw new TypeError(this, other, "<");
    }

    @Override
    public CodeBoolean greaterThan(CodePrimitive other) {
        throw new TypeError(this, other, ">");
    }

    @Override
    public CodeBoolean lessThanEqualTo(CodePrimitive other) {
        throw new TypeError(this, other, "<=");
    }

    @Override
    public CodeBoolean greaterThanEqualTo(CodePrimitive other) {
        throw new TypeError(this, other, ">=");
    }

    @Override
    public CodeBoolean equalTo(CodePrimitive other) {
        return other.getData().equals(this.data) ? CodeBoolean.TRUE : CodeBoolean.FALSE;
    }

    private boolean escaped = false;
    @Override
    public String getData() {
        return this.data;
    }

    public void parseExpressionInside(){
        StringBuilder result = new StringBuilder();
        int lastEnd = 0; // Tracks the end of the last match

        // Pattern to match expressions within brackets
        Pattern pattern = Pattern.compile("\\[([^\\[\\]]*?)\\]");
        Matcher matcher = pattern.matcher(this.data);

        while (matcher.find()) {
            // Append the portion of the string before the current match
            result.append(this.data, lastEnd, matcher.start());

            // Extract the expression within brackets
            String expression = matcher.group(1);
            try {
                // Initialize the parser and tokenize the expression
                RecursiveDescentParser simpleParser = new RecursiveDescentParser();
                TokenFactory factory = new TokenFactory();
                TokenCursor cursor = factory.tokenize(new LeximCursor(expression));
                simpleParser.appendCursor(cursor);

                // Parse the expression and evaluate it
                ASTNode expressionNode = simpleParser.parseExpression();
                String evaluatedExpression = expressionNode.execute().getInstance().toString();

                // Append the evaluated expression to the result
                result.append(evaluatedExpression);
            } catch (Exception e) {
                // Handle invalid expressions by throwing a custom exception
                // Assuming `InvalidStringInterpolationError` is a custom exception class you've defined for this purpose
                throw new InvalidStringInterpolationError("Invalid expression: `" + expression + "`.");
            }

            lastEnd = matcher.end();
        }

        // Append the remainder of the string after the last match
        result.append(this.data.substring(lastEnd));
        this.data = result.toString();
        this.tokenRepresentation = result.toString();
    }


}

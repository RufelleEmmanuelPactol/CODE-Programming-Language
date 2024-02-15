package com.code.tokenizer.tokens;

import com.code.data.CodeInteger;
import com.code.data.CodePrimitive;
import com.code.data.CodeString;

import java.util.HashMap;

public class TokenFactory {

    protected HashMap<String, Class<? extends Token>> factoryMaster;
    

    protected void initializeOperators() {
        addOperator(">");
        addOperator(">=");
        addOperator("<");
        addOperator("<=");
        addOperator("==");
        addOperator("+");
        addOperator("++");
        addOperator("-");
        addOperator("--");
        addOperator("/");
        addOperator("*");
        addOperator("%");
        factoryMaster.put("(", OpenParenthesis.class);
        factoryMaster.put(")", CloseParenthesis.class);
    }

    protected Token terminalExpressionMatch(String str) {
        if (str.matches("\\d+")) {
            return new ValueToken(new CodeInteger(str));
        } if (str.matches("\".*\"")) {
            return new ValueToken(new CodeString(str));
        } else {
            return null;
        }
    }


    private void addOperator(String operator) {
        factoryMaster.put(operator, Operand.class);
    }

    public TokenFactory(){
        factoryMaster = new HashMap<>();
    }
}

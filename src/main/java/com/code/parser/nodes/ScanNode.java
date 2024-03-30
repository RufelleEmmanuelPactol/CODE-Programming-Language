package com.code.parser.nodes;

import com.code.data.CodeFloat;
import com.code.data.CodeInteger;
import com.code.data.CodeString;
import com.code.errors.runtime.TypeError;
import com.code.tokenizer.tokens.Token;
import com.code.virtualmachine.CodeClass;
import com.code.virtualmachine.CodeObject;

import java.util.List;
import java.util.Scanner;

public class ScanNode extends ASTNode{
    protected final VariadicNode args;
    private static Scanner scanner = new Scanner(System.in);

    public ScanNode(Token value, VariadicNode args) {
        this.value = value;
        this.args = args;
    }

    @Override
    public CodeObject execute() {
        List<NonTerminalFactorNode> args = this.args.getArgs();
        for (var arg: args) {
            CodeObject result = arg.execute();
            if (result.getInstance() instanceof CodeString){
                String value = scanner.nextLine();
                result.assign(CodeClass.initializePrimitive("STRING", value));
                continue;
            } else if (result.getInstance() instanceof CodeFloat) {
                Double value = scanner.nextDouble();
                result.assign(CodeClass.initializePrimitive("FLOAT", value));
                continue;
            } else if (result.getInstance() instanceof CodeInteger) {
                Integer value = scanner.nextInt();
                result.assign(CodeClass.initializePrimitive("INT", value));
                continue;
            }else {
                throw new TypeError(result,  "SCAN");
            }
        } return null;
    }
}

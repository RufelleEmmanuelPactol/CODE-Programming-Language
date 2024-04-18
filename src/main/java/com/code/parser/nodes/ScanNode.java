package com.code.parser.nodes;

import com.code.data.CodeFloat;
import com.code.data.CodeInteger;
import com.code.data.CodeString;
import com.code.errors.runtime.InvalidArguments;
import com.code.errors.runtime.TypeError;
import com.code.parser.engine.RecursiveDescentParser;
import com.code.tokenizer.LeximCursor;
import com.code.tokenizer.TokenCursor;
import com.code.tokenizer.TokenFactory;
import com.code.tokenizer.tokens.Token;
import com.code.virtualmachine.CodeClass;
import com.code.virtualmachine.CodeObject;
import com.code.virtualmachine.CodeRuntime;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScanNode extends ASTNode{
    protected final VariadicNode args;
    private static Scanner scanner = new Scanner(System.in);

    public ScanNode(Token value, VariadicNode args) {
        this.value = value;
        this.args = args;
    }



    public static ArrayList<String> parseString(String input) {
        ArrayList<String> results = new ArrayList<>();
        // Split input on commas that are not within quotes
        Pattern pattern = Pattern.compile("(\"[^\"]*\")|([^,]+)");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            if (matcher.group(1) != null) {  // This is the quoted text
                // Remove quotes and handle escapes within the quotes
                String quotedPart = matcher.group(1);
                String processedQuotedPart = processQuotedString(quotedPart.substring(1, quotedPart.length() - 1));
                results.add(processedQuotedPart);
            } else if (matcher.group(2) != null) {  // This is the non-quoted text
                results.add(matcher.group(2));
            }
        }

        return results;
    }

    /**
     * Processes the content of a quoted string, handling escape sequences.
     *
     * @param str The string with surrounding quotes to process.
     * @return A string with quotes removed and escape sequences processed, keeping inner spaces.
     */
    private static String processQuotedString(String str) {
        // Remove the surrounding quotes
        String content = str.substring(1, str.length() - 1);
        // Replace escaped double quotes with actual double quotes
        content = content.replace("\\\"", "\"");
        // Replace escaped backslashes with actual backslashes
        content = content.replace("\\\\", "\\");
        return content;
    }
    @Override
    public CodeObject execute() {
        sync();
        CodeRuntime.forceFlush();

        int counter = args.getArgs().size();

        String longLine = scanner.nextLine();
        var lines = parseString(longLine);
        if (lines.size() != counter) {
            throw new InvalidArguments("SCAN", counter, lines.size());
        }
        List<NonTerminalFactorNode> args = this.args.getArgs();

        for (int i=0; i<counter; i++) {
            CodeObject result = args.get(i).execute();
            var value = lines.get(i);
            if (result.getInstance() instanceof CodeString){
                result.assign(CodeClass.initializePrimitive("STRING", value));
                continue;
            } else if (result.getInstance() instanceof CodeFloat) {
                result.assign(CodeClass.initializePrimitive("FLOAT", Double.parseDouble(value.replace(" ", ""))));
                continue;
            } else if (result.getInstance() instanceof CodeInteger) {
                result.assign(CodeClass.initializePrimitive("INT", Integer.parseInt(value.replace(" ", ""))));
                continue;
            }else {
                throw new TypeError(result,  "SCAN");
            }
        }

       return CodeClass.getNull();
    }
}

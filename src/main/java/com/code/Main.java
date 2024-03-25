package com.code;


import com.code.errors.StandardErrorHandler;
import com.code.parser.engine.RecursiveDescentParser;
import com.code.tokenizer.LeximCursor;
import com.code.tokenizer.TokenCursor;
import com.code.tokenizer.TokenFactory;
import com.code.virtualmachine.CodeRuntime;
import com.code.virtualmachine.RegisterPrimitives;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {


        Thread.setDefaultUncaughtExceptionHandler(new StandardErrorHandler());
        RegisterPrimitives.registerPrimitives();

        // This is the main method that is used to run the program.
        // This method reads the <file>.code file, and prepares for execution.
        // The CodeRuntime.getRuntime().runUsingMainThread() method is used to run the
        // process using the main thread. While CODE does not support multiple threads
        // at the moment, it will in the future.

        // We plan on creating a CODE NATIVE API that will allow for the creation of
        // native .class files allowing the use of the Java API in CODE. This will allow
        // for the creation of more complex programs that require the use of the Java API.
        RecursiveDescentParser rdp = new RecursiveDescentParser();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("main.code"));
            String line;
            TokenFactory factory = new TokenFactory();
            while ((line = reader.readLine()) != null)  {
                LeximCursor cursor = new LeximCursor(line);
                TokenCursor result = factory.tokenize(cursor);
                rdp.appendCursor(result);
            }
            rdp.parseCompletely();
            var symb = CodeRuntime.getRuntime().runtimeSymbolTable;
            CodeRuntime.getRuntime().runMain();
        } catch (IOException e) {
            System.exit(0);
        }


    }
}
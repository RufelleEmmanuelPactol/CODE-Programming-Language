package com.code;


import com.code.errors.CodeError;
import com.code.errors.StandardErrorHandler;
import com.code.errors.compile.FileNotFoundError;
import com.code.errors.runtime.VMRuntimeError;
import com.code.parser.engine.RecursiveDescentParser;
import com.code.tokenizer.LeximCursor;
import com.code.tokenizer.TokenCursor;
import com.code.tokenizer.TokenFactory;
import com.code.virtualmachine.CodeRuntime;
import com.code.virtualmachine.RegisterPrimitives;
import com.code.virtualmachine.SimpleTimer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static String[] args;
    public static Boolean DEBUG_MODE = false;
    public static ArrayList<String> rawCode;
    public static String currentLine;
    public static void main(String[] args) throws Exception {

        SimpleTimer.startTime();

        Main.args = args; // set the args to the args passed in the main method
        Thread.setDefaultUncaughtExceptionHandler(new StandardErrorHandler());
        RegisterPrimitives.registerPrimitives();
        rawCode = new ArrayList<>();
        // This is the main method that is used to run the program.
        // This method reads the <file>.code file, and prepares for execution.
        // The CodeRuntime.getRuntime().runUsingMainThread() method is used to run the
        // process using the main thread. While CODE does not support multiple threads
        // at the moment, it will in the future.

        // We plan on creating a CODE NATIVE API that will allow for the creation of
        // native .class files allowing the use of the Java API in CODE. This will allow
        // for the creation of more complex programs that require the use of the Java API.
        RecursiveDescentParser rdp = new RecursiveDescentParser();

        // if no args, set args[0] to the default file, 'main.code'
        if (args.length == 0) {
            args = new String[1];
            args[0] = "main.code";
            DEBUG_MODE = true;
        }


        try {
            BufferedReader reader = new BufferedReader(new FileReader(args[0]));
            String line;
            boolean once = false;
            TokenFactory factory = new TokenFactory();
            while ((line = reader.readLine()) != null)  {
                currentLine = line;
                rawCode.add(line);
                once = true;
                LeximCursor cursor = new LeximCursor(line);
                TokenCursor result = factory.tokenize(cursor);
                rdp.appendCursor(result);
            }
            if (!once) {
              return;
            }
            rdp.parseCompletely();
            var symb = CodeRuntime.getRuntime().runtimeSymbolTable;
            CodeRuntime.getRuntime().runMain();

        } catch (IOException e) {
            String curDir= System.getProperty("user.dir");
            throw new FileNotFoundError(e.getMessage() + "\n\t\tCurrent Directory: " + curDir);
        }

        System.out.println("\n>>> CODE successfully executed in " + SimpleTimer.endTime() + ".\n>>> Process finished with exit code 0.");
    }
}
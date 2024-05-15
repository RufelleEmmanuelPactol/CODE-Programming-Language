package com.code.errors;

import com.code.Main;
import com.code.errors.compile.ParseError;
import com.code.virtualmachine.CodeRuntime;
import com.code.virtualmachine.SimpleTimer;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class StandardErrorHandler implements Thread.UncaughtExceptionHandler {
    private static final Scanner scanner = new Scanner(System.in);
    private static Throwable base = null;

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        try {
            base = e;
            System.err.println("\nCODE Standard Error Handler, CODE_LANG_VERSION: 0.1.0");
            System.err.println("\nBefore fatally crashing, the Code Runtime Virtual Machine was able to retrieve the error message.");
            System.err.println("Note that errors can come from the JVM-interops (Exceptions) or from the Code Runtime (Errors).");

            // Split the error message into lines and find the longest line
            String[] lines = (e.getMessage() == null ? "null" : e.getMessage()).split("\n");
            int maxLength = 0;
            for (String line : lines) {
                if (line.length() > maxLength) {
                    maxLength = line.length();
                }
            }
            maxLength = Math.max(maxLength, 60);
            // Adjust the border length based on the longest line
            String border = String.format("<%s CODE Virtual Machine Telemetry Module %s>", "=".repeat(( maxLength - 12) / 2), "=".repeat((maxLength - 12) / 2));
            String border2 = String.format("<%s CODE Virtual Machine Diagnostic Analysis %s>", "=".repeat(( maxLength - 13) / 2), "=".repeat((maxLength - 13) / 2));

            System.err.println("\n " + border);
            System.err.println("\nThe virtual machine has retrieved the following error message:\n");
            System.err.println("[" + e.getClass().getSimpleName() + "]::" + ((e instanceof CodeError c) ? c.getMessage() : e.getCause() == null? e.getMessage():e.getCause().toString()));
            System.err.println("\n " + border);

            if (!(e instanceof ParseError)) {
                System.err.println("\n " + border2);
                System.err.println("\nThe virtual machine has detected a fatal error in the code execution.");
                System.err.println("\nThe error is suspected to have been caused by line " + CodeRuntime.getRuntime().GLOBAL_THREAD.getCurrentLineNumber() + ".");
                System.err.println("The error is the following: ");
                System.err.println(">>>" + Main.rawCode.get(CodeRuntime.getRuntime().GLOBAL_THREAD.getCurrentLineNumber() - 1));
                System.err.println("\n\n " + border2);
            }


            System.err.println("\nThe error will cause the CODE Runtime (Virtual Machine) to stop execution.");
            System.err.println("\n\n>>> Process finished with exit code " + (e instanceof CodeError c ? c.hashcode() : e.hashCode()) + ".");
            System.err.println(">>> CODE successfully executed in " + Main.simpleTimer.endTime() + ".");
            if (Main.args.length == 0) {
                e.printStackTrace();
            }
        } catch (Exception ex) {
            System.err.println("CODE Standard Error Handler: Fatal error in the error handler. Please report this to the CODE team.");
            e.printStackTrace();
        }
    }


    public static void scheduleTask(long delayInMilliseconds, Runnable task) {
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                task.run();
            }
        }, delayInMilliseconds);
    }
}

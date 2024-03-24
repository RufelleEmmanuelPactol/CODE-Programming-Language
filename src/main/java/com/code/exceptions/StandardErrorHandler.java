package com.code.exceptions;

import com.code.virtualmachine.CodeRuntime;

public class StandardErrorHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.err.println("CODE Standard Error Handler, CODE_LANG_VERSION: 0.0.1");
        System.err.println("\tAn error occurred in the CODE Runtime (Virtual Machine)! The error is as follows: ");
        System.err.println("\n\t\t`" +e.getMessage() + "`\n\n\tFound using the CODE Runtime (Virtual Machine) in line " + CodeRuntime.getRuntime().GLOBAL_THREAD.getCurrentLineNumber() +".");
        System.err.println("\tThe error will cause the CODE Runtime (Virtual Machine) to stop execution.");
        System.err.println("\tERR_HASH: " + e.hashCode() +".");

        System.err.println("\nJVM Expansion of the Error Code Value:");
        e.printStackTrace();

    }
}

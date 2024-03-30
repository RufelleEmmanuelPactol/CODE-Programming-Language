package com.code.errors;

import com.code.Main;
import com.code.virtualmachine.CodeRuntime;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class StandardErrorHandler implements Thread.UncaughtExceptionHandler {
    private static final Scanner scanner = new Scanner(System.in);

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.err.println("CODE Standard Error Handler, CODE_LANG_VERSION: 0.0.1");
        System.err.println("\tAn error occurred in the CODE Runtime (Virtual Machine)! The error is as follows: ");
        System.err.println("\n\t\t[" + e.getClass().getSimpleName() + "]::"+ e.getMessage() + "\n\n\tFound using the CODE Runtime (Virtual Machine) in line " + CodeRuntime.getRuntime().GLOBAL_THREAD.getCurrentLineNumber() + ".");
        System.err.println("\tThe error will cause the CODE Runtime (Virtual Machine) to stop execution.");
        System.err.println("\tERR_HASH: " + e.hashCode() + ".");

        if (Main.args.length == 0) {
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

package com.code.errors;

import com.code.virtualmachine.CodeRuntime;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;


public class StandardErrorHandler implements Thread.UncaughtExceptionHandler {
    private static final Scanner scanner = new Scanner(System.in);
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.err.println("CODE Standard Error Handler, CODE_LANG_VERSION: 0.0.1");
        System.err.println("\tAn error occurred in the CODE Runtime (Virtual Machine)! The error is as follows: ");
        System.err.println("\n\t\t" +e.getMessage() + "\n\n\tFound using the CODE Runtime (Virtual Machine) in line " + CodeRuntime.getRuntime().GLOBAL_THREAD.getCurrentLineNumber() +".");
        System.err.println("\tThe error will cause the CODE Runtime (Virtual Machine) to stop execution.");
        System.err.println("\tNative Error Code: [" + e.getClass().getCanonicalName() + "]");
        System.err.println("\tERR_HASH: " + e.hashCode() +".");


        System.err.println("\nPress the enter key in 1.5 seconds to get the JVM expansion.");
        scheduleTask(1500, ()->{
            System.exit(3);
        });
        scanner.nextLine();
        System.err.println("\nJVM Expansion of the Error Code Value:");
        e.printStackTrace();


    }
    public static void scheduleTask(int delayInMilliseconds, Runnable task) {
        Timer timer = new Timer(delayInMilliseconds, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Execute the task after the delay
                task.run();
            }
        });

        timer.setRepeats(false); // Execute only once
        timer.start();
    }
}

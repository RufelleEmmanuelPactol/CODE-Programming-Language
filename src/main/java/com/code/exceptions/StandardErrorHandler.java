package com.code.exceptions;

public class StandardErrorHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.err.println(e.getMessage());
    }
}

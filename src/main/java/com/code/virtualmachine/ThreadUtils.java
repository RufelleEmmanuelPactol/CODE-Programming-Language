package com.code.virtualmachine;

public class ThreadUtils {

    Thread t;

    public ThreadUtils() {
        this.t = Thread.currentThread();
    }

    public void sleep(int millis) throws InterruptedException {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw e;
        }
    }


    public Thread getCurrentThread() {
        return Thread.currentThread();
    }

}

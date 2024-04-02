package com.code.virtualmachine;

import com.code.frames.StackFrame;
import com.code.errors.StandardErrorHandler;

import java.util.Stack;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The VMThread class is the class that is responsible for executing the code.
 * There can be multiple threads running at the same time, each with their own
 * thread and stack frame.
 */
public class VMThread {
    private int currentLineNumber;
    private String threadName;
    private static final ReentrantLock lock = new ReentrantLock();

    public void incrementLineNumber() {
        synchronized (lock) {
            currentLineNumber++;
        }
    }

    public void syncLineNumber(int num) {
        currentLineNumber = num;
    }

    public int getCurrentLineNumber() {
        synchronized (lock) {
            return currentLineNumber;
        }
    }

    public VMThread(int currentLineNumber, String threadName) {
        this.currentLineNumber = currentLineNumber;
        this.threadName = threadName;
    }

    /**
     * Contains attributes and methods for the runtime stack of the
     * current thread.
     */
    private final Stack<StackFrame> threadStack = new Stack<>();

    public void pushStackFrame(StackFrame stack) {
        synchronized (lock) {
            threadStack.push(stack);
        }
    }

    public StackFrame popStackFrame(StackFrame stack) {
        synchronized (lock) {
            return threadStack.pop();
        }
    }

    protected void createProcess (Runnable process) {
        Thread t = new Thread(process);
        t.setUncaughtExceptionHandler(new StandardErrorHandler());
        t.setName("com.code.vm.process.thread::virtual.std::protocol[" + threadName +"]:\n");
        t.start();
    }
}

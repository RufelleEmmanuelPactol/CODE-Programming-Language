package com.code.virtualmachine;

import com.code.frames.StackFrame;

import java.util.Stack;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The executor class is the class that is responsible for executing the code.
 * There can be multiple executors running at the same time, each with their own
 * thread and stack frame.
 */
public class VMThread {
    private int currentLineNumber;
    private static final ReentrantLock lock = new ReentrantLock();

    public void incrementLineNumber() {
        synchronized (lock) {
            currentLineNumber++;
        }
    }

    public int getCurrentLineNumber() {
        synchronized (lock) {
            return currentLineNumber;
        }
    }

    public VMThread(int currentLineNumber) {
        this.currentLineNumber = currentLineNumber;
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
}

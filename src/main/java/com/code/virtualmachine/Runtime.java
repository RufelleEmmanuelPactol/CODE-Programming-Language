package com.code.virtualmachine;

import com.code.frames.StackFrame;

import java.util.Stack;
import java.util.concurrent.locks.ReentrantLock;

public class Runtime {

    private static final ReentrantLock lock = new ReentrantLock();
    public static final VMThread GLOBAL_THREAD = new VMThread(0);



}

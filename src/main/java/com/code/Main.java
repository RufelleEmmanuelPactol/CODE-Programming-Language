package com.code;

import com.code.data.CodeInteger;
import com.code.data.CodeString;
import com.code.frames.StackFrame;
import com.code.virtualmachine.CodeRuntime;

public class Main {
    public static void main(String[] args) {
        CodeRuntime.getRuntime().runUsingMainThread(()->{
            CodeString empty = new CodeString("");
            CodeString zero = new CodeString("0");
            System.out.println(empty.not());
            System.out.println(zero.not());
            System.out.println(empty.and(empty));
            System.out.println(empty.or(zero));
        });
    }
}
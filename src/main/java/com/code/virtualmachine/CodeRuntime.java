package com.code.virtualmachine;

import com.code.errors.compile.NoStartError;
import com.code.parser.engine.SymbolTable;
import com.code.parser.nodes.ASTNode;
import com.code.parser.nodes.CodeBlockNode;

import java.util.Stack;

/**
 * The Runtime class is the class that manages the runtime of the virtual machine.
 * It contains the global thread and the lock that is used to synchronize the threads.
 * Currently, CODE only supports one thread, but in the future, it will support multiple
 * threads.
 */
public class CodeRuntime {

    private boolean RETURNING_FUNCTION = false;

    public void returnInterrupt() {
        RETURNING_FUNCTION = true;
    }

    public boolean retrieveReturnInterrupt() {
        boolean value = RETURNING_FUNCTION;
        RETURNING_FUNCTION = false;
        return value;
    }

    // CONTINUE interrupt
    private boolean CONTINUE_INTERRUPT = false;

    public void continueInterrupt() {
        CONTINUE_INTERRUPT = true;
    }

    public boolean retrieveContinueInterrupt() {
        boolean value = CONTINUE_INTERRUPT;
        CONTINUE_INTERRUPT = false;
        return value;
    }

    private boolean BREAK_INTERRUPT = false;

    public void breakInterrupt() {
        BREAK_INTERRUPT = true;
    }

    public boolean retrieveBreakInterrupt() {
        boolean value = BREAK_INTERRUPT;
        BREAK_INTERRUPT = false;
        return value;
    }

    private boolean INSIDE_LOOP = false;

    public void loopInterrupt() {
        INSIDE_LOOP = true;
    }

    public boolean retrieveLoopInterrupt() {
        boolean value = INSIDE_LOOP;
        INSIDE_LOOP = false;
        return value;
    }



    public SymbolTable runtimeSymbolTable;

    private Stack<SymbolTable> symbolTableStack = new Stack<>();

    public void siblingSymbolTablePush() {
        symbolTableStack.push(runtimeSymbolTable);
        runtimeSymbolTable = new SymbolTable(runtimeSymbolTable.getParent());
    }

    public void siblingSymbolTablePop() {
        runtimeSymbolTable = symbolTableStack.pop();
    }


    public void popSymbolTable() {
        runtimeSymbolTable = runtimeSymbolTable.getParent();
    }

    public void pushSymbolTable() {
        runtimeSymbolTable = new SymbolTable(runtimeSymbolTable);
    }



    public final VMThread GLOBAL_THREAD;

    /**
     * Don't let anyone instantiate this class.
     */
    private CodeRuntime() {
        GLOBAL_THREAD = new VMThread(1, "MAIN_THREAD");
        runtimeSymbolTable = new SymbolTable();
    };

    private static final CodeRuntime CURRENT_RUNTIME = new CodeRuntime();

    /**
     * This method returns the current instance of the runtime engine.
     * @return the current instance of the runtime engine.
     */
    public static CodeRuntime getRuntime() {
        return CURRENT_RUNTIME;
    }


    /**
     * This method runs the process using the main thread.
     * The main thread is the only thread that is currently supported.
     */
    public void runUsingMainThread(Runnable process)  {
        GLOBAL_THREAD.createProcess(process);
    }

    public void runMain() {
        CodeBlockNode mainFunction = (CodeBlockNode) runtimeSymbolTable.search("CODE");
        if (mainFunction == null) {
            throw new NoStartError();
        } for (ASTNode statement : mainFunction.getStatements()) {
            statement.execute();
        }
    }
}

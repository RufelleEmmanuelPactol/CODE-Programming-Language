package com.code.virtualmachine;

import com.code.errors.compile.NoStartError;
import com.code.parser.engine.SymbolTable;
import com.code.parser.nodes.ASTNode;
import com.code.parser.nodes.CodeBlockNode;

/**
 * The Runtime class is the class that manages the runtime of the virtual machine.
 * It contains the global thread and the lock that is used to synchronize the threads.
 * Currently, CODE only supports one thread, but in the future, it will support multiple
 * threads.
 */
public class CodeRuntime {

    public SymbolTable runtimeSymbolTable;
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

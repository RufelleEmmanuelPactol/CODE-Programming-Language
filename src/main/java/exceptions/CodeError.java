package exceptions;

public abstract class CodeError extends RuntimeException {

    public CodeError(String message) {
        super("CODElang Virtual Machine Error: " +
                message);
    }
}

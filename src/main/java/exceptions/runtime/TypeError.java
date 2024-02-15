package exceptions.runtime;

import com.code.data.CodePrimitive;
import com.code.virtualmachine.CodeRuntime;

public class TypeError extends VMRuntimeError {

    @SuppressWarnings("rawtypes")
    public TypeError(CodePrimitive type1, CodePrimitive type2, String operator) {
        super("type_error\n" +
                "\tType Error: " + type1.getTypeStrRepresenation() + " and " + type2.getTypeStrRepresenation() + " are not compatible. When using operator '" + operator + "'. Found in line " + CodeRuntime.getRuntime().GLOBAL_THREAD.getCurrentLineNumber() + ".");
    }


}

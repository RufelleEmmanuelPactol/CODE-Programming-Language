package com.code.data;

import com.code.errors.runtime.TypeError;

public class CodeChar extends CodeString{

    @Override
    public String getTypeStrRepresenation() {
        return "CHAR";
    }

    public CodeChar (String s) {
        super(s);
        if (this.getRawString().length() != 1) {
            throw new TypeError("CHAR", "STRING", "assignment[Characters != 1], passed string {" + getRawString() + "}");
        }
    }

    public CodeChar(){
        super(" ");
    }



    @Override
    public String getData() {
        return Character.toString(super.getData().charAt(0));
    }
}

package com.code.data;

import com.code.errors.runtime.TypeError;

public class CodeChar extends CodeString{

    @Override
    public String getTypeStrRepresenation() {
        return "CHAR";
    }

    public CodeChar (String s) {
        super(s);

        if (s.length() != 3) {
            throw new TypeError("CHAR", "STRING", "assignment[Characters > 1]");
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

package com.code;


import com.code.tokenizer.LeximCursor;
import com.code.tokenizer.TokenCursor;
import com.code.tokenizer.TokenFactory;
import com.code.virtualmachine.CodeRuntime;

import java.io.BufferedReader;
import java.io.FileReader;

public class Main {
    public static void main(String[] args) {
        CodeRuntime.getRuntime().runUsingMainThread(()->{
            try {
                BufferedReader reader = new BufferedReader(new FileReader("main.code"));
                String line = "";
                TokenFactory factory = new TokenFactory();
                while ((line = reader.readLine()) != null)  {
                    LeximCursor cursor = new LeximCursor(line);
                    TokenCursor result = factory.tokenize(cursor);
                    result.forEach(System.out::println);
                }



            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
package com.code;

import com.code.data.CodeInteger;
import com.code.data.CodeString;
import com.code.tokenizer.LeximCursor;
import com.code.tokenizer.TokenFactory;
import com.code.virtualmachine.CodeRuntime;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class Main {
    public static void main(String[] args) {
        CodeRuntime.getRuntime().runUsingMainThread(()->{
            try {
                BufferedReader reader = new BufferedReader(new FileReader("main.code"));
                StringBuilder build = new StringBuilder();
                String line = "";
                while ((line = reader.readLine()) != null)  {
                    build.append(line).append("\n");
                    LeximCursor cursor = new LeximCursor(line);
                    TokenFactory.PRODUCE.tokenize(cursor).forEach(System.out::println);
                }


            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
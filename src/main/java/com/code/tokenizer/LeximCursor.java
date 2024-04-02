package com.code.tokenizer;

import com.code.tokenizer.tokens.Token;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LeximCursor implements Iterable<String> {
    protected ArrayList<String> lexims;


    public LeximCursor(String codeLine) {
        lexims = new ArrayList<>();
        ArrayList<String> tokens = new ArrayList<>();
        // Updated pattern string to handle escaped quotes within quoted strings and ensure operators are matched correctly
        String patternString =
                "(\\\"(?:\\\\\\\"|[^\"])*?\\\")" +                  // Matches double-quoted string literals
                        "|('(?:\\\\'|[^'])*?')" +                            // Matches single-quoted string literals
                        "|(>=|<>|<=|<|==|\\+\\+|--|\\+|-|/|\\*|>|%)" +        // Matches operators
                        "|([()=;.,:{}#$])" +                                  // Matches single character symbols, excluding []
                        "|(\\d+\\.\\d+|\\d+)" +                                // Matches floating-point numbers and integers
                        "|([\\w&|]+)" +                                       // Matches words (identifiers and keywords)
                        "|(\\[.\\])+" +
                        "|(@NATIVE)";                                         // Matches [any character only one]

        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(codeLine);


        while (matcher.find()) {
            for (int i = 1; i <= matcher.groupCount(); i++) {
                if (matcher.group(i) != null) {
                    tokens.add(matcher.group(i));
                }
            }
        }

       tokens.add("\n");
        lexims = tokens;
    }

    @Override
    public Iterator<String> iterator() {
        return lexims.iterator();
    }

    @Override
    public void forEach(Consumer<? super String> action) {
        lexims.forEach(action);
    }

    @Override
    public Spliterator<String> spliterator() {
        return lexims.spliterator();
    }





}

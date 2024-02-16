package com.code.tokenizer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LeximCursor implements Iterable<String> {
    private ArrayList<String> lexims;


    public LeximCursor(String codeLine) {
        lexims = new ArrayList<>();
        ArrayList<String> tokens = new ArrayList<>();
        // Updated pattern string to handle escaped quotes within quoted strings and ensure operators are matched correctly
        String patternString =
                "(\"(?:\\\\\"|[^\"])*?\")" + // Match quoted strings, accounting for escaped quotes
                        "|(>=|<=|==|\\+\\+|--|\\+|-|/|\\*|>|%)|([()=;.,])" + // Match operators including '>', and single character tokens
                        "|(\\d+)" + // Match integers
                        "|(\\w+)"; // Match words

        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(codeLine);

        while (matcher.find()) {
            for (int i = 1; i <= matcher.groupCount(); i++) {
                if (matcher.group(i) != null) {
                    tokens.add(matcher.group(i));
                }
            }
        }

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


    // implement such that we can use java range based for loop

}

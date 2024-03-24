package com.code.tokenizer;

import com.code.exceptions.compile.ParseError;
import com.code.tokenizer.tokens.NewLine;
import com.code.tokenizer.tokens.Token;
import com.code.virtualmachine.CodeRuntime;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class TokenCursor implements Iterable<Token> {
    private ArrayList<Token> tokens;
    private int currentIndex = 0;
    public TokenCursor(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }

    public void append(Token token) {
        tokens.add(token);
    }

    public Token next() {

        if (currentIndex < tokens.size()) {
            if (current() instanceof NewLine) {
                CodeRuntime.getRuntime().GLOBAL_THREAD.incrementLineNumber();
            }
            return tokens.get(currentIndex++);
        }
        return null;
    }

    public Token current() {
        if (currentIndex < tokens.size()) {
            return tokens.get(currentIndex);
        }
        throw new ParseError("No more tokens to parse! Your statement is incomplete.");
    }

    public ArrayList<Token> getAll() {
        return tokens;
    }

    public Token lookAhead() {
        if (currentIndex < tokens.size()) {
            return tokens.get(currentIndex);
        }
        return null;
    }

    public boolean hasNext() {
        return currentIndex < tokens.size();
    }

    public void reset() {
        currentIndex = 0;
    }

    public int size() {
        return tokens.size();
    }

    public Token previous() {
        if (currentIndex > 0) {
            return tokens.get(--currentIndex);
        }
        return null;
    }

    public Token lookBefore() {
        if (currentIndex > 0) {
            return tokens.get(currentIndex - 1);
        }
        return null;
    }





    @Override
    public Iterator<Token> iterator() {
        return tokens.iterator();
    }

    @Override
    public void forEach(Consumer<? super Token> action) {
        tokens.forEach(action);
    }

    @Override
    public Spliterator<Token> spliterator() {
        return tokens.spliterator();
    }
}

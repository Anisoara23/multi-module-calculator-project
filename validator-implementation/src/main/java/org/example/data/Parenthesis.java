package org.example.data;

import java.util.Arrays;
import java.util.List;

public enum Parenthesis {

    RIGHT_PARENTHESIS(')'),
    LEFT_PARENTHESIS('(');

    public final char parenthesis;

    Parenthesis(char parenthesis) {
        this.parenthesis = parenthesis;
    }

    public static List<Character> getCharParenthesis() {
        return Arrays.stream(Parenthesis.values())
                .map(value -> value.parenthesis)
                .toList();
    }
}

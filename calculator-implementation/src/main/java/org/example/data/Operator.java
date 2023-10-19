package org.example.data;

import java.util.Arrays;
import java.util.List;

public enum Operator {

    PLUS('+'),
    MINUS('-'),
    MULTIPLY('*'),
    DIVIDE('/');

    public final char operator;

    Operator(char operator) {
        this.operator = operator;
    }

    public static List<Character> getCharOperators() {
        return Arrays.stream(Operator.values())
                .map(value -> value.operator)
                .toList();
    }
}

package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

import static org.example.data.Operator.*;
import static org.example.data.Parenthesis.*;

public class ValidatorImpl implements Validator{

    private final Stack<Integer> validatorStack = new Stack<>();

    public static final List<Character> OPERATORS = getCharOperators();

    public static final List<Character> PARENTHESIS = getCharParenthesis();

    @Override
    public void validate(String expression) {
        validateExpressionContent(expression);
        validateParenthesis(createParenthesisArray(expression));
    }

    private void validateExpressionContent(String expression) {
        validateNoInvalidChars(expression);
        validateStartsWith(expression);
        validateEndsWith(expression);
        validateConsecutiveOperators(expression);
    }

    private void validateConsecutiveOperators(String expression) {
        char[] expressionCharArray = expression.toCharArray();
        for (int i = 0; i < expressionCharArray.length - 1; i++) {
            if (OPERATORS.contains(expressionCharArray[i]) && OPERATORS.contains(expressionCharArray[i + 1])) {
                throw new IllegalArgumentException("Cannot have more than one operator consecutive: " + expressionCharArray[i] + expressionCharArray[i + 1]);
            }
        }
    }

    private void validateEndsWith(String expression) {
        List<Character> invalidEndings = new ArrayList<>(OPERATORS);
        invalidEndings.add(LEFT_PARENTHESIS.parenthesis);

        Optional<Character> optionalChar = invalidEndings.stream()
                .filter(op -> expression.endsWith(String.valueOf(op)))
                .findFirst();

        if (optionalChar.isPresent()) {
            throw new IllegalArgumentException("Expression can not end with " + optionalChar.get());
        }
    }

    private void validateStartsWith(String expression) {
        if (expression.startsWith(String.valueOf(MULTIPLY.operator)) || expression.startsWith(String.valueOf(DIVIDE.operator))) {
            throw new IllegalArgumentException("Expression can not start with " + expression.charAt(0));
        }
    }

    private void validateNoInvalidChars(String expression) {
        char[] expressionCharArray = expression.toCharArray();
        for (char ch : expressionCharArray) {
            if ((ch < 48 || ch > 57) && !OPERATORS.contains(ch) && !PARENTHESIS.contains(ch)) {
                throw new IllegalArgumentException("Illegal operator or operand: " + ch);
            }
        }
    }

    private List<Character> createParenthesisArray(String expression) {
        List<Character> parenthesis = new ArrayList<>();
        for (char ch : expression.toCharArray()) {
            if (ch == RIGHT_PARENTHESIS.parenthesis || ch == LEFT_PARENTHESIS.parenthesis) {
                parenthesis.add(ch);
            }
        }
        return parenthesis;
    }

    private void validateParenthesis(List<Character> parenthesisArray) {
        for (int parenthesis : parenthesisArray) {
            if (validatorStack.isEmpty() && parenthesis == RIGHT_PARENTHESIS.parenthesis) {
                throw new IllegalArgumentException("Left parenthesis are missing");
            } else if (!validatorStack.isEmpty() && parenthesis == RIGHT_PARENTHESIS.parenthesis && validatorStack.peek() == LEFT_PARENTHESIS.parenthesis) {
                validatorStack.pop();
            } else {
                validatorStack.push(parenthesis);
            }
        }
        if (!validatorStack.isEmpty()) {
            throw new IllegalArgumentException("Right parenthesis are missing");
        }
    }
}

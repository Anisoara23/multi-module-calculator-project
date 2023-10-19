package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static org.example.data.Operator.*;
import static org.example.data.Parenthesis.*;

public class CalculatorImpl implements Calculator{
    public final Stack<Object> expressionElementsStack = new Stack<>();

    public static final List<Character> OPERATORS_AND_PARENTHESIS = new ArrayList<>();

    private static int index = 0;

    static {
        OPERATORS_AND_PARENTHESIS.addAll(getCharOperators());
        OPERATORS_AND_PARENTHESIS.addAll(getCharParenthesis());
    }

    @Override
    public double calculate(String expression) {
        StringBuilder newExpression = new StringBuilder();

        while (expressionElementsStack.size() != 1) {
            if (expressionElementsStack.size() > 1) {
                while (!expressionElementsStack.isEmpty()) {
                    newExpression.insert(0, expressionElementsStack.pop());
                }
                calculatePriorityOperations(String.valueOf(newExpression));
                calculateNonPriorityOperations();
            } else {
                calculatePriorityOperations(expression);
            }
            index = 0;
        }

        return (double) expressionElementsStack.pop();
    }

    private void calculatePriorityOperations(String expression) {
        char[] expressionCharArray = expression.toCharArray();
        while (index < expressionCharArray.length) {
            if (expressionElementsStack.isEmpty()) {
                if (OPERATORS_AND_PARENTHESIS.contains(expressionCharArray[index])) {
                    if (expressionCharArray[index] == MINUS.operator) {
                        index++;
                        expressionElementsStack.push(-getNumber(expressionCharArray));
                    } else if (expressionCharArray[index] == PLUS.operator) {
                        index++;
                        expressionElementsStack.push(getNumber(expressionCharArray));
                    } else {
                        expressionElementsStack.push(expressionCharArray[index]);
                        index++;
                    }
                } else {
                    expressionElementsStack.push(getNumber(expressionCharArray));
                }
            } else {
                if (expressionCharArray[index] == MINUS.operator && expressionCharArray[index - 1] == LEFT_PARENTHESIS.parenthesis) {
                    index++;
                    expressionElementsStack.push(-getNumber(expressionCharArray));
                } else if (expressionCharArray[index] == PLUS.operator && expressionCharArray[index - 1] == LEFT_PARENTHESIS.parenthesis) {
                    index++;
                    expressionElementsStack.push(getNumber(expressionCharArray));
                } else if (getCharOperators().contains(expressionCharArray[index]) || expressionCharArray[index] == LEFT_PARENTHESIS.parenthesis) {
                    expressionElementsStack.push(expressionCharArray[index]);
                    index++;
                } else if (expressionCharArray[index] == RIGHT_PARENTHESIS.parenthesis) {
                    calculateNonPriorityOperations();
                } else {
                    Double number = getNumber(expressionCharArray);
                    if (expressionElementsStack.peek().equals(MULTIPLY.operator) || expressionElementsStack.peek().equals(DIVIDE.operator)) {
                        char op = (char) expressionElementsStack.pop();
                        number = switch (op) {
                            case '*' -> (Double) expressionElementsStack.pop() * number;
                            case '/' -> (Double) expressionElementsStack.pop() / number;
                            default -> number;
                        };
                    }
                    expressionElementsStack.push(number);
                }
            }
        }

    }

    private void calculateNonPriorityOperations() {
        while (expressionElementsStack.size() > 1) {
            Double number = (Double) expressionElementsStack.pop();
            char op = (char) expressionElementsStack.pop();
            number = switch (op) {
                case '+' -> (Double) expressionElementsStack.pop() + number;
                case '-' -> (Double) expressionElementsStack.pop() - number;
                case '*' -> (Double) expressionElementsStack.pop() * number;
                case '/' -> (Double) expressionElementsStack.pop() / number;
                default -> number;
            };

            if (!expressionElementsStack.isEmpty() && expressionElementsStack.peek().equals(LEFT_PARENTHESIS.parenthesis)) {
                expressionElementsStack.pop();
                expressionElementsStack.push(number);
                index++;
                break;
            }
            expressionElementsStack.push(number);
            index++;
        }
    }

    private static Double getNumber(char[] expressionCharArray) {
        StringBuilder stringNumber = new StringBuilder();

        while (index < expressionCharArray.length && !OPERATORS_AND_PARENTHESIS.contains(expressionCharArray[index])) {
            stringNumber.append(expressionCharArray[index]);
            index++;
        }

        return Double.parseDouble(stringNumber.toString());
    }
}

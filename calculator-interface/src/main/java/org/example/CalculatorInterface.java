package org.example;

import java.util.Scanner;

public class CalculatorInterface {

    public static void main(String[] args) {
        Calculator calculator = new CalculatorImpl();
        Validator validator = new ValidatorImpl();

        try (Scanner scanner = new Scanner(System.in)){
            while (true){
                System.out.println("Give the expression to be calculated(type 'q' to quite): ");
                String expression = scanner.nextLine();

                if (expression.equalsIgnoreCase("q")){
                    break;
                }

                try {
                    String nonEmptyCharsExpression = expression.replaceAll("\\s", "");
                    validator.validate(nonEmptyCharsExpression);
                    System.out.println("Result = " + calculator.calculate(nonEmptyCharsExpression) + "\n");
                } catch (IllegalArgumentException e){
                    System.out.println(e.getMessage());
                }
            }

        }
    }
}

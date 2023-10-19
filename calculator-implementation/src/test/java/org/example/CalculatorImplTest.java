package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculatorImplTest {
    private Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new CalculatorImpl();
    }

    @ParameterizedTest
    @CsvSource({
            "10+2,12",
            "10-2,8",
            "10/2,5",
            "10*2,20",
            "10+2-1,11",
            "10/2+2,7",
            "10+5*2,20",
            "10-2+5/2-1,6.5",
            "10-2*3/3,8",
            "(10+(2+1)/2)*(2+8),115",
            "20/2*(2+3)-(28+2)/2,35",
            "9-10,-1",
            "-9-11,-20",
            "+9-11,-2",
            "((10+2)*(10-2)/4)*2+2*(2+1),54"
    })
    void test(String expression, double expectedResult) {
        double actualResult = calculator.calculate(expression);
        assertEquals(Math.round(expectedResult), Math.round(actualResult));
    }
}
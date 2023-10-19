package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorImplTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = new ValidatorImpl();
    }

    @ParameterizedTest
    @ValueSource(strings = {"1+2", "(1+2)*4", "1/2+3-2*(3+5*(6-5))"})
    public void testWhenValidExpressionProvided_thenDoNothing(String expression) {
        assertDoesNotThrow(() -> validator.validate(expression), "Expression should be valid");
    }

    @ParameterizedTest
    @CsvSource({"(2+3,Right parenthesis are missing",
            "*9,Expression can not start with *",
            "a+1,Illegal operator or operand: a",
            "/4+5,Expression can not start with /",
            "(5+6)),Left parenthesis are missing",
            "4+5+,Expression can not end with +",
            "4+5-,Expression can not end with -",
            "4+5*,Expression can not end with *",
            "5/,Expression can not end with /",
            "++2+3,Cannot have more than one operator consecutive: ++",
            "2+-3,Cannot have more than one operator consecutive: +-",
            "2+3(,Expression can not end with ("
    })
    void testWhenInvalidExpressionProvided_thenThrow(String expression, String expectedMessage) {
        String actualMessage = assertThrows(IllegalArgumentException.class, () -> validator.validate(expression), "Expected exception is not thrown").getMessage();
        assertEquals(expectedMessage, actualMessage, "Error message is not as expected");
    }
}
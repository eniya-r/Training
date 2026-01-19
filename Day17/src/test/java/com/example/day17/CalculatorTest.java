package com.example.day17;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    private Calculator calc;

    @BeforeEach
    void setUp() {
        calc = new Calculator();
    }

    @Test
    void add_shouldReturnSum() {
        assertEquals(8, calc.add(5, 3), "5 + 3 should be 8");
        assertEquals(0, calc.add(-2, 2), "-2 + 2 should be 0");
    }

    @Test
    void subtract_shouldReturnDifference() {
        assertEquals(2, calc.subtract(5, 3));
        assertEquals(-4, calc.subtract(3, 7));
    }

    @Test
    void multiply_shouldReturnProduct() {
        assertEquals(15, calc.multiply(5, 3));
        assertEquals(0, calc.multiply(0, 99));
    }

    @Test
    void divide_shouldReturnQuotient() {
        assertEquals(5, calc.divide(10, 2));
        assertEquals(-3, calc.divide(-9, 3));
    }

    @Test
    void divide_byZero_shouldThrow() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> calc.divide(10, 0),
                "Division by zero should throw"
        );
        assertEquals("Division by zero", ex.getMessage());
    }

    @AfterEach
    void tearDown() {
        calc = null;
    }
}


package com.example.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class MathOperationServiceTest {

    @Test
    void safeOperation_success_returnsEngineValue() throws Exception {
        Math.MathEngine engine = mock(Math.MathEngine.class);
        when(engine.performOperation(10, 5)).thenReturn(2);

        Math.MathOperationService service = new Math.MathOperationService(engine);

        int result = service.safeOperation(10, 5);

        assertEquals(2, result);
        verify(engine, times(1)).performOperation(10, 5);
    }

    @Test
    void safeOperation_engineThrows_returnsFallbackMinusOne() throws Exception {
        Math.MathEngine engine = mock(Math.MathEngine.class);
        when(engine.performOperation(10, 0)).thenThrow(new Exception("Division by zero"));

        Math.MathOperationService service = new Math.MathOperationService(engine);

        int result = service.safeOperation(10, 0);

        assertEquals(-1, result);
        verify(engine, times(1)).performOperation(10, 0);
    }
}

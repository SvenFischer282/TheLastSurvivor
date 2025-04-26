package Main.Utils.Exceptions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NegativeValueExceptionTest {


    @Test
    void testNegativeValueException() {
        NegativeValueException negativeValueException = assertThrows(NegativeValueException.class, () -> {
            throw new NegativeValueException("You can't use negative values");
        });
        assertEquals("You can't use negative values", negativeValueException.getMessage());
    }
}
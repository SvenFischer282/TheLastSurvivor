package Main.Utils.Exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GunNotReadyExceptionTest {

    @Test
    void testGunNotReadyExceptionMessage() {
        String expectedMessage = "Gun isn't ready yet";

        GunNotReadyException exception = assertThrows(
                GunNotReadyException.class,
                () -> {
                    // Simulate a method that would throw it
                    throw new GunNotReadyException(expectedMessage);
                }
        );

        // Optionally check the message too
        assertEquals(expectedMessage, exception.getMessage());
    }
}

package Main.Utils.Exceptions;

/**
 * An exception which handles negative values
 */
public class NegativeValueException extends Exception {
    public NegativeValueException(String message) {
        super(message);
    }
}

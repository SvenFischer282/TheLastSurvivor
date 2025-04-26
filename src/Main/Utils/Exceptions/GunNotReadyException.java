package Main.Utils.Exceptions;

/**
 * AN exception which handles if gun is ready
 */
public class GunNotReadyException extends Exception {
    public GunNotReadyException(String message) {
        super(message);
    }
}

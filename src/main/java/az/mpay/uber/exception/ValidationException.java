package az.mpay.uber.exception;

public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, String pin) {
        super(String.format(message, pin));
    }
}

package az.mpay.unitech.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, String pin) {
        super(String.format(message, pin));
    }

    public UserNotFoundException(String message, Long userId) {
        super(String.format(message, userId));
    }
}

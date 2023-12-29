package az.mpay.uber.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, String phoneNumber) {
        super(String.format(message, phoneNumber));
    }
}

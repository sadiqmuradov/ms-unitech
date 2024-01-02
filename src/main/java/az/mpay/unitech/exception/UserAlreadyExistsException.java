package az.mpay.unitech.exception;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String message, String pin) {
        super(String.format(message, pin));
    }
}

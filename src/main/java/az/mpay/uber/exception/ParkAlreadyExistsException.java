package az.mpay.uber.exception;

public class ParkAlreadyExistsException extends RuntimeException {

    public ParkAlreadyExistsException(String message, String parkId) {
        super(String.format(message, parkId));
    }
}

package az.mpay.uber.exception;

public class ParkNotFoundException extends RuntimeException {

    public ParkNotFoundException(String message, String parkId) {
        super(String.format(message, parkId));
    }
}

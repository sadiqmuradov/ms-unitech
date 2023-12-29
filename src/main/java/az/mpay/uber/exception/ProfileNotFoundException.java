package az.mpay.uber.exception;

public class ProfileNotFoundException extends RuntimeException {

    public ProfileNotFoundException(String message, String profileId) {
        super(String.format(message, profileId));
    }
}

package az.mpay.unitech.exception;

public class MultipleAccountsException extends RuntimeException {

    public MultipleAccountsException(String message, String phoneNumber) {
        super(String.format(message, phoneNumber));
    }
}

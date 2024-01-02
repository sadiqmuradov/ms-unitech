package az.mpay.unitech.exception;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(String message, Long accountId) {
        super(String.format(message, accountId));
    }
}

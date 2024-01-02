package az.mpay.unitech.exception;

public class TransactionLogNotFoundException extends RuntimeException {

    public TransactionLogNotFoundException(String message, String logId) {
        super(String.format(message, logId));
    }
}
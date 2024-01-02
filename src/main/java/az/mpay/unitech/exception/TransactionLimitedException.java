package az.mpay.unitech.exception;

public class TransactionLimitedException extends RuntimeException {

    public TransactionLimitedException(String message, String limits) {
        super(String.format(message, limits));
    }
}

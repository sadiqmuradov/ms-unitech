package az.mpay.uber.exception;

public class TransactionDeclinedException extends RuntimeException {

    public TransactionDeclinedException(String message) {
        super(message);
    }
}

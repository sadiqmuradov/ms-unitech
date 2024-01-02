package az.mpay.unitech.exception;

public class TransactionDeclinedException extends RuntimeException {

    public TransactionDeclinedException(String message) {
        super(message);
    }
}

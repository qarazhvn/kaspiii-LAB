package kz.lab.project1;

public class TransactionService implements TransactionInterface {

    private TransactionMode mode;

    public TransactionService(TransactionMode mode) {
        this.mode = mode;
    }

    public void setMode(TransactionMode mode) {
        this.mode = mode;
    }

    @Override
    public String startTransaction(String cardNumber, long amount) {
        return ApiService.getInstance().start(cardNumber, amount);
    }

    @Override
    public String completeTransaction(String transactionId, boolean success) {
        return switch (mode) {
            case MODE1 -> ApiService.getInstance().completeMode1(transactionId, success);
            case MODE2 -> ApiService.getInstance().completeMode2(transactionId, success);
        };
    }
}

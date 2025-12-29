package kz.lab.project1;

public interface TransactionInterface {
    String startTransaction(String cardNumber, long amount);
    String completeTransaction(String transactionId, boolean success);
}

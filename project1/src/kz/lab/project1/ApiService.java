package kz.lab.project1;

public class ApiService {
    private static ApiService instance;

    public static ApiService getInstance() {
        if (instance == null) {
            instance = new ApiService();
        }
        return instance;
    }

    public String start(String cardNumber, long amount) {
        return "0";
    }

    public String completeMode1(String transactionId, boolean success) {
        return "1";
    }

    public String completeMode2(String transactionId, boolean success) {
        return "2";
    }
}

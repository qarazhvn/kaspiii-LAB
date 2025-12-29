package kz.lab.project1;

public class Main {
    public static void main(String[] args) {
        System.out.println("Project1...");

        TransactionService service =
                new TransactionService(TransactionMode.MODE1);

        String tx1 = service.startTransaction("4111111111111111", 100);
        System.out.println(service.completeTransaction(tx1, true));

        service.setMode(TransactionMode.MODE2);

        String tx2 = service.startTransaction("4111111111111111", 200);
        System.out.println(service.completeTransaction(tx2, false));
    }
}


import java.util.concurrent.atomic.AtomicInteger;

public class BankAccountAtomicFix {

    static class BankAccount {
        private final AtomicInteger balance = new AtomicInteger(0);

        public void deposit(int amount) {
            balance.addAndGet(amount);
        }

        public int getBalance() {
            return balance.get();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        BankAccount account = new BankAccount();
        Runnable customerDeposit = () -> account.deposit(100);

        Thread customer1 = new Thread(customerDeposit, "Customer-1");
        Thread customer2 = new Thread(customerDeposit, "Customer-2");

        customer1.start();
        customer2.start();

        customer1.join();
        customer2.join();

        System.out.println("Actual   balance = " + account.getBalance());
    }
}

import java.util.concurrent.locks.ReentrantLock;

class BankAccount {
    public int balance = 100;
    private final ReentrantLock lock = new ReentrantLock();

    public void deposit(int amount) {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " acquired lock for deposit");
            int newBalance = balance + amount;
            Thread.sleep(500);
            balance = newBalance;
            System.out.println(Thread.currentThread().getName() + " deposited " + amount + ", new balance = " + balance);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
            System.out.println(Thread.currentThread().getName() + " released lock after deposit");
        }
    }

    public void withdraw(int amount) {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " acquired lock for withdraw");
            if (balance >= amount) {
                int newBalance = balance - amount;
                Thread.sleep(500);
                balance = newBalance;
                System.out.println(Thread.currentThread().getName() + " withdrew " + amount + ", new balance = " + balance);
            } else {
                System.out.println(Thread.currentThread().getName() + " insufficient funds");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
            System.out.println(Thread.currentThread().getName() + " released lock after withdraw");
        }
    }
}

public class LockingDemo {
    public static void main(String[] args) {
        BankAccount account = new BankAccount();

        Thread t1 = new Thread(() -> account.deposit(50), "Thread-Deposit");
        Thread t2 = new Thread(() -> account.withdraw(30), "Thread-Withdraw");

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Final balance = " + account.balance);
    }
}

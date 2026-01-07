import java.util.concurrent.atomic.AtomicInteger;

public class AtomicVariableDemo {

    static class Counter {
        private final AtomicInteger count = new AtomicInteger(0);

        public void increment() {
            count.incrementAndGet();
        }

        public int getCount() {
            return count.get();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();

        Runnable task = () -> {
            for (int i = 0; i < 100000; i++) {
                counter.increment();
            }
        };

        Thread t1 = new Thread(task, "Worker-1");
        Thread t2 = new Thread(task, "Worker-2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        int expected = 2 * 100000;
        int actual = counter.getCount();

        System.out.println("Expected count = " + expected);
        System.out.println("Actual   count = " + actual);
    }
}

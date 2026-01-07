public class SimpleSynchronizedDemo {
    static class Counter {
        private int count = 0;

        public synchronized void increment() {
            count++;
        }

        public  int getCount() {
            return count;
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

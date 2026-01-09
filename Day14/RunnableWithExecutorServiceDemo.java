import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RunnableWithExecutorServiceDemo {

    static class PrintRunnable implements Runnable {
        private final String label;

        public PrintRunnable(String label) {
            this.label = label;
        }

        @Override
        public void run() {
            for (int i = 1; i <= 3; i++) {
                String threadName = Thread.currentThread().getName();
                System.out.println(threadName + " - " + label + " : step " + i);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    System.out.println(threadName + " - interrupted");
                    return;
                }
            }
            System.out.println(Thread.currentThread().getName() + " - " + label + " completed");
        }
    }

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        try {
            Runnable task1 = new PrintRunnable("Job-A");
            Runnable task2 = new PrintRunnable("Job-B");
            Runnable task3 = new PrintRunnable("Job-C");

            executor.execute(task1);

            executor.submit(task2);
            executor.submit(task3);

        } finally {
            executor.shutdown();
        }
    }
}

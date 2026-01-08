import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceDemo {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        for (int i = 1; i <= 50; i++) {
            int taskId = i;
            executor.submit(() -> {
                System.out.println("Task " + taskId + " running on " + Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {

                    System.out.println("Task " + taskId + " interrupted.");
                    return;
                }
                System.out.println("Task " + taskId + " completed.");
            });
        }

        executor.shutdown();
        System.out.println("Executor shutdown initiated...");

        try {
            if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                System.out.println("Forcing shutdown...");
                executor.shutdownNow();

          //      executor.awaitTermination(5, TimeUnit.SECONDS);

            }
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted while waiting.");
            executor.shutdownNow();
        }

        System.out.println("Main thread finished.");
    }
}

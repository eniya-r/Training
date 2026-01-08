import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CachedThreadPoolDemo {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService pool = Executors.newCachedThreadPool();

        submitBurst(pool, "Burst-1");

        TimeUnit.SECONDS.sleep(2);
        submitBurst(pool, "Burst-2");

        pool.shutdown();
        System.out.println("Main: Shutdown initiated.");

        if (!pool.awaitTermination(5, TimeUnit.SECONDS)) {
            System.out.println("Main: Pool did not terminate in time. Forcing shutdown...");
            pool.shutdownNow();
        }

        System.out.println("Main: Finished. Cached thread pool terminated.");
    }

    private static void submitBurst(ExecutorService pool, String label) {
        for (int i = 1; i <= 6; i++) {
            final int taskId = i;
            pool.submit(() -> {
                String name = Thread.currentThread().getName();
                System.out.println(label + " - Task " + taskId + " started on " + name);
                try {
                    long millis = 200 + (taskId * 50L);
                    TimeUnit.MILLISECONDS.sleep(millis);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println(label + " - Task " + taskId + " interrupted on " + name);
                    return;
                }
                System.out.println(label + " - Task " + taskId + " completed on " + name);
            });
        }
    }
}

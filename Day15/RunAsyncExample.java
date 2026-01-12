import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class RunAsyncExample {
    public static void main(String[] args) {
        CompletableFuture<Void> loggingTask = CompletableFuture.runAsync(() -> {
            try {
                System.out.println("Async log started on thread: " + Thread.currentThread().getName());
                TimeUnit.MILLISECONDS.sleep(300);
                System.out.println("Async log finished!");
            } catch (InterruptedException e) {
                throw new RuntimeException("Logging task interrupted", e);
            }
        });

        System.out.println("Main thread continues...");

        loggingTask.join();

        System.out.println("Program done.");
    }
}

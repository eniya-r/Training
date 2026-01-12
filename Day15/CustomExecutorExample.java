import java.util.concurrent.*;

public class CustomExecutorExample {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService ioExecutor = Executors.newFixedThreadPool(4);

        CompletableFuture<String> fetchUserFuture = CompletableFuture.supplyAsync(() -> {
            sleep(300);
            return "User: Eniya";
        }, ioExecutor);



        CompletableFuture<Void> all = CompletableFuture.allOf(fetchUserFuture);

        all.join();
        System.out.println("Fetch result = " + fetchUserFuture.join());

        ioExecutor.shutdown();
        System.out.println("Program done.");
    }

    private static void sleep(long ms) {
        try {
            TimeUnit.MILLISECONDS.sleep(ms);
        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted", e);
        }
    }
}

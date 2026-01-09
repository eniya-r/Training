import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureDemo {

    public static void main(String[] args) {
        System.out.println("Main thread started...");
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                return "Interrupted!";
            }
            return "hello completablefuture";
        });

        CompletableFuture<String> transformedFuture = future
                .thenApply(result -> result.toUpperCase())
                .thenApply(result -> result + " :: processed");

        transformedFuture.thenAccept(finalResult ->
                System.out.println("Async result: " + finalResult)
        );

        System.out.println("Main thread is free to do other work...");

        try {
            transformedFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("Main thread done.");
    }
}

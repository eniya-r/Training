import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class ThenComposeExample {
    public static void main(String[] args) {
        CompletableFuture<Integer> userIdFuture = CompletableFuture.supplyAsync(() -> {
            sleep(200);
            System.out.println("Fetched user ID");
            return 42;
        });

        CompletableFuture<List<String>> ordersFuture = userIdFuture.thenCompose(userId -> {
            return fetchOrdersAsync(userId);
        });

        ordersFuture.thenAccept(orders -> {
            System.out.println("Orders for user: " + orders);
        }).join();

        System.out.println("Program done.");
    }

    private static CompletableFuture<List<String>> fetchOrdersAsync(int userId) {
        return CompletableFuture.supplyAsync(() -> {
            sleep(300);
            System.out.println("Fetched orders for userId=" + userId);
            return List.of("Order#A1", "Order#B2", "Order#C3");
        });
    }

    private static void sleep(long ms) {
        try { TimeUnit.MILLISECONDS.sleep(ms); }
        catch (InterruptedException e) { Thread.currentThread().interrupt(); throw new RuntimeException(e); }
    }
}

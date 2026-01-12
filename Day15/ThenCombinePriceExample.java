import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class ThenCombinePriceExample {
    public static void main(String[] args) {
        CompletableFuture<Double> priceFuture = CompletableFuture.supplyAsync(() -> {
            sleep(250);
            System.out.println("Fetched product price");
            return 100.00;
        });

        CompletableFuture<Double> shippingFuture = CompletableFuture.supplyAsync(() -> {
            sleep(300);
            System.out.println("Fetched shipping fee");
            return 49.00;
        });

        CompletableFuture<Double> totalFuture = priceFuture.thenCombine(
                shippingFuture,
                (price, shipping) -> price + shipping
        );

        CompletableFuture<String> messageFuture = totalFuture.thenApply(total ->
                "Payable total:" + String.format("%.2f", total)
        );

        messageFuture.thenAccept(System.out::println).join();

        System.out.println("Program done.");
    }

    private static void sleep(long ms) {
        try { TimeUnit.MILLISECONDS.sleep(ms); }
        catch (InterruptedException e) { Thread.currentThread().interrupt(); throw new RuntimeException(e); }
    }
}

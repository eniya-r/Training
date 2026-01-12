import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.concurrent.atomic.AtomicInteger;

public class PartialTicketBookingDemo {
    static class BookingResult {
        final boolean success;
        final String ticketId;
        final String message;

        private BookingResult(boolean success, String ticketId, String message) {
            this.success = success;
            this.ticketId = ticketId;
            this.message = message;
        }

        static BookingResult success(String ticketId) {
            return new BookingResult(true, ticketId, "Booked successfully");
        }

        static BookingResult failure(String reason) {
            return new BookingResult(false, null, reason);
        }

        @Override
        public String toString() {
            return success ? ("✔ " + ticketId) : ("✖ " + message);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int requested = 10;
        int available = 5;

        if (requested > available) {
            System.out.println("Requested " + requested + " tickets, but only " + available + " are available.");
            System.out.println("Proceeding with partial booking of " + available + " tickets...");
        }

        ExecutorService executor = Executors.newFixedThreadPool(8);

        AtomicInteger remainingSeats = new AtomicInteger(available);

        List<CompletableFuture<BookingResult>> bookingFutures = new ArrayList<>();

        for (int i = 1; i <= requested; i++) {
            int attemptNo = i;
            CompletableFuture<BookingResult> bookingFuture =
                    reserveSeatAsync(remainingSeats, attemptNo, executor)
                            .thenCompose(reserved -> {
                                if (!reserved) {
                                    return CompletableFuture.completedFuture(
                                            BookingResult.failure("Sold out (attempt #" + attemptNo + ")")
                                    );
                                }
                                return issueTicketAsync(attemptNo, executor)
                                        .thenApply(ticketId -> BookingResult.success(ticketId));
                            });

            bookingFutures.add(bookingFuture);
        }

        CompletableFuture<Void> allDone =
                CompletableFuture.allOf(bookingFutures.toArray(new CompletableFuture[0]));
        allDone.join();

        List<BookingResult> results = bookingFutures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());

        long successCount = results.stream().filter(r -> r.success).count();
        long failureCount = results.size() - successCount;

        System.out.println("\n--- Booking Summary ---");
        System.out.println("Requested: " + requested);
        System.out.println("Available at start: " + available);
        System.out.println("Booked successfully: " + successCount);
        System.out.println("Failed: " + failureCount);

        results.stream()
                .filter(r -> r.success)
                .forEach(r -> System.out.println("Ticket issued: " + r.ticketId));

        results.stream()
                .filter(r -> !r.success)
                .forEach(r -> System.out.println("Failure: " + r.message));

        executor.shutdown();

        executor.awaitTermination(5, TimeUnit.SECONDS);


    }


    private static CompletableFuture<Boolean> reserveSeatAsync(AtomicInteger remainingSeats,
                                                               int attemptNo,
                                                               Executor executor) {
        return CompletableFuture.supplyAsync(() -> {
            sleep(150);

            int previous = remainingSeats.getAndUpdate(r -> (r > 0) ? (r - 1) : r);
            boolean reserved = previous > 0;

            System.out.println("Attempt #" + attemptNo + ": " +
                    (reserved ? "Reserved. Remaining=" + remainingSeats.get()
                            : "Sold out"));
            return reserved;
        }, executor);
    }

    private static CompletableFuture<String> issueTicketAsync(int attemptNo, Executor executor) {
        return CompletableFuture.supplyAsync(() -> {
            sleep(200);
            String ticketId = "TKT-" + attemptNo + "-" + System.currentTimeMillis();
            System.out.println("Attempt #" + attemptNo + ": Ticket issued = " + ticketId);
            return ticketId;
        }, executor);
    }

    private static void sleep(long ms) {
        try { TimeUnit.MILLISECONDS.sleep(ms); }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // restore interrupt flag
            throw new RuntimeException("Interrupted", e);
        }
    }
}

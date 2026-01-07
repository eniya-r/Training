public class VolatileDemo {
    private static volatile boolean running = true;

    public static void main(String[] args) throws InterruptedException {
        Thread worker = new Thread(() -> {
            while (running) {

            }
            System.out.println("Worker stopped.");
        });

        worker.start();

        Thread.sleep(2000);
        running = false;
    }
}

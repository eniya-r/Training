public class InterruptedExceptionDemo {

    public static void main(String[] args) throws InterruptedException {
        Thread worker = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " started.");

            try {
                System.out.println("Worker going to sleep for 5 seconds...");
                Thread.sleep(5000);
                System.out.println("Worker woke up normally (no interrupt).");
            } catch (InterruptedException e) {
                System.out.println("Caught InterruptedException in worker!");
                System.out.println("Exception toString(): " + e);
                return;
            }

            System.out.println("Worker finished work.");
        }, "Worker-Thread");

        worker.start();

        Thread.sleep(1000);

        worker.interrupt();

        worker.join();
        System.out.println("Main thread done.");
    }
}

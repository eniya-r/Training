class PrintTask implements Runnable {
    private final String label;
    PrintTask(String label) {
        this.label = label;
    }

    @Override
    public void run() {
        System.out.println(label + " started on " + Thread.currentThread().getName());
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            System.err.println(label + " interrupted on " + Thread.currentThread().getName());
            Thread.currentThread().interrupt();
        }
        System.out.println(label + " finished on " + Thread.currentThread().getName());
    }
}

public class RunnableDemo {
    public static void main(String[] args) {
        Runnable taskA = new PrintTask("Task-A");
        Runnable taskB = new PrintTask("Task-B");

        Thread t1 = new Thread(taskA, "Worker-1");
        Thread t2 = new Thread(taskB, "Worker-2");

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.err.println("Main interrupted while joining");
            Thread.currentThread().interrupt();
        }

        System.out.println("All tasks done; main exits.");
    }
}

class PrintTask1 implements Runnable {
    private final String label;

    PrintTask1(String label) {
        this.label = label;
    }

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        System.out.println(label + " started on " + threadName);
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            System.err.println(label + " interrupted on " + threadName);
            Thread.currentThread().interrupt();
            return;
        }
        System.out.println(label + " finished on " + threadName);
    }
}

public class RunnableParallelDemo {
    public static void main(String[] args) {
        Runnable taskA = new PrintTask("Task-A");
        Runnable taskB = new PrintTask("Task-B");
        Runnable taskC = new PrintTask("Task-C");

        Thread t1 = new Thread(taskA, "Worker-1");
        Thread t2 = new Thread(taskB, "Worker-2");
        Thread t3 = new Thread(taskC, "Worker-3");

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            System.err.println("Main interrupted while joining");
            Thread.currentThread().interrupt();
        }

        System.out.println("All tasks done; main exits.");
    }
}

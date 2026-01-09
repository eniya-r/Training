import java.util.concurrent.*;

public class CallableWithExecutorServiceDemo {

    static class SumCallable implements Callable<Integer> {
        private final int n;

        public SumCallable(int n) {
            this.n = n;
        }

        @Override
        public Integer call() throws Exception {
            if (n < 0) {
                throw new IllegalArgumentException("n must be non-negative");
            }
            int sum = 0;
            for (int i = 1; i <= n; i++) {
                sum += i;
                Thread.sleep(100);
            }
            return sum;
        }
    }

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        try {
            Callable<Integer> c1 = new SumCallable(5);
            Callable<Integer> c2 = new SumCallable(10);
            Callable<Integer> c3 = new SumCallable(7);

            Future<Integer> f1 = executor.submit(c1);
            Future<Integer> f2 = executor.submit(c2);
            Future<Integer> f3 = executor.submit(c3);

            try {
                Integer r1 = f1.get();
                Integer r2 = f2.get();
                Integer r3 = f3.get();

                System.out.println("[f1] result = " + r1);
                System.out.println("[f2] result = " + r2);
                System.out.println("[f3] result = " + r3);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("[main] Interrupted while waiting for results.");
            } catch (ExecutionException e) {
                System.out.println("[main] Task failed: " + e.getCause().getMessage());
            }

            System.out.println("[f1] isDone after get? " + f1.isDone());
            System.out.println("[f2] isDone after get? " + f2.isDone());
            System.out.println("[f3] isDone after get? " + f3.isDone());

        } finally {
            executor.shutdown();
        }

        System.out.println("Main thread done.");
    }
}

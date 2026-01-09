import java.util.concurrent.*;

public class FutureGetTimeoutDemo {

    static class VowelCountTask implements Callable<Integer> {
        private final String text;

        public VowelCountTask(String text) {
            this.text = text;
        }

        @Override
        public Integer call() throws Exception {
            Thread.sleep(1500);
            int count = 0;
            for (char ch : text.toLowerCase().toCharArray()) {
                if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u') {
                    count++;
                }
            }
            return count;
        }
    }

    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        try {
            Future<Integer> future = executor.submit(new VowelCountTask("Hello Eniya how are you"));

            try {
                Integer vowelCount = future.get(1, TimeUnit.SECONDS);
                System.out.println("Vowel count: " + vowelCount);
            } catch (TimeoutException te) {
                System.out.println("Timed out after 1 second. Cancelling the task...");
                boolean cancelled = future.cancel(true);
                System.out.println("Cancelled? " + cancelled);
            } catch (InterruptedException ie) {
                System.out.println("Main interrupted while waiting.");
            } catch (ExecutionException ee) {
                System.out.println("Task failed: " + ee.getCause());
            }

        } finally {
            executor.shutdown();
        }

        System.out.println("Main thread done.");
    }
}

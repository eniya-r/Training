import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;

public class CpuVsIoThreadsDemo {

    private static String ts() {
        return System.currentTimeMillis() + " ms";
    }

    static class CpuWorker implements Runnable {
        private volatile boolean running = true;
        private long ops = 0L;

        public void stop() { running = false; }

        @Override
        public void run() {
            double acc = 0.0;
            while (running) {
                acc += Math.sin(ops % 360) * Math.cos(ops % 360);
                if ((ops & 0xFFFFF) == 0) {
                    if (acc > 1e12) acc = 0.0;
                    Thread.yield();
                }
                ops++;
            }
            System.out.println(ts() + " CpuWorker: stopped, ops=" + ops);
        }
    }

    static class HeartbeatWorker implements Runnable {
        private volatile boolean running = true;

        public void stop() { running = false; }

        @Override
        public void run() {
            int beat = 0;
            while (running) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
                beat++;
                String msg = "Heartbeat #" + beat + " on " + Thread.currentThread().getName();
                System.out.println(ts() + " " + msg);
            }
            System.out.println(ts() + " HeartbeatWorker: stopped");
        }
    }

    static class CpuSampler implements Runnable {
        private final OperatingSystemMXBean osBean;
        private volatile boolean running = true;

        CpuSampler(OperatingSystemMXBean osBean) {
            this.osBean = osBean;
        }

        public void stop() { running = false; }

        @Override
        public void run() {
            while (running) {
                double procLoad = osBean.getProcessCpuLoad();
                double sysLoad  = osBean.getSystemCpuLoad();

                String procPct = (procLoad >= 0 ? String.format("%.1f%%", procLoad * 100) : "N/A");
                String sysPct  = (sysLoad  >= 0 ? String.format("%.1f%%", sysLoad  * 100) : "N/A");

                System.out.println(ts() + " CPU Sampler: Process=" + procPct + " | System=" + sysPct);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            System.out.println(ts() + " CPU Sampler: stopped");
        }
    }

    public static void main(String[] args) throws InterruptedException {

        OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

        CpuWorker cpuWorker = new CpuWorker();
        HeartbeatWorker hbWorker = new HeartbeatWorker();
        CpuSampler sampler = new CpuSampler(osBean);

        Thread tCpu = new Thread(cpuWorker, "CPU-Worker");
        Thread tHB  = new Thread(hbWorker,  "Heartbeat-Worker");
        Thread tMon = new Thread(sampler,   "CPU-Sampler");

        printStates("Before start", tCpu, tHB, tMon);

        tCpu.start();
        tHB.start();
        tMon.start();

        printStates("After start", tCpu, tHB, tMon);

        Thread.sleep(20_000);

        cpuWorker.stop();
        hbWorker.stop();
        sampler.stop();

        tCpu.join();
        tHB.join();
        tMon.join();

        printStates("After join", tCpu, tHB, tMon);
        System.out.println(ts() + " Main: demo finished");
    }

    private static void printStates(String label, Thread... threads) {
        System.out.println(ts() + " --- " + label + " ---");
        for (Thread t : threads) {
            System.out.println("  " + t.getName() + " state = " + t.getState());
        }
    }
}

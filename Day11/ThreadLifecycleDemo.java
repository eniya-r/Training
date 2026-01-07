class MyThread extends Thread {
    public void run() {
        System.out.println(Thread.currentThread().getName() + " is running");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " finished");
    }
}

public class ThreadLifecycleDemo {
    public static void main(String[] args) {
        MyThread t1 = new MyThread();
        MyThread t2 = new MyThread();

        System.out.println("Before start: t1 state = " + t1.getState());

        t1.start();
        t2.start();

        System.out.println("After start: t1 state = " + t1.getState());

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("After join: t1 state = " + t1.getState());
        System.out.println("Main thread finished");
    }
}

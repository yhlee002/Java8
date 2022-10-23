package P06_Concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Executors
public class App3 {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2); // 스레드를 하나만 사용하는 executor
        executorService.submit(getRunnable("one"));
        executorService.submit(getRunnable("two"));
        executorService.submit(getRunnable("three"));
        executorService.submit(getRunnable("four"));
        executorService.submit(getRunnable("five"));

        executorService.shutdown();
    }

    private static Runnable getRunnable(String msg) {
        return () -> System.out.println(msg + " :: " + Thread.currentThread().getName());
    }
}

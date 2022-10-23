package P06_Concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

// Executors
public class App3 {
    public static void main(String[] args) {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

        /**
         * delay 후에 실행되도록 스케쥴링하기
         */
        /*
        service.schedule(getRunnable("Hello"), 1, TimeUnit.SECONDS);
        service.shutdown();
        */

        /**
         * delay 후에 period 간격으로 실행되도록 스케쥴링 하기
         * `service.shutdown()`가 뒤따라 오게 되면, inturrupt가 발생하면 스레드가 멈추므로 사용하지 않는다.
         */

        service.scheduleAtFixedRate(getRunnable("Hello"), 1, 2, TimeUnit.SECONDS);


    }

    private static Runnable getRunnable(String msg) {
        return () -> System.out.println(msg + " :: " + Thread.currentThread().getName());
    }
}

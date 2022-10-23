package P06_Concurrent;

import java.util.List;
import java.util.concurrent.*;

// Callable, Future
public class App4 {
  public static void main(String[] args) throws ExecutionException, InterruptedException {
    ExecutorService service = Executors.newFixedThreadPool(4);

    Callable<String> collable1 = () -> "Processing1";
    Callable<String> collable2 = () -> "Processing2";
    Callable<String> collable3 = () -> "Processing3";


    List<Future<String>> futures = service.invokeAll(List.of(collable1, collable2, collable3));
    futures.stream().forEach(f -> {
      System.out.println(f.isDone());
    });

    String futureAny = service.invokeAny(List.of(collable1, collable2, collable3));
    System.out.println(futureAny);

    service.shutdown();
    System.out.println("service shutdown : " + service.isShutdown());
  }
}

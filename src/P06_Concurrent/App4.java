package P06_Concurrent;

import java.util.concurrent.*;

// Callable
public class App4 {
  public static void main(String[] args) throws ExecutionException, InterruptedException {
    ExecutorService service = Executors.newFixedThreadPool(4);

    System.out.println("Start");

    Future<String> future = service.submit(() -> "Processing"); // Collable의 T call();
    System.out.println("isDone() " + future.isDone());

    future.get();

    System.out.println("isDone() " + future.isDone());
    System.out.println("End!");
  }


}

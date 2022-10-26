package P06_Concurrent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// CompletableFuture
public class App5 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /**
         * CompletableFuture
         * - void runAsync(Callable c) 사용
         * - T applySync(Function f)
         * - void applySync(Consumer c)
         */
        // create with constructor
        CompletableFuture<String> future = new CompletableFuture();
        future.complete("Hello");
        System.out.println(future.get());

        // create and set value with static method(completedFuture(U v))
        CompletableFuture<String> future2 = CompletableFuture.completedFuture("World");
        System.out.println(future2.get());

        // create CompletableFuture<Void>
        CompletableFuture<Void> future3 = CompletableFuture.runAsync(() -> {
            System.out.println("RunAsyncResult");
        });
        future3.get();

        // create CompletableFuture<T>
        CompletableFuture<String> future4 = CompletableFuture.supplyAsync(() -> "SupplyAsyncResult");
        System.out.println(future4.get());

        // add callback(return value exist) - G thenApply(Function<T, G> f)
        CompletableFuture<String> future5 = CompletableFuture.supplyAsync(() -> "SupplyAsyncResult2")
                .thenApply(f -> {
                    System.out.println(f);
                    return f.toUpperCase();
                });
        System.out.println(future5.get());

        // add callback(return value not exist, parameter exist) - void thenAccept(Consumer c)
        CompletableFuture<Void> future6 = CompletableFuture.supplyAsync(() -> "SupplyAsyncResult3")
                .thenAccept(f -> System.out.println(f));
        future6.get();

        // add callback(return value not exist, no parameter) - void thenRun(Runnable r)
        CompletableFuture<Void> future7 = CompletableFuture.supplyAsync(() -> "SupplyAsyncResult4")
                .thenRun(() -> {
                    System.out.println("thenRun1");
                    System.out.println("thenRun2");
                });
        future7.get();

        /**
         * ForkJoinPool이 아닌 별도의 스레드 전달
         * 콜백의 경우 thenRun, thenApply, thenAccept 에 'Async'를 붙인 메서드를 통해 별도의 스레드 전달 가능
         */

        ExecutorService executor = Executors.newFixedThreadPool(2);

        // supplySync + thenRunAsync
        CompletableFuture<Void> future8_1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("[future8_1] " + Thread.currentThread().getName());
            return "future8_1";
        }).thenRunAsync(() -> {
            System.out.println("[future8_1] " + Thread.currentThread().getName());
        }, executor);
        future8_1.get();

        // supplySync + thenAcceptAsync
        CompletableFuture<Void> future8_2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("[future8_2] " + Thread.currentThread().getName());
            return "future8_2";
        }).thenAcceptAsync((result) -> {
            System.out.println("[future8_2] " + Thread.currentThread().getName()); // main?
        }, executor);
        future8_2.get();

        // supplySync + thenApplyAsync
        CompletableFuture<Boolean> future8_3 = CompletableFuture.supplyAsync(() -> {
            System.out.println("[future8_3] " + Thread.currentThread().getName());
            return "future8_3";
        }).thenApplyAsync((result) -> {
            System.out.println("[future8_3] " + Thread.currentThread().getName());
            return result.startsWith("future");
        }, executor);
        future8_3.get();

        executor.shutdown();
    }
}

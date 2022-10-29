package P06_Concurrent;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

// CompletableFuture(작업) 간의 조합 - Callback과 다름
public class App6 {

    public static void main(String[] args) {

        /**
         * thenCompose(CompletableFuture c2)
         * 두 작업간에 의존성이 있는 경우에 조합하는 경우
         */
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> {
            System.out.println("[1] Hello " + Thread.currentThread().getName());
            return "Hello";
        });

        CompletableFuture<String> future = hello.thenCompose(App6::getWorld);
        System.out.println(future.join());


        /**
         * thenCombine(CompletableFuture c2)
         * 두 작업간에 의존성이 없지만 동시에 비동기적으로 실행하게끔 하는 경우
         * Cf. 두개 타입의 인자를 받아 한 타입의 결과를 리턴하는 `BiFunction`을 인자로 받는다.
         */

        CompletableFuture<String> hello2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("[thenCombine] Hello " + Thread.currentThread().getName());
            return "Hello";
        });


        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> {
            System.out.println("[thenCombine] World " + Thread.currentThread().getName());
            return "World";
        });


        CompletableFuture<String> future2 = hello2.thenCombine(world, (hr, wr) -> hr + " " + wr);
        System.out.println(future2.join());

        /**
         * allOf(CompletableFuture c1, CompletableFuture c2, ...)
         * 두개 이상의 작업을 모두 조합하는 경우
         * Cf. 모든 작업의 리턴 타입이 다를 수 있고(같지 않아도 컴파일시에 에러가 나지 않음), 에러가 난 작업도 있을 것이므로 result의 의미가 없다.
         */

        // 1차 시도(단순히 결과 받아보기)
        CompletableFuture<Void> future3 = CompletableFuture.allOf(hello, world)
                .thenApply(result -> {
                    return result; // null 반환
                });
        System.out.println("[allOf(1)] " + future3.join());

        // 2차 시도(애초에 CompletableFuture 타입의 리스트를 반환해서 thenApply에 이르러서는 리스트의 모든 작업들의 결과를 `map()`, `collect()`를 통해 받아온다.
        List<CompletableFuture> futures = Arrays.asList(hello, world);
        CompletableFuture<List<Object>> future4 = // CompletableFuture<List<String>>해도 됨(반환 타입이 모두 같기 때문에)
                CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]))
                .thenApply(result -> futures.stream().map(CompletableFuture::join).collect(Collectors.toList()));
        System.out.println("[allOf(2)] " + future4.join());

        /**
         * anyOf(CompletableFuture<T> c1, CompletableFuture<U> c2, ...)
         * 두개 이상의 작업중 가장 먼저 끝난 작업의 결과를 도출하는 경우
         */

        CompletableFuture<Object> future5 = CompletableFuture.anyOf(hello, world).thenApply(result -> result);

        System.out.println("[anyOf] " + future5.join());


    }

    private static CompletableFuture<String> getWorld(String message) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("[1] World " + Thread.currentThread().getName());
            return message + " World";
        });
    }
}

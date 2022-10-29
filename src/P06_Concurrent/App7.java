package P06_Concurrent;

import java.util.concurrent.CompletableFuture;

// CompletableFuture의 예외 처리
public class App7 {

    public static void main(String[] args) {
        boolean throwError = true;

        /**
         * exceptionally()
         * 작업들(다수의 조합된 CompletableFuture일 경우에도 가능)의 내부에서 Exception이 throw될 경우 실행된다.
         */
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> {
            if (throwError) {
                throw new IllegalStateException();
            }
            System.out.println("Hello " + Thread.currentThread().getName());
            return "Hello";
        }).exceptionally(exception -> {
            System.out.println(exception);
            return "Error!";
        });

        System.out.println("[exceptionally] " + hello.join());

        /**
         * handle()
         * 정상적으로 종료되었을 때와 에러가 발생했을 때 모두 사용 가능하다.
         * 두 개의 파라미터(1.정상적인 경우의 결과, 2.Exception)를 받아서 하나의 결과를 리턴한다. - `BiFuntion`을 인자로 받음
         */
        CompletableFuture<String> hello2 = CompletableFuture.supplyAsync(() -> {
            if (throwError) {
                throw new IllegalStateException();
            }
            System.out.println("Hello " + Thread.currentThread().getName());
            return "Hello";
        }).handle((successResult, exception) -> {
            if (exception != null) {
                System.out.println(exception);
                return "Error!";
            }
            return successResult;
        });

        System.out.println("[handle] " + hello2.join());
    }
}

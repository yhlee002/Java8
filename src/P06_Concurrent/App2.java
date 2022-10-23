package P06_Concurrent;

// Inturrupt 관련
public class App2 {

    public static void main(String[] args) {

        Thread thread = new Thread(() -> {
            while (true) {
                System.out.println("[Thread2] " + Thread.currentThread().getName());

                try {
                    Thread.sleep(3000L);
                } catch (InterruptedException e) {
                    throw new IllegalStateException(e);
                }
            }
        });

        thread.start();

        System.out.println("[Main] " + Thread.currentThread().getName());
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("[Main] Thread2 is finished.");
    }
}

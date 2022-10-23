package P06_Concurrent;

// Inturrupt 관련
public class App2 {

    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(() -> {
            while (true) {
                System.out.println("[Thread2] " + Thread.currentThread().getName());

                try {
                    Thread.sleep(3000L); // 다른 스레드에게 리소스 사용권이 우선됨
                } catch (InterruptedException e) {
                    System.out.println("inturrupted !");
                    return;
                }
            }
        });

        thread.start();

        System.out.println("[Main] " + Thread.currentThread().getName());
        Thread.sleep(3000L);
        thread.interrupt();
    }
}

package P06_Concurrent;

public class App {

    public static void main(String[] args) {
        // Thread 상속
        Thread1 thread1 = new Thread1();
        thread1.start();

        // Runnable(Functional Interface) 구현
        Thread thread2 = new Thread(() -> {
            try {
                Thread.sleep(1000L); // 다른 스레드에게 리소스 사용권이 우선됨
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("[Thread2] " + Thread.currentThread().getName());
        });

        thread2.start();

        System.out.println("[Main] " + Thread.currentThread().getName());
    }

    static class Thread1 extends Thread {
        @Override
        public void run() {
            System.out.println("[Thread1] " + Thread.currentThread().getName());
        }
    }
}

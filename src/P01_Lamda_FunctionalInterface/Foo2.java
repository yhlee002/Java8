package P01_Lamda_FunctionalInterface;

import java.util.function.Consumer;
import java.util.function.IntConsumer;

public class Foo2 {

    public static void main(String[] args) {
        Foo2 foo = new Foo2();
        foo.run();
    }

    private void run() {
        int baseNumber = 10;

        // Inner Class(Local Class)
        class LocalClass {
            void printBaseNumber(Integer i) {
                int baseNumber = 21;
                System.out.println(i + baseNumber); // 10이 아닌 21이 출력된다. (shadowing)
            }
        }

        // Anonymous Class
        Consumer<Integer> integerConsumer = new Consumer<Integer>() {
            @Override
            public void accept(Integer i) {
                int baseNumber = 31; // 10이 아닌 31이 출력된다. (shadowing)
                System.out.println(i + baseNumber);
            }
        };

        IntConsumer printInt = i -> System.out.println(i + baseNumber);
        printInt.accept(10);
    }
}

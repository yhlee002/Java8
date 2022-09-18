package P01_Lamda_FunctionalInterface;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

class Greeting {
    private String name;

    public Greeting() {
    }

    public String getName() {
        return name;
    }

    public Greeting(String name) {
        this.name = name;
    }

    public String hello(String name) {
        return "hello, " + name;
    }

    public static String hi(String name) {
        return "hi, " + name;
    }
}

public class Foo3 {
    public static void main(String[] args) {
        UnaryOperator<String> hi = s -> "hi, " + s;
        // 위의 람다식에서 'i -> "number"'부분이 아닌,
        // 이 것을 수행할 수 있는 (기존의) 다른 메서드를를참조하는 것이다.

        Greeting gt = new Greeting();
        UnaryOperator<String> hi2 = gt::hello; // colon(:)이 2개면 method reference를 의미한다.
        UnaryOperator<String> hi3 = Greeting::hi; // 클래스 Greeting의 static method 참조

        Supplier<Greeting> greetingSp = Greeting::new;
        Greeting greeting1 = greetingSp.get();
        System.out.println(greeting1.getName()); // null

        String name = "yh";
        Function<String, Greeting> greetingSp2 = Greeting::new;
        Greeting greeting2 = greetingSp2.apply(name);
        System.out.println(greeting2.getName()); // yh

        String[] names = {"yh", "hw", "ml", "nr"};
        Arrays.sort(names, (o1, o2) -> o1.compareTo(o2));
        System.out.println(Arrays.toString(names));

        // 임의의 인스턴스들을 돌면서 compareToIgnoreCase 참조
        Arrays.sort(names, String::compareToIgnoreCase); // 자기 자신 문자열과 인자로 받은 문자열을 비교해주는 메서드 참조
        System.out.println(Arrays.toString(names));
    }
}

package P01_Lamda_FunctionalInterface;

import java.util.function.*;

public class Foo {
    public static void main(String[] args) {
        /* 함수형 인터페이스를 이용한 람다식 사용 방법 */
        RunSomething rs1 = () -> System.out.println("Hello");
        RunSomething rs2 = () -> {
            System.out.println("Hello");
            System.out.println("Java");
        };

        rs1.doIt();
        rs2.doIt();

        RunSomething2 rs3 = number -> number + 10;

        System.out.println(rs3.doIt(1)); // 1
        System.out.println(rs3.doIt(1)); // 1
        System.out.println(rs3.doIt(1)); // 1

        /* 자바에서 제공하는 함수형 인터페이스 function 사용 방법 */

        // 1 - 1. Function을 구현한 클래스(내부적으로 apply 메서드 override) 인스턴스 생성 후 apply 함수 호출
        Plus10 a = new Plus10();
        System.out.println(a.apply(5));

        // 1 - 2. Function<T, R> 타입의 참조변수를 바로 만들 수 있음
        Function<Integer, Integer> b = i -> i + 10;
        Function<Integer, Integer> c = i -> i * 2;

        // 1 - 3. Function의 compose, andThen
        System.out.println(b.compose(c).apply(2)); // 2 * 2 + 10 = 14
        System.out.println(b.andThen(c).apply(2)); // (2 + 10) * 2 = 24

        // 2. BiFunction
        BiFunction<Integer, Integer, Integer> biFunc = (x, y) -> x + y;
        System.out.println(biFunc.apply(1, 2)); // 3

        // 3. Consumer
        Consumer<Integer> print = i -> System.out.println(i);
        print.accept(20); // 20

        // 4. Supplier
        Supplier<Integer> supplier = () -> 10;
        System.out.println(supplier.get()); // 10

        // 5. Predicate
        Predicate<Integer> pred = i -> i > 0;
        Predicate<Integer> pred2 = i -> i > 10;
        Predicate<String> startsWithA = str -> str.startsWith("A");

        // 5 - 1. test
        System.out.println(pred.test(5)); // true
        System.out.println(startsWithA.test("ABC")); // true
        System.out.println(startsWithA.test("BAC")); // false
        // 5 - 2. and
        System.out.println(pred.and(pred2).test(5)); // true && false = false
        // 5 - 2. or
        System.out.println(pred.or(pred2).test(5)); // true || false = true
        // 5 - 2. negate
        System.out.println(pred.negate().test(-1)); // true
        System.out.println(pred.negate().test(10)); // false
    }
}

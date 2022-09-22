package P02_Default_method_Static_method;

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;

/**
 * Java 8부터 추가된 default 메서드들
 */
public class App {
    public static void main(String[] args) {
        List<String> names = new ArrayList<>();
        names.add("yh");
        names.add("hw");
        names.add("moongchi");
        names.add("ladda");
        names.add("ddui");
        names.add("lili");
        names.add("roro");

        /*
        names.forEach(name -> {
            System.out.println(name);
        });
        */

        // 위의 로직과 동일. 인자를 하나 받고 이를 출력하는 메서드 reference
        names.forEach(System.out::println);

        /**
         * Spliterator : 순환에 사용된다.
         * Iterator가 hasNext() 를 통해 반복을 진행하듯이 Spliterator는 tryAdvance() 인자로 반복해서 수행할 로직을 Consumer 형태로 전달해야 한다.
         * Cf. Java Stream의 기반에 사용된다.
         */

        Spliterator<String> spliterator = names.spliterator();
        System.out.println("\n### Spliterator 1");
        while (spliterator.tryAdvance(System.out::println));

        /**
         * trySplit() : 절반으로 요소들을 나눠준다.
         * Cf. 기존의 Spliterator의 절반의 요소들이 반환된다. 나머지 요소들만이 기존 Spliterator에 남는다.
         */

        Spliterator<String> spliterator2 = names.spliterator(); // ['yh', 'hw', 'moongchi', 'ladda', 'ddui', 'lili', 'roro']
        Spliterator<String> s2Sub1 = spliterator2.trySplit(); // ['yh', 'hw', 'moongchi']
        Spliterator<String> s2Sub2 = spliterator2.trySplit(); // ['ladda', 'ddui']
        System.out.println("\n### Spliterator 2");
        while(spliterator2.tryAdvance(System.out::println)); // 남은 요소 : ['lili', 'roro']

        System.out.println("\n### Sub1");
        while(s2Sub1.tryAdvance(System.out::println));
        System.out.println("\n### Sub2");
        while(s2Sub2.tryAdvance(System.out::println));

        /**
         * stream() : Collection의 모든 구현체들이 가지는 default method이다.
         * spliterator를 인자로 받는 것을 볼 수 있다.
         */
    }
}

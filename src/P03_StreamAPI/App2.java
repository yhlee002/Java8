package P03_StreamAPI;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class App2 {
    public static void main(String[] args) {
        List<OnlineClass> springClasses = new ArrayList<>();
        springClasses.add(new OnlineClass(1, "spring boot", true));
        springClasses.add(new OnlineClass(2, "spring data jpa", true));
        springClasses.add(new OnlineClass(3, "spring mvc", false));
        springClasses.add(new OnlineClass(4, "spring core", false));
        springClasses.add(new OnlineClass(5, "rest api development", false));

        List<OnlineClass> javaClasses = new ArrayList<>();
        javaClasses.add(new OnlineClass(6, "The Java, Test", true));
        javaClasses.add(new OnlineClass(7, "The Java, Code manipulation", true));
        javaClasses.add(new OnlineClass(8, "The Java, 8 to 11", false));

        List<List<OnlineClass>> events = new ArrayList<>();
        events.add(springClasses);
        events.add(javaClasses);

        /* 스트림 주요 기능 - 메서드 사용 예 */
        System.out.println("1. spring으로 시작하는 수업");
        springClasses.stream()
                .filter(oc -> oc.getTitle().startsWith("spring"))
                .forEach(r -> System.out.println(r.getTitle()));

        System.out.println("\n2. close 되지 않은 수업");
        springClasses.stream()
                .filter(oc -> oc.isClosed())
                .forEach(r -> System.out.println(r.getTitle()));

        // 위 스트림을 method reference 를 이용해 수정한 버전
        springClasses.stream()
                .filter(Predicate.not(OnlineClass::isClosed)) // !OnlineClass::isClosed가 되지 않는다.
                .forEach(r -> System.out.println(r.getTitle()));

        System.out.println("\n3. 수업 이름만 모아서 스트림 만들기");
        springClasses.stream()
                .map(OnlineClass::getTitle) // getTitle()에 의해 스트림의 처리 데이터 타입이 OnlineClass -> String으로 변환된다.
                .forEach(System.out::println);

        System.out.println("\n4. 두 수업 목록에 들어있는 모든 수업 아이디 출력");
        System.out.println("(1)");
        events.stream()
                .flatMap(new Function<List<OnlineClass>, Stream<?>>() {
                    @Override
                    public Stream<?> apply(List<OnlineClass> onlineClasses) {
                        return onlineClasses.stream().map(OnlineClass::getTitle);
                    }
                })
                .forEach(System.out::println);

        // 위 fltMap(List<OnlineClass> -> String)과 달리 List<OnlineClass> -> OnlineClass로만 바꾸도록 수정한 버전
        System.out.println("(2)");
        events.stream()
                .flatMap(list -> list.stream())
                .forEach(oc -> System.out.println(oc.getTitle()));

        System.out.println("(3)");
        events.stream()
                .flatMap(Collection::stream)
                .forEach(oc -> System.out.println(oc.getTitle()));

        System.out.println("\n5. 10부터 1씩 증가하는 무제한 스트림 중에서 앞에 10개 빼고 최대 10개까지만");
        Stream.iterate(10, i -> i + 1) // 10부터 1씩 증가하는 무제한 스트림
                .skip(10) // 10개를 스킵
                .limit(10) // 10개까지만 처리
                .forEach(System.out::println);

        System.out.println("\n6. 자바 수업 중에 Test가 들어있는 수업이 있는지 확인");
        boolean result6 = javaClasses.stream().anyMatch(oc -> oc.getTitle().contains("Test"));
        System.out.println(result6);

        System.out.println("\n7 .스프링 수업 중에 제목에 spring이 들어간 것만 모아서 List로 만들기");
        // 1) 초기 작성(List<OnlineClass> 타입으로 처리)
        System.out.println("(1)");
        List<OnlineClass> result7 = springClasses.stream()
                .filter(oc -> oc.getTitle().startsWith("spring"))
                .collect(Collectors.toList());
        result7.forEach(oc -> System.out.println(oc.getTitle()));

        // 2) filter, map 순으로 사용
        List<String> result7_2 = springClasses.stream()
                .filter(oc -> oc.getTitle().startsWith("spring"))
                .map(oc -> oc.getTitle())
                .collect(Collectors.toList());

        // 3) map, filter 순으로 사용
        List<String> result7_3 = springClasses.stream()
                .map(oc -> oc.getTitle())
                .filter(t -> t.startsWith("spring"))
                .collect(Collectors.toList());
    }
}

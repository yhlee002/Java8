package P03_StreamAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        List<OnlineClass> result1 = springClasses.stream()
                .filter(oc -> oc.getTitle().startsWith("spring"))
                .collect(Collectors.toList());
        result1.forEach(r -> System.out.println(r.getTitle()));

        System.out.println("\n2. close 되지 않은 수업");
        List<OnlineClass> result2 = springClasses.stream()
                .filter(oc -> oc.isClosed())
                .collect(Collectors.toList());
        result2.forEach(r -> System.out.println(r.getTitle()));

        System.out.println("\n3. 수업 이름만 모아서 스트림 만들기");
        List<String> result3 = springClasses.stream()
                .map(oc -> oc.getTitle())
                .collect(Collectors.toList());
        result3.forEach(System.out::println);

        System.out.println("\n4. 두 수업 목록에 들어있는 모든 수업 아이디 출력");
        events.stream().map()

        System.out.println("\n5. 10부터 1씩 증가하는 무제한 스트림 중에서 앞에 10개 빼고 최대 10개까지만");


        System.out.println("\n6. 자바 수업 중에 Test가 들어있는 수업이 있는지 확인");
        long cnt = javaClasses.stream().filter(oc -> oc.getTitle().indexOf("Test") != -1).count();
        System.out.println(cnt);

        System.out.println("\n7 .스프링 수업 중에 제목에 spring이 들어간 것만 모아서 List로 만들기");
        List<OnlineClass> result7 = springClasses.stream()
                .filter(oc -> oc.getTitle().startsWith("spring"))
                .collect(Collectors.toList());
        result7.forEach(oc -> System.out.println(oc.getTitle()));
    }
}

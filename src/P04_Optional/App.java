package P04_Optional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class App {
    public static void main(String[] args) {
        List<OnlineClass> springClasses = new ArrayList<>();
        springClasses.add(new OnlineClass(1, "spring boot", true));
//        springClasses.add(new OnlineClass(2, "spring data jpa", true));
//        springClasses.add(new OnlineClass(3, "spring mvc", false));
//        springClasses.add(new OnlineClass(4, "spring core", false));
        springClasses.add(new OnlineClass(5, "rest api development", false));

        Optional<OnlineClass> optional = springClasses.stream()
                .filter(oc -> oc.getTitle().startsWith("spring"))
                .findFirst();

        // isPresent() Cf. isEmpty()와 반대
        boolean present = optional.isPresent();
        System.out.println("isPresent? " + present);

        // map으로부터 반환받은 Optional 인스턴스 속의 값도 Optional일 경우 => 두번 꺼내야 함
        Optional<Optional<Progress>> progress = optional.map(OnlineClass::getProgress);
        Optional<Progress> progress1 = progress.orElse(Optional.empty());
        System.out.println("map result - isPresent? " + progress1.isPresent());

        // flatMap => Optional 속의 값이 역시나 Optional일 경우 한번 더 처리해줌
        Optional<Progress> progress2 = optional.flatMap(OnlineClass::getProgress);
        System.out.println("flatMap result - isPresent? " + progress2.isPresent());



        Optional<OnlineClass> optional2 = springClasses.stream()
                .filter(oc -> oc.getTitle().startsWith("jpa"))
                .findFirst();

        // get()
        OnlineClass onlineClass2 = optional2.get(); // 없는 경우 NoSuchElementException 발생
        System.out.println(onlineClass2.getTitle());

        // orElse(), orElseGet(), orElseThrow()
        optional2.ifPresent(o -> System.out.println(o.getTitle()));
//        OnlineClass onlineClass3 = optional2.orElse(createNewClass()); // 인자로 메서드를 받을 경우 반드시 한 번은 실행된다.
        OnlineClass onlineClass3 = optional2.orElseGet(App::createNewClass);
        optional2.orElseThrow(IllegalStateException::new); // 없는 경우 IllegalStateException 발생
        System.out.println(onlineClass3.getTitle());


    }

    private static OnlineClass createNewClass() {
        System.out.println("start createNewClass");
        return new OnlineClass(10, "New Class", false);
    }
}

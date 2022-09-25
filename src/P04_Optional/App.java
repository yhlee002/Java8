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
        boolean present = optional.isPresent();
        System.out.println("isPresent? " + present);

        OnlineClass onlineClass = optional.get();
        System.out.println(onlineClass.getTitle());

        Optional<OnlineClass> optional2 = springClasses.stream()
                .filter(oc -> oc.getTitle().startsWith("jpa"))
                .findFirst();

        /*
        OnlineClass onlineClass2 = optional2.get();
        System.out.println(onlineClass2.getTitle()); // NoSuchElementException 발생
        */

        optional2.ifPresent(o -> System.out.println(o.getTitle()));
        OnlineClass onlineClass3 = optional2.orElse(createNewClass());
        System.out.println(onlineClass3.getTitle()); // New Class

    }

    private static OnlineClass createNewClass() {
        return new OnlineClass(10, "New Class", false);
    }
}

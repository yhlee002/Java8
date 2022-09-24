package P02_Default_method_Static_method;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class App2 {
    public static void main(String[] args) {
        String[] strings = new String[]{"yh", "hw", "molang"};
        List<String> names = new ArrayList<>(List.of(strings));
        List<String> limitResult = names
                .stream().map(name -> {
                    System.out.println(name); // yh
                    return name.toUpperCase();
                })
                .limit(1) // 1번만 실행
                .collect(Collectors.toList()); // 리턴값을 List 타입으로 모은다.

        limitResult.forEach(System.out::println); // YH
    }
}

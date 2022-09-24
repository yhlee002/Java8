package P03_StreamAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) {
        String[] strings = new String[]{"yh", "hw", "molang"};
        List<String> names = new ArrayList<>(List.of(strings));

        /* 중개 오퍼레이터와 종료 오퍼레이터 */
        List<String> limitResult = names
                .stream().map(name -> { // 스트림 속 반복(중개 오퍼레이터)
                    System.out.println(name); // yh
                    return name.toUpperCase();
                })
                .limit(1) // 1번만 실행(중개 오퍼레이터)
                .collect(Collectors.toList()); // 리턴값을 List 타입으로 모음(종료 오퍼레이터)

        limitResult.forEach(System.out::println); // YH

        /* 병렬 처리 - parellelStream() */
        List<String> parellelList = names.parallelStream().map(s -> {
            System.out.println(s + " " + Thread.currentThread().getName());
            return s.toUpperCase();
        }).collect(Collectors.toList());
        parellelList.forEach(System.out::println);
    }
}

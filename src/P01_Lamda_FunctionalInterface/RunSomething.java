package P01_Lamda_FunctionalInterface;

@FunctionalInterface // anotation을 붙이면 추상 메서드가 여러 개일 경우 컴파일러에서 에러 발생
public interface RunSomething {
    // 추상 메서드가 하나만 있으면 그게 함수형 인터페이스(두개 이상 있으면 아님)
    void doIt(); // 인터페이스에서는 abstract 키워드 생략 가능

    // 인터페이스에 static 메서드나 public 키워드도 생략 가능 (static이 생략되면 어떻게 구분하나? -> 내부 로직이 있으면?
    static void printName() {
        System.out.println("yh");
    }

    // 인터페이스에 default 메서드 정의 가능
    default void printAge() {
        System.out.println("28");
    }
}
package P02_Default_method_Static_method;

public interface Foo {
    void printName();

    /**
     * @implSpec getName()으로부터 반환된 문자열을 대문자로 출력한다.
     */
    default void printNameUpperCase() {
        System.out.println(getName().toUpperCase());
    }

    String getName();

    static void printFoo() {
        System.out.println("Foo");
    }
}

package P02_Default_method_Static_method;

public interface Foo {
    void printName();

    default void printNameUpperCase() {
        System.out.println("FOO");
    }

}

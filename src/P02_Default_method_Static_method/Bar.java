package P02_Default_method_Static_method;

public interface Bar {
    default void printNameUpperCase() {
        System.out.println("FOO");
    }
}

package P02_Default_method_Static_method;

public class FooTest {
  public static void main(String[] args) {
    Foo foo = new DefaultFoo("myname");
    foo.printName();
    foo.printNameUpperCase();
  }
}

package P02_Default_method_Static_method;

public class FooTest {
  public static void main(String[] args) {
    Foo foo = new DefaultFoo("myname");
    foo.printName();
    foo.printNameUpperCase(); // default method(DefaultFoo 클래스 내부에는 관련 로직이 없어도 해당 기능을 가짐)
    Foo.printFoo(); // static method
  }
}

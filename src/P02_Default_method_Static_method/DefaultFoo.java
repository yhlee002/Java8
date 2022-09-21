package P02_Default_method_Static_method;

public class DefaultFoo implements Foo {

    String name;

    public DefaultFoo(String name) {
        this.name = name;
    }

    @Override
    public void printName() {
        System.out.println(this.name);
    }

    public String getName() {
        return this.name;
    }
}

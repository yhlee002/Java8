# 1. Functional Interface & Lambda

## 파일 순서

### Foo.java
1. 함수형 인터페이스를 이용한 람다식 사용 방법
2. 자바에서 제공하는 함수형 인터페이스 function 사용 방법
#### 관련 파일(클래스)
1. doSomething : 함수형 인터페이스 문법 정리
> Cf. 함수형 인터페이스는 이전부터 사용됐으나(Anonymous inner class) 자바8에 들어서 새롭게 명명

2. Plus10 : 함수형 인터페이스인 Function을 구현해 추상 메서드 apply를 override 하는 방식

### Foo2.java
1. local variable 참조(inner class/anonymous class vs Lambda)

### Foo3.java
1. method reference


## 람다식 특징
  메서드에 인자로 전달하거나 그 자체로 리턴할 수 있다. 이를 다른 말로는 "함수를 first class object로 사용할 수 있다."고 한다.


## 람다식 조건
1. 순수 함수(Pure function)여야 한다. - 함수 밖의 값을 변경하지 못한다.

2. 고차 함수(Higher-Order function)여야 한다. - 함수가 함수를 매개변수로 받을 수 있고 함수를 리턴할 수도 있다.
   - 인터페이스 `Function`의 메서드들과 관련(`compose()`, `andThen()` 등)
> Cf.함수 안에서 함수 밖의 Final이 아닌 변수를 참조하여 값을 **변경**하는 경우에는 `순수 함수`라고 볼 수 없다.


## Java가 기본적으로 제공하는 함수형 인터페이스
- Function<T, R> : 하나의 매개변수를 가짐
  - `R apply(T t)` : T 타입의 값을 받아 R타입의 값 반환
  - `R compose(Function<? super V,? extends T> before)`
  - `R andThen(Function<? super V,? extends T> before)`
  ```java
  public class Test {
    public static void main(String[] args) {
      Function<Integer, Integer> func = (i) -> i + 10;
      func.apply(20); // 30
    
      Function<Integer, Integer> func2 = (i) -> i * 2;
      func.compose(func2).apply(1); // 1 * 2 + 10 = 12
      func.andThen(func2).apply(1); // (1 + 10) * 2 = 22
    }
  }
  ```
- BiFunction<T, U, R> : 두 개의 매개변수를 가짐
  - `R apply(T t, U u)` : T 타입의 값과 U 타입의 값을 받아 R타입의 값 반환
  ```java
  import java.util.function.BiFunction; 
    
  public class Test {
    public static void main(String[] args){
      BiFunction<Integer, Integer, Integer> biFunc = (x, y) -> x + y;
      System.out.println(biFunc.apply(1, 2)); // 3
    }
  }  
  ```  

- Consumer<T> : 반환값이 없음(T 타입을 받아 아무값도 리턴하지 않음)
  - `void accept(T t)` : T 타입의 값을 받음
  - `default Consumer<T> andThen(Consumer<? super T> after)` : 해당 함수의 실행 결과를 after의 인자로 전달
    ```java
    import java.util.function.Consumer;     
    
    public class Test {
       public static void main(String[] args) {
          Consumer<Integer> print = (i) -> System.out.println(i);
          print.accept(20);
    
          Consumer<Integer> print2 = i -> System.out.println(i + 10);
          print.andThen(print2).accept(1); // 1 \n 11
       }
    }
    ```

- Supplier<T> : 매개변수가 없음(항상 T 타입의 값을 제공)
  - `T get()`
  ```java
  import java.util.function.Supplier;
  
  public class Test {
    public static void main(String[] args) {
      Supplier<Integer> supp = () -> 10;
      System.out.println(supp.get()); // 10
    }
  }
  ```
- Predicate<T> : T 타입의 값을 받아 boolean을 반환
  - `boolean test(T t)`
  - `And`
  - `Or`
  - `Negate` : NOT의 의미
  ```java
  import java.util.function.Predicate;
  
  public class Test {
    public static void main(String[] args) {
      Predicate<Integer> pred = i -> i > 0;
      Predicate<Integer> pred2 = i -> i > 10;
      // test
      System.out.println(pred.test(5)); // true
      // and, or, negate
      System.out.println(pred.and(pred2).test(5)); // true && false = false
      System.out.println(pred.or(pred2).test(5)); // true || false = true
      System.out.println(pred.negate().test(-1)); // true
      System.out.println(pred.negate().test(10)); // false
    }
  }
  ```
- UnaryOperator<T> : T 타입의 값을 받아 T 타입의 값을 반환한다. (Function과 유사하나 입력값과 반환값이 같은 경우 사용)
> Cf. Function<T, T> 와 동일하다.
- BinaryOperator<T> : T 타입의 값 두개를 받아 T 타입의 값을 반환하다. (BiFunction과 유사하나 입력값 2개와 반환값이 모두 같은 경우 사용)
> Cf. BiFunction<T, T, T> 와 동일하다.


## Function
```java
@FunctionalInterface
public interface Function<T,R> {
   
}
```
Java가 기본적으로 제공하는 함수형 인터페이스. Java에서 제공하는 함수형 인터페이스 기능들은 `java.lang.function` 패키지에 주로 저장되어 있다.

### 주요 메서드
#### R apply(T t)
   함수형 인터페이스 `Function`의 유일한 추상 메서드. 
Cf. `R`과 `T`을 보면 매개변수와 반환 타입이 다를 수 있음을 알 수 있다.

#### compose(Function<? super V,? extends T> before)
   인자로 받은 Function의 결과를 해당 Function의 인자로 전달한다.
> Cf. `뒤에 실행될 함수.compose(먼저 실행될 함수).apply(인자)` 형태로 사용하며, 주어진 `인자`는 먼저 실행되는 항수의 인자로 사용된다.

#### andThen(Function<? super V,? extends T> after)
   해당 Function의 결과를 인자로 받은 Function의 인자로 전달한다.
> Cf. `앞에 실행될 함수.andThen(뒤에 실행될 함수).apply(인자)` 형태로 사용하며, 주어진 `인자`는 먼저 실행되는 항수의 인자로 사용된다.

---
### 사용방법
1. 인터페이스 `Function<T, R>`를 상속하는 클래스 사용
   1. 내부적으로 `Function<T, R>`의 추상 메서드인 `R apply(T t)` 오버라이딩
   2. 해당 클래스의 인스턴스를 생성해 인스턴스의 `apply` 호출(클래스의 매개변수 타입과 반환 타입이 일치해야 한다.)
  ```java
class TypeA implements Function<Integer, Integer> {
    @Override
    public Integer apply(Integer n) {
        return n + 10;
    }
}

public class Test {
   public static void main(String[]args){
        TypeA a = new TypeA();
        a.apply();
   }
}
```

2. 인터페이스 `Function<T, R>` 타입의 참조변수에 람다식 작성(매개변수 타입과 반환 타입을 알아보기 쉽다.)
```java
public class Test {
   public static void main(String[] args) {
      Function<Integer, Integer> b = i -> i + 10;
      Function<Integer, Integer> c = i -> i * 2;
   }
}
```

## Lambda Expression
### 기본 문법
- 반환 타입과 메서드명 생략
- 매개변수 타입은 유추가 가능한 경우 생략 가능(대부분의 경우 생략 가능)
- 괄호 `{}`안에 식이 한 줄이라면 괄호 생략 가능
- 괄호 안에 식이 한 줄이고 반환값이 있다면 return 생략 + 괄호 생략(return이 있으면 반드시 괄호 필요)

### local variable 참조
1. Effective final
> Java8 이전에는 자신의 scope 외부에 있는 local vairable 중에서는 final 변수만 참조할 수 있었다.
> Java8부터 생긴 기능으로, 이 변수의 final이 붙지 않아도 참조할 수 있는 경우가 있는데, 이는 해당 변수가 실질적으로 final일 때에 해당한다.
> final 키워드는 붙지 않았더라도 변수의 값이 변경되지 않으면 사용할 수 있다. 컴파일러는 해당 변수의 값을 변경하려하면 에러를 반환하게 된다.
> 이렇게 final이 붙지 않은 변수의 값이 변경되지 않는 것을 'Effective final'라고 한다.

2. Shadowing 
> Inner Class와 Anonymous Class의 local variable은 Shadowing이 일어나지만, Lambda는 일어나지 않는다.
> 외부에 있는 변수와 같은 이름의 변수 사용 불가(같은 scope임을 의미한다. 즉, 별도의 scope를 가지지 않는다.)

 
## Method Reference
람다가 하는 일이 기존 메서드 또는 생성자를 호출하는 거라면, 메서드 레퍼런스를 사용해 매우 간결하게 표현 가능
- static 메서드 참조 -> `Type::static-method-name`
- 특정 객체의 인스턴스 메서드 참조 -> `instance::method-name`
- 임의 객체의 인스턴스 메서드 참조 -> `Type::instance`
  - 특정 타입의 불특정 인스턴스의 특정 메서드를 참조
    ```java
    public class Test {
        public static void main(String[] args){
            String[] names = {"yh", "hw", "ml", "nr"};
            Arrays.sort(names, (o1, o2) -> o1.compareTo(o2));
            System.out.println(Arrays.toString(names));

            // 임의의 인스턴스들을 돌면서 compareToIgnoreCase 참조
            Arrays.sort(names, String::compareToIgnoreCase); // 자기 자신 문자열과 인자로 받은 문자열을 비교해주는 메서드 참조
            System.out.println(Arrays.toString(names));
        }
    }
    ```
- 생성자 참조 -> `Type::new`


```java
import java.util.function.UnaryOperator;

public class Test {
    public static void main(String[] args) {
        UnaryOperator<String> hello = s -> "hello, " + s;
        // 해당 기능을 하는 메서드가 클래스 Greeting 내부에 있는 메서드 hello라면 아래와 같이 참조
        Greeting greeting = new Greeting();
        UnaryOperator<String> hello2 = greeting::hello;
        System.out.println(hello2.apply("yh"));
        // static 메서드를 참조할 경우에는 '타입::메서드명'
    }
}
```

만약 클래스의 인스턴스를 얻는 방법을 메서드 참조로 수행하고자 한다면 아래와 같이 할 수 있다.
```java
public class Test {
    public static void main(String[] args) {
        Greeting greeting0 = new Greeting();
        
        // 매개변수가 없는 생성자 참조 -> Supplier Type
        Supplier<Greeting> greetingSp = Greeting::new;
        Greeting greeting1 = greetingSp.get();
        System.out.println(greeting1.getName()); // null
        
        // 매개변수가 하나 있는 생성자 참조 -> Function Type
        String name = "yh";
        Function<String, Greeting> greetingSp2 = Greeting::new;
        Greeting greeting2 = greetingSp2.apply(name);
        System.out.println(greeting2.getName()); // yh
    }
}
```

# 1. Functional Interface & Lambda

## 파일 순서

1. Foo
   1. 함수형 인터페이스를 이용한 람다식 사용 방법
   2. 자바에서 제공하는 함수형 인터페이스 function 사용 방법

2. doSomething : 함수형 인터페이스 문법 정리
> Cf. 함수형 인터페이스는 이전부터 사용됐으나(Anonymous inner class) 자바8에 들어서 새롭게 명명

3. Plus10 : 함수형 인터페이스인 Function을 구현해 추상 메서드 apply를 override 하는 방식


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

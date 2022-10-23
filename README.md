# 1. Functional Interface & Lambda

## 파일 순서

### Foo.java

1. 함수형 인터페이스를 이용한 람다식 사용 방법
2. 자바에서 제공하는 함수형 인터페이스 function 사용 방법
3. Java가 제공하는 기본적인 함수형 인터페이스
   ex. Function, BiFunction, UnaryOperator, BinaryOperator, Supplier, Consumer, Predicate

#### 관련 파일(클래스)

1. doSomething : 함수형 인터페이스 문법 정리

> Cf. 함수형 인터페이스는 이전부터 사용됐으나(Anonymous inner class) 자바8에 들어서 새롭게 명명

2. Plus10 : 함수형 인터페이스인 Function을 구현해 추상 메서드 apply를 override 하는 방식

### Foo2.java

1. local variable 참조(inner class/anonymous class vs Lambda)

### Foo3.java

1. method reference

### RunSomething, RunSomething2

1. 함수형 인터페이스 연습

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
public interface Function<T, R> {

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
    public static void main(String[] args) {
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

# 2. 인터페이스의 Default 메서드와 Static 메서드

## 파일 순서

### Foo

인터페이스

### DefaultFoo

인터페이스를 구현한 클래스(default 메서드는 다시 정의하지 않는다.)

### FooTest

인터페이스를 구현한 클래스의 인스턴스를 생성하여 default 메서드 출력

### Bar

Foo와 같은 반환타입, 같은 매개변수, 같은 이름을 가진 메서드(-> 사실상 같은 메서드)를 가진다. (둘을 같이 구현하는 경우 같은 메서드를 사용하려고 할 경우 직접 구현해야 한다.)
--

### App

Java 8에서부터 제공하는 default 메서드들

## Default Method

> default 메서드들은 내용을 구현할 수 있다. 이는 이를 구현하지 못한 클래스들의 컴파일 에러를 방지한다.

인터페이스를 구현하는 모든 클래스들이 공통적인 기능을 가지게 하기 위해 메서드를 추가할 수 있다.
그런데 인터페이스 생성 이후 또 다른 추상 메서드들을 추가하게 되면 이를 미처 구현하지 못한 모든 클래스들에 컬파일 에러가 나게 된다.

- 앞에 default 키워드를 붙여야 한다.
- 해당 인터페이스를 구현한 클래스 내부에서 해당 메서드를 재정의(Override)할 수 있다.
- 내부에서 재정의 가능한 메서드(추상 메서드)를 호출하는 경우에는 반드시 이를 문서화하여야 한다. (어떤 값을 반환하는지 등에 대해)
    - JavaDoc(`/** */`)에서 제공하는 @implSpec 등의 annotation을 사용할 수 있다.
- 모든 클래스가 구현하는 toString()은 default 메서드(혹은 abstract 메서드)로 정의할 수 있다. 그 외의 Object 클래스가 제공하는 메서드들은 불가능하다. (equals 등)
- 본인이 수정할 수 있는 사용자 정의 인터페이스에서만 만들 수 있다. (라이브러리의 메서드는 불가능)
- 해당 인터페이스를 상속하는 다른 인터페이스에서 재정의할 수 있다.
  > Cf. 같은 로직이 아닌 다른 로직을 전달하고 싶다면 이를 추상메서드로 재정의하거나 내용만 다르게 작성할 수도 있다.
- 같은 이름의 default 메서드를 가진 두 개 이상의 인터페이스를 구현한 클래스의 경우에는 직접 오버라이딩해서 쓸 수 밖에 없다.
  > Cf. 둘 중 어떤 인터페이스의 default 메서드를 쓸 것인가를 정할 수 없어 컴파일 에러가 발생한다.

## Static Method

> 특정 타입 관련 유틸리티나 헬퍼 메서드를 제공하고자 할 때 static 메서드를 작성할 수 있다.

```java
public interface Foo {
    static void printFoo() {
        System.out.println("Foo");
    }
}

public class App {
    public static void main(String[] args) {
        Foo.printFoo();
    }
}
```

## Java 8에 추가된 default 메서드

### Iterable

- forEach()
- spliterator()
    - Spliterator 클래스 : 순환에 사용
        * Iterator가 hasNext() 를 통해 반복을 진행하듯이 Spliterator는 tryAdvance() 인자로 반복해서 수행할 로직을 Consumer 형태로 전달해야 한다.
        * Cf. Java Stream의 기반에 사용된다.
        * trySplit() : 절반으로 요소들을 나눠준다. (기존의 Spliterator의 절반의 요소들이 반환된다. 나머지 요소들만이 기존 Spliterator에 남는다.)

### Collection

- stream(), parallelStream()
    - stream() - Collection의 모든 구현체들이 가지는 default method이다. spliterator가 인자로 전달되는 것을 볼 수 있다.
  ```java
  // Collection 인터페이스 내부
  @Contract(pure = true)
  default Stream<E> stream() {
      return StreamSupport.stream(spliterator(), parellel);
  }
    ```
- spliterator()

### Comparable

- reversed()
- thenComparing()
- static reverseOrder(), naturalOrder()
- static nullsFirst(), nullsLast()
- static comparing()

--

# 3. Stream API

## 파일 순서

### App

중개 오퍼레이터와 종료 오퍼레이터 비교

### App2

`OnlineClass`를 이용한 Stream 주요 기능 살펴보기

### OnlineClass

`App2`에 사용되는 사용자 정의 클래스

## 특징

- 연속된 데이터를 처리하는 Operator들의 모음. 그 자체로 데이터가 아니다.
  Cf. Collection이 데이터를 가지고 있다면 이를 소스로 처리하는 역할을 수행
- 처리하는 데이터 소스를 변경하지 않는다.
- Stream으로 처리하는 데이터는 오직 한번만 처리한다.
- 무제한일 수 있다. (Short Circuit 메서드 - 제한을 걸 수 있다. - 를 이용해 제한할 수 있다.)
- 중개 오퍼레이션들은 기본적으로 'lazy'하다.
- 병렬적으로 데이터를 처리할 수 있다. - `parellelStream()`
    - 내부에서 Spliterator를 사용해 Collection 데이터를 반으로 나누고, 각각을 병행적으로 처리해 마지막에 데이터를 합산하는 작업을 한다.
    - 병렬 처리가 무조건 더 빨라 좋기만 한 것은 아니다. - 스레드 생성, 데이터 수집, 스레드 간의 컨텍스트 스위칭 비용 등 비용이 더 들 수도 있다.
    - Cf. 데이터가 너무 방대하게 큰 경우에는 이 방법이 더 유용하다.

```java
import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        String[] strings = new String[]{'yh', 'hw', 'molang'};
        List<String> names = new ArrayList<>(List.asList(strings));
        names.forEach(System.out::println);
    }
}
```

### 종류

#### Intermediate operation(중개 오퍼레이터)

계속 이어진다. 즉, Stream 타입을 반환한다. Ex. `map()`, `limit()`, `sorted` 등

#### Terminal Operation(종료 오퍼레이터)

Stream 타입이 아닌 타입을 반환한다. Ex. `forEach()`, `allMatch()`, `collect()` 등

#### 예 - map(), limit(), collect(), ...

```java
import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        String[] strings = new String[]{"yh", "hw", "molang"};
        List<String> names = new ArrayList<>(List.of(strings));

        // 중개 오퍼레이터만 사용한 경우 실행되지 않는다.
        names.stream().map(name -> {
            System.out.println(name);
            return name.toUpperCase();
        });

        // 종료 오퍼레이터가 함께 올 때 중개 오퍼레이터도 실행이 된다.
        List<String> limitResult = names
                .stream().map(name -> {
                    System.out.println(name); // yh
                    return name.toUpperCase();
                })
                .limit(1) // 1번만 실행한다.
                .collect(Collectors.toList()); // Stream 외의 타입 반환, 종료 오퍼레이션

        limitResult.forEach(System.out::println);
    }
}
```

### 주요 메서드

#### map

Stream 클래스에서 제공하는 메서드
Stream의 각 요소들을 반복해서 처리

#### forEach

Stream 혹은 Iterable 클래스에서 제공하는 메서드
Stream 혹은 List의 각 요소들을 반복

#### collect

Stream 클래스에서 제공하는 메서드
특정 타입으로 데이터를 모을 때 사용(종료 오퍼레이터에 해당)
Cf. List<String> 타입으로 모으고자 할 때는 `collect(Collectors.toList())` 과 같이 작성할 수 있다.

#### limit

Stream 클래스에서 제공하는 메서드
몇 개의 데이터만 처리할 것인지 제한을 둘 때 사용

#### skip

Stream 클래스에서 제공하는 메서드
몇 개의 데이터를 스킵할 것인지 제한을 둘 때 사용

#### anyMatch

Stream 클래스에서 제공하는 메서드
조건에 매칭되는 결과가 하나라도 있는지 확인할 때 사용
boolean 타입을 반환한다. (종료 오퍼레이터에 해당)
예를 들어 Java 강의 중 강의명에 'Test'가 들어가는 강의가 있는지 확인하고자 할 때는 `javaClasses.stream().anyMatch(oc -> oc.contains("Test"));`과 같이 작성할
수 있다.

#### Stream.iterate

Stream 클래스에서 제공하는 `static` 메서드
데이터로부터 스트림을 만드는 것이 아닌 직접 스트림을 만든다. 때문에 초깃값(seed)가 필요하다.
예를 들어 10부터 1씩 증가하는 스트림을 생성하고자 한다면 `Stream.iterate(10, i -> i + 1)`와 같이 작성할 수 있다.

### Stream Pipeline

0 ~ n개의 중개 오퍼레이션과 하나의 종료 오퍼레이션으로 구성된다.
스트림이 처리해야하는 데이터는 종료 오퍼레이션을 실행할 때에만 처리한다.
파이프라인 내에서 작업을 수행하는 오퍼레이터들이 취급하는 데이터 타입은 이전 오퍼레이터에 의해 달라질 수 있다.

```java
import P03_StreamAPI.OnlineClass;

import java.util.ArrayList;
import java.util.Collection;

public class Test {
    public static void main(String[] args) {
        List<OnlineClass> classes = new ArrayList<>();
        classes.add(1, "class 1", true);
        classes.add(2, "class 2", true);

        List<OnlineClass> classes2 = new ArrayList<>();
        classes2.add(3, "class 3", true);
        classes2.add(4, "class 4", true);

        List << List < OnlineClass >> list = new ArrayList<>();
        list.add(classes);
        list.add(classes2);

        classes.stream()
                .map(oc -> oc.getTitle()) // 취급 타입이 OlineClass -> String
                .forEach(System.out::println);

        list.stream()
                .flatMap(Collection::stream) // 취급 타입이 List<OnlineClass> -> OnlineClass
                .forEach(System.out::println);
    }
}
```

# Optional

> 오직 하나의 값을 가지고 있을 수도 없을 수도 있는 컨테이너

Java 8 이전에는 제대로 된 값을 리턴할 수 없을 때 할 수 있는 방법은 두 가지 밖에 없었다. `null`을 반환하거나 Error를 반환하는 것이 그것이다.

- 에러를 발생시키는 경우에는 Java에서 Stack Track를 출력하기 위해 리소스를 쓰게 되므로 좋은 방법이 아니다.
    - Cf. 로직을 처리할 때 에러를 발생시키는 것은 좋은 습관이 아니다.
- `null`을 반환하는 경우에는 이를 참조하는 코드에서 값이 `null`일 수 있는 점에 유의해 주의해야 한다.
- Java 8부터는 `Optional`을 사용해 참조하는 코드에게 명시적으로 빈 값일 수 있음을 알려주고, 빈 값의 경우에 대한 처리를 강제한다.

```java
import java.util.Optional;

public class OnlineClass {
    public Optional<Progress> getProgress() {
        return Optional.ofNullable(progress);
    }
}
```

## 주의할 점

- 리턴 값으로만 쓰기를 권장한다. (메서드 매개변수 타입, `Map`의 키 타입, 인스턴스 필드 타입으로 사용하지 않기를 권고)
    - Cf. Map의 특징 중 가장 중요한 점은 key는 `null`이 될 수 없다는 점이다.
- `Optional<T>` 타입을 반환하는 경우에서 `null`을 반환하지 말아야 한다. 즉, 해당 값이 `null`일 지라도 `null`이 아닌 `Optional.empty()`를 반환해야 한다.
    - 이를 참조하는 곳에서 `NullPointerException`이 발생하기 때문이다.
- Contianer 성격의 타입들을 Optional로 다시금 감싸지 않아야 한다. - `Collection`, `Map`, `Stream`, `Array`, `Optional`을 `Optional`로 감싸지 말아야
  한다.
    - 이 들은 비어있음을 표현할 수 있는 기능이 있다. 이 기능을 사용해야 한다.

## 주요 메서드

### Optional.of(), Optional.ofNullable(), Optional.empty()

`Optional`을 만든다.

### isPresent(), isEmpty()

`Optional`에 값이 있는지 없는지 확인하는 메서드로, `isEmpty()`는 Java 11부터 제공한다.

### get()

`Optional`에 있는 값을 가져온다.
비어있는 경우에는 `NoSuchElementException`이 발생한다.

### ifPresent(Consumer)

`Optional`에 값이 있는 경우에는 그 값을 인자로 받아 수행할 내용(`Consumer` 형태)가 들어간다.

```java
import P04_Optional.OnlineClass;

public class Test {
    public static void main(String[] args) {
        List<OnlineClass> springClasses = new ArrayList<>();
        springClasses.add(new OnlineClass(1, "spring boot", true));
        springClasses.add(new OnlineClass(5, "rest api development", false));

        Optional<OnlineClass> optional = springClasses.stream()
                .filter(oc -> oc.getTitle().startsWith("spring"))
                .findFirst();

        // 값이 존재할 때만 처리
        optional.ifPresent(o -> System.out.println(o.getTitle()));
        // 값이 존재하면 가져오고, 없으면 다른 방식으로 처리
        OnlineClass onlineClass = optional.orElse(createNewClass());
        System.out.println(onlineClass.getTitle());
    }

    private static OnlineClass createNewClass() {
        return new OnlineClass(10, "New Class", false);
    }
}

```

### orElse(T)

`Optional`에 값이 있으면 가져오고 없는 경우에는 다른 타입(`T`)의 값을 반환한다.
> Cf. 인자로 `T`타입을 반환하는 메서드를 줄 경우 이 메서드는 반드시 한 번은 실해된다.
> 이를 방지하고 싶다면 `orElse(T)`가 아닌 `orElseGet(Supplier)`를 사용할 수 있다.
> 때문에 `Optional` 타입의 객체가 값을 가지지 않을 때 반환할 값으로 전달하려는 값이 상수면 `orElse()`를,
> 전달하려는 값이 동적인 값이면 `orElseGet()`을 사용하는 것이 좋아보인다.

### orElseGet(Supplier)

`Optional`에 값이 있으면 가져오고 없는 경우에 수행할 내용(`Supplier` 형태)가 들어간다.

### orElseThrow(), orElseThrow(Supplier)

`Optional`에 값이 있으면 가져오고 없는 경우에는 에러를 발생시킨다.

### filter(Predicate)

`Optional`에 있는 값을 걸러낸다.

### map(Function), flatMap(Function)

`Optional`에 있는 값을 변환한다.
Cf. flatMap : `Optional` 안에 있는 인스턴스가 `Optional`인 경우에 사용하면 편리하다. 아래 예시 코드의 1번과 2번은 같은 결과를 반환한다.

```java
public class App {
    public static void main(String[] args) {
        Optional<OnlineClass> optional = springClasses.stream()
                .filter(oc -> oc.getTitle().startsWith("spring"))
                .findFirst();

        // (1) 두번 꺼내는 경우
        Optional<Optional<Progress>> progress = optional.map(OnlineClass::getProgress);
        Optional<Progress> progress1 = progress.orElse(Optional.empty());
        System.out.println("map result - isPresent? " + progress1.isPresent());

        // (2) flatMap을 사용해 한번만 꺼내는 경우
        Optional<Progress> progress2 = optional.flatMap(OnlineClass::getProgress);
        System.out.println("flatMap result - isPresent? " + progress2.isPresent());
    }
}
```

Cf. `Stream`에서 제공하는 `flatMap()`과 `Optional`에서 제공하는 `flatMap()`과는 매우 다르다.
`Stream`에서 제공하는 `map()`은 1:1 맵핑이다. 이와 달리 `flatMap()`은 List에 담긴 List의 요소들을 꺼낼 때 주로 쓰인다. 즉, input은 하나지만 output이 다수일 때
사용된다.

# Date, Time

### Cf. 기존에 Java에서 시간과 관련해 제공하던 API들의 특징

Ex) `Date`, `GregorianCalendar`, `SimpleDateFormat` 등

- `Date`는 mutable한 특성(인스턴스 내부의 값 변경 가능)때문에 thread-safe하지 못하다는 단점이 있다. - 동시성 문제를 명확히 해결하지 않으면 중간에 값이 변경될 가능성이 있다.ㅠ
- 클래스명이 불명확하다. (`Date`가 time까지 다룬다는 점)
- 버그가 발생할 여지가 많다. (타입 안정성이 없고, 월이 0부터 시작하는 등)
    - `GregorianCalender`를 통해 `Calendar` 인스턴스를 만들 때 '월'은 0부터 시작하는 것을 감안하여 입력해야 한다. (타입안정성 X)
- 날짜 시간 처리가 복잡한 애플리케이션에서는 보통 'Joda Time'을 쓰곤 했다. (외부 라이브러리)

### Java 8에서 제공하는 Date-Time API(JSR-310)

기존에 많이 쓰이던 외부 라이브러리 'Joda Time' 기능들이 Java 표준 API로 많이 들어오게 되었다.

#### 철학

- clear : 명확해야 한다.
- fluent
- immutable : 기존 객체의 값에 변화를 주면 기존 객체의 값이 변화되는 것이 아니라 새로운 객체로 반환된다.
- extensible

#### 주요 API

- 기계용 시간(machine time)과 인류용 시간(human time)으로 나뉜다.
  Cf. 기계용 시간은 EPOCK(1970/01/01 00:00:00)부터 현재까지의 timestamp를 표현한다.
- timestamp로는 `Instant`을 사용 가능하다.
- 특정 날짜(`LocalDate`), 시간(`LocalTime`), 일시(`LocalDateTime`) 사용 가능
- 기간을 표현할 때는 `Duration`(시간 기반)과 `Period`(날짜 기반) 사용 가능
- `DateTimeFormatter`를 사용해서 일시를 특정한 형태의 문자열로 만들 수 있다.

## Instant

- now() : 현재 UTC(Universal Time Coordinated)을 반환한다.
  > UTC와 GMT(Greenwich Mean Time)은 동일하다.
- 특정 TimeZone의 타임스탬프를 얻고자 하면 ZonedDateTime 인스탄스를 반환해주는 `atZone(ZoneId z)`를 사용할 수 있다.

  Cf. 현재 시스템상의 TimeZone을 사용하고자 할 때에는 ZoneId.systemDefault()를 사용할 수 있다.
  > 현재 시스템상의 TimeZone을 사용한다고 할 때 한국의 경우 타임스탬프 뒤에 `+09:00[Asia/Seoul]`가 함께 출력된다.

```java
import java.time.ZoneId;

public class App {
    public static void main(String[] args) {
        Instant now = Instant.now();

        ZoneId zone = ZoneId.systemDefault(); // 현재 system상의 zone
        ZonedDateTime now = now.atZone(zone);
    }
}
```

## ZonedDateTime

`LocalDateTime`에 TimeZone의 개념이 결부된 개념이다.

- `now()`에 인자를 전달하지 않을 경우 `Instant`와 마찬가지로 UTC(+00:00) 기준 시간을 반환받는다.
- 특정 시간대의 현재 시간을 구할 때에는 `now()`의 인자로 `ZoneId` 혹은 `ZoneOffset`을 전달한다.
- 특정 시간을 구할 때에는 `of(ZoneId or ZoneOffset)`를 사용한다.
- 만약 `ZoneId.of(ZoneId or ZoneOffset)`의 인자는 '국가명/도시'뿐만 아니라 '시차'로도 전달 가능하다.

```java
public class App {
    public static void main(String[] args) {
        System.out.println("Current(UTC) " + ZonedDateTime.now());
        System.out.println("Current(Chicago) " + ZonedDateTime.now(ZoneId.of("America/Chicago")));
        System.out.println("Current(Paris) " + ZonedDateTime.now(ZoneId.of("Europe/Paris")));

        // ZoneId에 ZoneOffset값을 넣어도 값이 나오긴 한다. (+2 == UTC+2 == UTC+02 == UTC+02:00)
        System.out.println("Current(+2)" + ZonedDateTime.now(ZoneId.of("+2")));
        System.out.println("Current(UTC+2) " + ZonedDateTime.now(ZoneId.of("UTC+2")));
        System.out.println("Current(UTC+02) " + ZonedDateTime.now(ZoneId.of("UTC+02")));
        System.out.println("Current(UTC+02:00) " + ZonedDateTime.now(ZoneId.of("UTC+02:00")));

        System.out.println("Current(GMT+2) " + ZonedDateTime.now(ZoneId.of("GMT+2")));
        System.out.println("Current(GMT+02) " + ZonedDateTime.now(ZoneId.of("GMT+02")));
        System.out.println("Current(GMT+02:00) " + ZonedDateTime.now(ZoneId.of("GMT+02:00")));
    }
}
```

### ZoneId vs ZoneOffset

`ZoneId`는 TimeZone을, `ZoneOffset`은 시차(UTC와의 시차)를 의미한다. 예를 들어 서울의 타임존은 `Asia/Seoul`이지만, 시차는 `+09:00'이다.
> 영토의 위치, 크기 등에 따라 항상 하나의 타임존을 사용하지 않는 지역들이 많다.
> 예를 들어 올해(2022년 기준) 캐나다의 토론토는 01.01 ~ 03.13, 11.06 ~ 12.31 사이에는 EST Timezone을 따르지만 03.13 ~ 11.06 간에는 EDT Timezone을 따른다.
> 때문에 특정 지역의 타임존에 따르는 시간을 구할 때에는 `ZonedDateTime`의 인자로 ZoneId를 사용하는 것이 좋다.

## LocalDateTime

- 자동으로 시스템의 TimeZone이 적용된 시간이 반환되며, 타임존 정보를 가지지 않는다. (배포한 서버의 TimeZone을 따름에 유의해야 한다.)
- 특정 날짜의 `LocalDateTime`을 구하고자 할 때에는 `LocalDateTime.of(int year, Month month, int dayOfMonth, int hour, int minute)`를
  사용해야 한다. (인자로 초, 밀리초까지도 전달할 수 있다.)

## Cf. Instant에서 ZonedDateTime으로의 변환

- `Instant` 타입의 인스턴스를 `ZonedDateTime`으로 변환할 때는 `atZone(ZoneId or ZoneOffset)`을 사용한다.
- `ZonedDateTime` 타입의 인스턴스를 `Instant`로 변환할 때는 `toInstant()`를 사용한다.

```java
public class App {
    public static void main(String[] args) {
        Instant instant1 = Instant.now();
        ZonedDateTime zonedDateTime = instant1.atZone(ZoneId.of("Asia/Seoul"));
        LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();
        System.out.println(instant1);
        // 2022-10-22T16:13:09.757512100Z
        System.out.println(zonedDateTime);
        // 2022-10-23T01:13:09.757512100+09:00[Asia/Seoul]
        System.out.println(localDateTime);
        // 2022-10-23T01:13:09.757512100
    }
}
```

## ZonedDateTime -> LocalDateTime

타임존 정보가 빠지기만 할 뿐 타임존이 현재 시스템의 타임존으로 바뀌고 시간이 달라지지는 않는다는 점에 유의해야 한다.

```java
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class App {
    public static void main(String[] args) {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        LocalDateTime nowLocal = now.toLocalDateTime();
    }
}
```

## LocalDateTime -> ZonedDateTime 간의 변환

시간대를 적용시키고자 한다면 `atZone(ZoneId or ZoneOffset)`을 통해 해당 시간이 어떤 시간대(의 시간)인지가 적용된 `ZonedDateTime`을 반환받을 수 있다.

```java
public class App {
    public static void main(String[] args) {
        LocalDateTime nowLocalDateTime = LocalDateTime.now();
        ZonedDateTime nowInSeoul = nowLocalDateTime.atZone(ZoneId.of("Asia/Seoul")); // 현재 시간이 Asia/Seoul의 시간임을 정의해준다.

        System.out.println("now(no timezone) " + nowLocalDateTime);
        // now(no timezone) 2022-10-23T00:41:24.471570400
        System.out.println("now(with timezone) " + nowInSeoul);
        // now(with timezone) 2022-10-23T00:41:24.471570400+09:00[Asia/Seoul]
    }
}
``` 

## ZonedTimeDate의 시간대 변환

`ZonedDateTime` 인스턴스를 사용해 다른 시간대의 해당 시간을 얻을 때에는 `ZonedDateTime.ofInstant()`를 통해 해당 시간이 다른 시간대에서는 어떻게 변하는지 알 수 있다.

Cf. 이 때, 기존의 `ZonedDateTime` 인스턴스를 `Instant` 타입으로 변환해 인자로 전달해주어야 한다.

```java
public class App {
    public static void main(String[] args) {
        ZonedDateTime nowInSeoul = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        
        System.out.println("now(Asia/Seoul) " + nowInSeoul);
        // now(with timezone) 2022-10-23T00:41:24.471570400+09:00[Asia/Seoul]
        System.out.println("now(Paris) " + ZonedDateTime.ofInstant(nowInSeoul.toInstant(), ZoneId.of("Europe/Paris")));
        // now(Paris) 2022-10-22T17:41:24.471570400+02:00[Europe/Paris]
    }
}
``` 

## Period
인류용 시간(`LocalDate`)상의 기간을 나타낼 수 있다.
- `Period.between(LocalDate l1, LocalDate l2)`, `until(LocalDate compareDate)`
- `Period`를 그대로 출력할 시 `P[남은 달]M[남은 날]D` 형태로 출력된다.
- 만약 `l1`(= `compareDate`)가 기준 `LocalDate` 보다 빠를 경우 `[남은 달]`과 [`남은 날]`은 0보다 작거나 같은 수가 출력된다.
- `getYears()`, `getMonths()`, `getDays()` 뿐만 아니라 `get(TemporalUnit unit)`도 사용할 수도 있다.

```java
public class App {
  public static void main(String[] args) {
    LocalDate today = LocalDate.now();
    LocalDate nextYearBirthDay = LocalDate.of(2023, Month.FEBRUARY, 14);

    Period period = Period.between(today, nextYearBirthDay);
    System.out.println(period); // P3M22D
    System.out.println(period.getDays()); // 22

    Period until = today.until(nextYearBirthDay);
    System.out.println(until.get(ChronoUnit.DAYS)); // 22
    System.out.println(until.getDays()); // 22
  }
}
```

## Duration
기계용 시간(`Instant`)상의 기간을 나타낼 수 있다.
- `Duration.between(Instant i1, Instant i2)`
- `Duration`를 그대로 출력할 시 `P[남은 시간]T[남은 초]S` 형태로 출력된다.

```java
public class App {
  public static void main(String[] args) {
    Instant inst1 = Instant.now();
    Instant inst2 = inst1.plus(10, ChronoUnit.SECONDS);
    Duration duration = Duration.between(inst1, inst2);
  }
}
```

## Formatting
- `DateTimeFormatter.ofPattern("String pattern")`을 통해 `LocalDateTime`, `LocalDate`에 대한 커스텀 포매팅이 가능하다.
- `parse(LocalDateTime l, DateTimeFormatter formatter)`를 통해 포매팅된 문자열 형태의 날짜 데이터를 `LocalDateTime` 혹은 `LocalDate` 형태로 파싱 가능하다.
- 커스텀 패턴에 시분초 정보가 있는데 이 정보가 없는 `LocalDate`를 포매팅하려하면 `Unsupported field: ClockHourOfAmPm` 에러가 발생한다.
- 포매팅과 파싱 사이에는 포매터(`DateTimeFormatter`)의 형태가 일치해야 한다.
```java
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class App {
  public static void main(String[] args) {
    LocalDateTime now = LocalDateTime.now();
    
    // formatting
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    System.out.println(now.format(formatter));
    
    // parsing
    LocalDate result = LocalDate.parse("2023 - 02 - 14", formatter);
    System.out.println("parsed " + result);
  }
}
```

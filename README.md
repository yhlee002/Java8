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

### Stream Pipeline
0 ~ n개의 중개 오퍼레이션과 하나의 종료 오퍼레이션으로 구성된다.
스트림이 처리해야하는 데이터는 종료 오퍼레이션을 실행할 때에만 처리한다.

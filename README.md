# 1. Functional Interface & Lambda

## 람다식 특징
메서드에 인자로 전달하거나 그 자체로 리턴할 수 있다. 이를 다른 말로는 "함수를 first class object로 사용할 수 있다."고 한다.

## 람다식 조건
1. 순수 함수(Pure function)여야 한다. - 함수 밖의 값을 변경하지 못한다.
2. 고차 함수(Higher-Order function)여야 한다. - 함수가 함수를 매개변수로 받을 수 있고 함수를 리턴할 수도 있다.

## Java가 기본적으로 제공하는 함수형 인터페이스
- Function<T, R>
- BiFunction<T, U, R>
- Consumer<T>
- Supplier<T>
- Predicate<T>
- UnaryOperator<T>


## Method Reference
람다가 하는 일이 기존 메서드 또는 생성자를 호출하는 거라면, 메서드 레퍼런스를 사용해 매우 간결하게 표현 가능


# 2. 인터페이스의 Default 메서드와 Static 메서드


## Default Method
default 메서드들은 내용을 구현할 수 있다. 이는 이를 구현하지 못한 클래스들의 컴파일 에러를 방지한다.


## Static Method
특정 타입 관련 유틸리티나 헬퍼 메서드를 제공하고자 할 때 static 메서드를 작성할 수 있다.


## Java 8에 추가된 default 메서드
- `Iterable`의 `forEach()`, `spliterator()`
- `Collection`의 `stream()`, `parallelStream()`, 
- `Comparable`의 `reversed()`, `thenComparing()`, `static reverseOrder()`, `static naturalOrder()`등


# 3. Stream API

## 종류
### Intermediate operation(중개 오퍼레이터)
계속 이어진다. 즉, `Stream` 타입을 반환한다. Ex. `map()`, `limit()`, `sorted()` 등


### Terminal Operation(종료 오퍼레이터)
`Stream` 타입이 아닌 타입을 반환한다. Ex. `forEach()`, `allMatch()`, `collect()` 등


## Stream Pipeline
0 ~ n개의 중개 오퍼레이션과 하나의 종료 오퍼레이션으로 구성된다.
스트림이 처리해야하는 데이터는 종료 오퍼레이션을 실행할 때에만 처리한다.
파이프라인 내에서 작업을 수행하는 오퍼레이터들이 취급하는 데이터 타입은 이전 오퍼레이터에 의해 달라질 수 있다.


# 4. Optional
값을 가지고 있을 수도 없을 수도 있는 타입이다. Java 8부터는 Optional을 사용해 참조하는 코드에게 명시적으로 빈 값일 수 있음을 알려주고, 빈 값의 경우에 대한 처리를 강제한다.


# 5. Date, Time(Date-Time API(JSR-310))

## 특징
clear, fluent, immutable, extensible

## 주요 클래스
- Instant
- ZonedDateTime
- LocalDateTime
- Period
- Duration
- Formatting


# 6. CompletableFuture
Java Multi-Thread Programming을 지원

## 주요 클래스
### Executors
- 구현체: Executor, ExecutorService, ScheduledExecutorService)
- `Runnable` 또는 `Callable` 형태의 작업을 인자로 받는다.
- Cf. 작업을 실행하고 나면 다음 작업이 들어올 때까지 대기하기 때문에 프로세스가 죽지 않는다. 때문에 필요시 명시적으로 종료시켜 주어야 한다.

### Callable
스레드에서 작업을 실행하고 결과를 가져오고 싶다면 void만 가능한 `Runnable` 대신 사용 가능

### Future

### CompletableFuture
- 비동기 작업 수행: `runAsync()`, `supplyAsync()`
- 콜백 수행: `thenApply()`, `thenAccept()`, `thenRun()`, `thenApplyAsync()`, `thenAcceptAsync()`, `thenRunAsync()`
   - Cf. '-Async'가 붙지 않은 메서드와 붙은 메서드의 차이는 아래와 같다.
      1. 붙지 않은 메서드: 기본적으로 현재 실행되던 스레드에서 실행된다.
      2. 붙은 메서드: `ForkJoinPool`에서 실행된다.
- 작업 조합: `thenCompose()`, `thenCombine()`, `allOf()`, `anyOf()`
- 예외 처리: `exceptionally()`, `handle()`

# 7. Annotation 변화
Java 8 부터 어노테이션을 1)타입 선언부에도 사용 가능하게, 2)중복해서 사용할 수 있게 바뀌었다.

## 타입 선언부
- 제네릭 타입
- 변수 타입
- 매개변수 타입
- 예외 타입

## 타입에 사용하기 위한 준비 사항
`@Target` 을 통해 해당 클래스가 어노테이션으로써 사용될 수 있는 곳을 정의할 수 있다.

Java 8에 TYPE_PARAMETER와 TYPE_USE 두 가지 타입이 추가되었다.

### 1) ElementType.TYPE_PARAMETER 
해당 클래스를Type Parameter에만 사용 가능해진다.

### 2) ElementType.TYPE_USE
해당 클래스를Type에 모두 사용 가능해진다.

## 중복 사용 가능한 어노테이션 생성
1. `@Repetable`어노테이션을 통해 컨테이너로 사용될 클래스 지정

    Cf. 컨테이너가 되는 클래스는 Retention 전략 범위와 Target 범위가 요소 어노테이션 클래스보다 넓어야 한다. (어노테이션 생성 시 `@Retention`, `@Target` 필요)

2. 컨테이너 클래스는 기본적으로 반드시 어노테이션 목록을 가져야 한다. 이를 value라는 이름으로 정의해야한다. (`@Repeatable` 어노테이션 내부에 value라는 이름의 멤버 변수가 존재함을 참고)

## 어노테이션 정보 조회

### 특정 클래스에 등록된 어노테이션 목록 조회
`<A extends Annotation>A[] getAnnotationsByType(Class<A>annotationClass)`

### 특정 타입의 클래스의 어노테이션 조회
`<A extends Annotation>A getAnnotation(Class<A>annotationClass)`

Cf. Repeatable한 어노테이션을 컨테이너로 감싸서 가져오기 위해서는 해당 메서드를 사용하되 인자로 컨테이너 클래스를 전달할 수 있다.

```java
@Item("가위")
@Item("색종이")
public class App {
    public void main(String[] args) {
        Item[] items = App_AnnotationDuplicateUse.class.getAnnotationsByType(Item.class);
        Arrays.stream(items).forEach(c -> {
            System.out.println(c.value());
        });

        ItemContainer container = App_AnnotationDuplicateUse.class.getAnnotation(ItemContainer.class);
        Arrays.stream(container.value()).forEach(c -> {
            System.out.println(c.value());
        });
    }
}
```
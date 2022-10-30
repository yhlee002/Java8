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
- Executors(구현체: Executor, ExecutorService, ScheduledExecutorService)
- Callable
- Future
- CompletableFuture
   - 비동기 작업 수행
   - 콜백 수행
   - 작업 조합
   - 예외 처리

# ThreadLocal

자바 ThreadLocal 클래스를 사용하면 동일한 스레드에서 읽고 쓰는것이 가능하다. 따라서 두개의 스레드가 동일한 코드를 실행 하고 코드에 동일한 ThreadLocal 참가 있어도 두 스레드는 서로 ThreadLocal변수를 볼  수 없다.따라서 자바 ThreadLocal 클래스는 간단하게 스레드 세이프를 구현할 수 있다.

### Creating a ThreadLocal

ThreadLocal은 다음과 같이 생성할 수 있다.

```java
private ThreadLocal threadLocal = new ThreadLocal();
```

각 스레드당 한번만 수행 한다. 여러 스레드에서 get 과 set을 통해 값을 변경 또는 가져올 수 있으며 변경된 값은 무조건 그 스레드에서만 사용 가능하다.

### Set ThreadLocal Value

set을 통해서 값을 설정 할 수 있다.

```java
threadLocal.set("A thread local value");
```

### Get ThreadLocal Value

get을 통해서 설정된 스레드의 값을 가져올 수 있다.

```java
String threadLocalValue = (String) threadLocal.get();
```

### Remove ThreadLocal Value

remove를 통해서 ThreaLocal에 설정된 값을 제거할 수 있다.

```java
threadLocal.remove();
```

### Generic ThreadLocal

Generic을 사용하면 형변환 없이 사용 가능하다.

```java
private ThreadLocal<String> myThreadLocal = new ThreadLocal<String>();
```

```java
myThreadLocal.set("Hello ThreadLocal");

String threadLocalValue = myThreadLocal.get();
```

### Initial ThreadLocal Value

set값이 초기화 되기 전에 get이 처음 호출될 때 사용되는  ThreadLocal 초기값을 설정 할 수 있다.

2가지 옵션이 있는데 아래와 같다.

1. ThreadLocal initialValue를 오버라이드 해서 구현
2. ThreadLocal supplier interface 구현

#### Override initialValue\(\)

ThreadLocal에 initialValue를 재정의 해서 구현할 수 있으며 가장 쉬운 방법은 익명 서브 클래스를 사용해서 구현하는 것이다.

```java
private ThreadLocal myThreadLocal = new ThreadLocal<String>() {
    @Override protected String initialValue() {
        return String.valueOf(System.currentTimeMillis());
    }
};   
```

#### Provide a Supplier Implementation

두 번째 방법으로는 Supplier를 파라미터로 받는 withIninital을 사용해서 설저 할 수 있다. 

```java
ThreadLocal<String> threadLocal = ThreadLocal.withInitial(new Supplier<String>() {
    @Override
    public String get() {
        return String.valueOf(System.currentTimeMillis());
    }
});
```

람다를 사용해서 구현할 수도 있다.

```java
ThreadLocal threadLocal = ThreadLocal.withInitial(
        () -> { return String.valueOf(System.currentTimeMillis()); } );
```

```java
ThreadLocal threadLocal3 = ThreadLocal.withInitial(
        () -> String.valueOf(System.currentTimeMillis()) );
```

  
참고 URL : [http://tutorials.jenkov.com/java-concurrency/threadlocal.html](http://tutorials.jenkov.com/java-concurrency/threadlocal.html)




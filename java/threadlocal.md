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




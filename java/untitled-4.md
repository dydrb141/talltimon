---
description: 생성자 레퍼런스를 사용하면서 몰랐던 사실.
---

# Untitled



```java
ILLEGAL_ARGUMENT(IllegalArgumentException::new),
NULL_POINT_EXCEPTION(e -> NullPointException());

Function<String, RuntimeException> supplier;

RuntimeExceptions(Function<String, RuntimeException> supplier) {
	this.supplier = supplier;
}
```

위 코드에서 NullPointExcetion의 람다와 IllegalArgumentException의 생성자레퍼런스는 동일한줄 알았지만 다른점이 있다.

다음 코드에서 Function의 String을 Integer로 바꾸면 생성자 레퍼런스로 사용한 표현식을 컴파일 에러가 난다.

왜그럴까? 

```javascript
ILLEGAL_ARGUMENT(IllegalArgumentException::new),
NULL_POINT_EXCEPTION(e -> NullPointException());

Function<Integer, RuntimeException> supplier;

RuntimeExceptions(Function<Integer, RuntimeException> supplier) {
	this.supplier = supplier;
}
```

사실 NullPoiontException에서 사용한 람다식은  e 파라메터를 받아서 아무것도 안한다는 소리와 마찮가지인 람다식이다. 람다를 잘 모르고 사용하다 보니 동일한줄 알았다.

IllegalArgumentExcetion\(String message\) 시그니처는 Function 인터페이스 시그니처와 동일하기 때문에 생성자 레퍼런스는 new만 사용해도 추론해서 컴파일 에러가 발생하지만 e -&gt; NullPointExceptionO은 그냥 기본 생성자를 사용한다는 것이기 때문에 컴파일 에러가 발생하지 않는다. 따라서 e -&gt; NullPointException\(e\)를 해줘야 동일하게 컴파일 에러가 발생한다.

추가로 반성하는 의미에서 생성자 참조에 대해서 정리한다.

```java
() -> new Apple()과 동일 (Apple())
Supplier<Apple> c1 = Apple::new;
Apple a1 = c1.get(); //Supplier의 get 메서드를 호출해서 새로운 Apple을 생성
```

```java
Function<Integer, Apple> c1 = a -> new Apple(a)와 동일 (Apple(Integer a));
Function<Integer, Apple> c2 = Apple::new; <- Apple(Integer a)의 생성자 참조
Apple a2 = c2.apply(110); <- Function 의 Apply메서드 무게를 인수로 호출해서 새로운 Apple객체 만듬
```

```java
BiFunction<String, Integer, Apple> c1 =  (a ,b) -> new Apple(a, b); 와 동일  Apple(String a, Integer b) ;
BiFunction<String, Integer, Apple> c2 = Apple::new;
Apple a3 = c2.apply("test", 10);
```




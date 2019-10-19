# Java 기본

### 자바의 특징

1. 이식성이 높다
2. 객체 지향 언어
3. 함수적 스타일 코딩 지원
4. 메모리 자동 관리
5. 다양항 애플리케이션 개발
6. 멀티 스레드 쉽게 구현
7. 동적로딩
8. 오픈 소스가 많음

### 변수란?

하나의 값을 저장할 수 있는 메모리 공간

기본 데이터 타입

정수 - byte\(1byte\), char\(2byte\), short\(2byte\) ,int\(4byte\), long\(8byte\)

실수 - float\(4byte\), double\(8byte\)

논리 -  boolean\(1byte\)

참조 타입

배열, 열거, 클래스, 인터페이스

### 객체란? 

* 물리적으로 존재하거나 추상적으로 생각할 수 있는것 중 자신의 속성을 가지고 있고 다른것과 식별 가능한 것

### 객체 지향 프로그래밍 특징

**캡슐화**

* 객체의 필드, 메소드를 하나로 묶고 실제 구현 내용을 감추는것
* 외부 객체는 객체 내부의 구조를 알지 못하며 객체가 노출해서 제공하는 필드와 메소드만 이용
* 캡슐화 이유  : 외부의 잘못된 사용으로 인해 객체 손상을 막

**상속**

* 상위 객체는 자기가 가지고 있는 필드와 메소드를 하위 객체에게 물려줌
* 상속 이유 : 

           1.객체를 재사용해서 하위 객체를 빨리 설계 

           2.반복된 코드의 양을 줄여줌

  자바는 다중 상속을 지원하지 않는다 왜?

```text
public class GodFather {
   void myFather() {
      System.out.println("GrandFather");
   }
}

class FatherA extends GodFather {
   @Override
   void myFather() {
      System.out.println("FatherA");
   }
}

class FatherB extends GodFather {
   @Override
   void myFather() {
      System.out.println("FatherB");
   }
}

class Son extends FatherA, FatherB { //컴파일 에러
   @Override
   void myFather() {
      super.myFahter(); //어떤 아버지를 출력할지 모른
   }
}
```

 위 구조에서는 어떤 파더를 출력할지 난감하다 그래서 자바 에서는 다중 상속을 막아놧다

그러면 인터페이스는 다중 상속이 되는데 그건 문제가 없나?

 - 인터페이스는 오버라이드 해서 재구현 하면 충돌나지 않게 적용 가능함

```text

interface A {
   default void hello() {
      System.out.println("helloA");
   }
}

interface B {
   default void hello() {
      System.out.println("helloB");
   }
}

class C implements A, B {
   
   public static void main(String[] args) {
      C c = new C();
      c.hello();
   }

}
```

위 소스는 A,B를 다중 상속 받으면 컴파일 오류가 발생하면서 재구현 하라고 한다.



```text
interface A {
   default void hello() {
      System.out.println("helloA");
   }
}

interface B extends A {
   default void hello() {
      System.out.println("helloB");
   }
}

class C implements A, B {
   public static void main(String[] args) {
      C c = new C();
      c.hello();
   }
}
```

 위 소스는 컴파일 오류가 발생하지 않지만 어떤게 출력될까? 바로 helloB가 출력된다.

이기는 규칙

1. 클래스나 슈퍼클래스에서 정의한 메소드가 디폴트 메소드보다 우선권 가짐
2. 1번을 제외하면 서브 인터페이스가 이긴다. B가 A를 상속 받으므로 B가 이
3. 디폴트 메소드의 우선순위가 결정되지 않았다면 여러 인터페이스를 상속받는 클래스가 명시적으로 디폴트 메소드를 오버라이드 하라고 경고한다.

다형성

 - 같은 타입이지만 샐행결과가 다양한 객체를 이용할 수 잇는 성질

### 인스턴스 멤버와 this

인스턴스 멤버 : 객체\(인스턴스\)를 생성한 후 사용할 수 있는 필드와 메소드, 이들을 각각 인스턴스 메소드

인스턴스 필드라 부름

### 정적 멤버와 static

정적 멤버는 클래스에 고정된 멤버

정적 필드, 정적 메소드라 부름 클래스 멤버라고도 부름

클래스 로더가 클래스를 로딩해서 메소드 메모리 영역에 적재할 때 클래스별 관리

클래스 로딩이 끝나면 바로 사용 가능














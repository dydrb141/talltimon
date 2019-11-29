# 웹 애플리케이션 영속성 관리

## 트랜잭션 범위의 영속성 컨텍스트

### 스프링 컨테이너 기본 전략

스프링 컨테이너는 트랜잭션 범위의 영속성 컨텍스트 전략을 기본으로 사용

트랜잭션의 범위와 영속성 컨텍스트의 생존 범위가 같다.

트랜잭션을 시작할때 영속성 컨텍스트를 생성하고 끝날때 영속성 컨텍스트 종료

같은 트랜잭션 안에서는 항상 같은 영속성 컨텍스트에 접근

 **스프링 트랜잭션에서 영속성 컨텍스트 동작 순서**  

1. 비즈니스 로직에 @Transactional 선언 
2. 스프링 AOP 동작 
3. AOP는 대상 메소드를 호출하기전 트랜잭션 시작 
4. 트랜잭션 커밋하면 JPA는 먼저 영속성 컨텍스트를 플러시해 변경된 내용을 db 반영후 db 커밋 
5. 트랜잭션 종료

* 트랜잭션이 같다면 트랜잭션 매니저가 달라고 같은 영속성 컨텍스트 사용 
* 트랜잭신이 다르면 영속성 컨텍스트가 다름, 다른 컨텍스트를 사용하므로 멀티 스레드 상황에 안전
* 보통 비즈니스 로직에서 트랜잭션이 종료 되기 때문에 Controller나 View 계층에서는 준영속 상태이다.

#### 준영속 상태와 지연 로딩

스프링에서는 트랜잭션 범위에서만 영속 상태가 될 수 있기 때문에 Controller나 View에서는 준영속 상태가 된다. 준영속 상태에서 가장 문제점은 지연 로딩이 되지 않는다는 점이다.

#### 준영속 상태의 지연로딩 해결 방법

1. 뷰가 필요한 엔티티를 미리 로딩
   * 글로벌 페치 전략 수정
     * 간단하게 @ManyToOne\(fecth = FetchType.EAGER\)를 사용해서 미리 로딩
     * 글로벌 페치 전략에 즉시 로딩 단점
       * 사용하지 않는 엔티티를 로딩
       * N + 1 문제가 발생
   * JPQL 페치 조인 \*fecth join을 활용해 미리 로딩
     * 단점 - 화면에 맞춰서 레파지토리 메소드가 증가 할 수 있다.
   * 강제로 초기화
   * Order order = orderRepository.findOrder\(id\);
   * order.getMember\(\).getName\(\) //프록시 객체를 강제로 초기화
   * 강제로 초기화 하면 준영속 상태에서 사용할 수 있다ㅓ.
2. OSIV를 사용해 엔티티를 항상 영속한 상태로 유지

#### N + 1 문제란?

Order.member 를 즉시로딩 설정 했다고 가정

{% code title="Order 즉시 로딩 그래프 탐색" %}
```sql
Order order = em.find(Order.class, 1L);
```
{% endcode %}

{% code title="실행 결과" %}
```sql
select o.*, m.*
from Oder o 
left outer join Member m o.MEMBER_ID = m.MEMBER_ID
where o.id=1
```
{% endcode %}

위 처럼 엔티티 그래프 탐색에는 문제가 없다. 하지만 JPQL을 사용할 때는 문제가 발생한다.

{% code title="Order JPQL" %}
```sql
List<Order> orders = em.createQuery("select o from Order o", Order.class).getResultList();
```
{% endcode %}

{% code title="Order JPQL 실행결과" %}
```sql
select * from Order // JPQL로 실행된 SQL 
select * from Member where id=? // EAGER로 실행된 SQL
select * from Member where id=? // EAGER로 실행된 SQL
select * from Member where id=? // EAGER로 실행된 SQL
select * from Member where id=? // EAGER로 실행된 SQL
```
{% endcode %}

JPQL을 사용할 때는 글로벌 패치 전략을 참고하지 않고 JPQL 자체만 사용

코드 동작 순서

1.  select o from Order o JPQL을 분석해서 select  _from Order SQL 생성_
2. _db의 결과를 받아 order 엔티티 인스턴스들 생성_ 
3. _Order.member의 글로벌 페치 전략이 즉시 로딩이기 때문에 연관된 member도 로딩해야함_ 
4. _member를 연속성 컨텍스트에서 찾음_
5. _만약 영속성 컨텍스트에 없으면 SELECT_  FROM MEMBER WHERE id = ? sql을 조회한 order엔티티 수만큼 실행

### OSIV Open Session in View

영속성 컨텍스트를 뷰까지 열어둠

View에서도 지연 로딩이 가능하도록 하는 것

#### 요청당 트랜잭션 방식

클라이언트의 요청이 들어오자마자 서블릿 필터나 스프링 인터셉터에서 트랜잭션 시작하고 요청이 끝날때 트랜잭션 끝냄

문제점

컨트롤러나 뷰 같은 프리젠테이션 계층에서 엔티티를 변경할 수 있다. 

변경이 되면 db까지 영향을 줌 \(왜? 영속성 컨텍스트가 유지되기 때문\)

프레젠테이션 계층에서 못막게 하는 방법

* 엔티티를 읽기 전용 인터페이스로 제공 - 읽기 전용 인터페이스를 따로 만듬
* 엔티티 레핑 - 읽기 전용 메소드만 가지고 있는 엔티티를 감싼 객체를 만듬
* DTO만 반환 - 데이터만 전달하는 DTO 객체츨 생성해서 반환

#### 스프링 OSIV : 비즈니스 계층 트랜잭션

동작 방식 

1. 클라이언트 요청이오면 서블릿 필터나 스프링 인터셉터에 영속성 컨텍스트를 생성, 단 트랜잭션은 시작하지 않음
2. 서비스 계층에서 @Transactional로 트랜잭션을 시작할 때 1번에서 미리 생성해둔 영속성 컨텍스트를 찾아와 트랜잭션 시작
3. 서비스 계층이 끝나면 트랜잭션을 커밋하고 영속성 컨텍스트 플러시. 이때 영속성 컨텍스트는 종료하지 않음
4. 컨트롤러와 뷰까지 영속성 컨텍스트가 유지되므로 조회한 엔티티는 영속 상태를 유지
5. 서블릿 필터나 스프링 인터셉터로 요청이 돌아오면 영속성 컨텍스트를 종료, 이때는 플러시 호출 안함.


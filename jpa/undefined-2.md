# 객체지향 쿼리 언어

## 객체지향 쿼리

### JPQL

엔티티 객체를 대상으로 하는 객체 지향 쿼리 JPQL을 사용하면 JPA는 JPQL을 분석하여 적절한 SQL을 만들어 디비 조회

TypeQuery - 반환할 타입을 명확하게 지정 Query - 반환할 타입을 명확하게 지정할수 없을 때

파라미터 바인딩 - 이름기준, 위치 기준 위치 기준보다 이름 기준이 좀더 명확하게 사용 가능

#### JPQL 조인

 **내부 조인**  m.team은 연관 필임 SELECT m, t 같이 두 개의 엔티티를 조회한다면 TypeQuery를 사용 할 수 없다 List 형식으로 받아야 됨

```java
String teamName = "팀A";
String query = "SELECT m FROM Member m INNER JOIN m.team t WHERE t.namme = :teamName";

List<Member> members = em.createQuery(query, Member.class)
                        .setParameter("teamName", teamName)
                        .getResultList();
```

 **외부 조인**  OUTER는 생략 가능하고 보통 LEFT JOIN으로 사용

```java
SELECT m FROM Member m LEFT JOIN m.team t
```

 **컬렉션 조인**  일대다 관계나 다대다 관계처럼 컬렉션을 사용하는 곳에 조인하는 것

#### 페치 조인

SQL조인 종류는 아니고 JPQL에서 성능 최적화를 위해 제공해주는 기능 연관된 엔티티나 컬렉션을 한번에 같이 조회하는 기능

```java
SELECT m FROM Member m JOIN FETCH m.team
```

**엔티티 페치 조인**

회원 엔티티를 조회하면서 연관된 팀 엔티티도 함께 조회 

m.team 다음에 별칭이 없다 페치 조인은 별칭을 사용할 수 없다. 

회원과 팀을 지연로딩으로 설정했어도 페치 조인은 프록시가 아닌 실제 엔티티이다. 

지연로딩이 일어나지 않음

```java
String jpql = "SELECT m FROM Member m JOIN FETCH m.team;
List<Member> mebers = em.createQuery(jpql, Member.class).getResultList();

for (Member member : members) {
    //페치 조인으로 회원과 팀을 함께 조회해서 지연 로딩 발생 안함.
    System.out.println(member.userName() + member.getTeam().name());
}
```

**컬렉션 페치 조인**

일대다 컬렉션 페치 조인 select t로 팀만 선택했는데 실행된 결과를 보면 팀과 연관된 회원도 함께 조회된다.

```java
SELECT t FROM Team t JOIN FETCH t.members WHERE t.name ='팀A'
```

**페치 조인의 특징 한계**

페치 조인은 SQL한 번으로 연관된 엔티티들을 함께 조회할 수 있어서 SQL 호출 횟수를 줄여 성능 최적화 

페치 조인은 글로벌 전략\(LAZY, EAGER\)보다 우선한다. 글로벌 전략을 즉시 로딩으로 설정하면 애플리케이션 전체에서 항상 즉시 로딩이 일어남 

글로벌 로딩 전략은 될수 있으면 지연로딩으로 하고 최적화가 필요하면 패치 조인 적용하는게 효과적

#### 페치조인의 한계 

1. 별칭 불가능

* SELECT, WHERE 절 서브 쿼리에 페치 조인 대상 사용 불가
* 하이버네이트에서는 조인 별칭 지원, 별칭을 잘못 사용하면 데이터 무결성이 깨질수 있음

2. 둘 이상의 컬렉션을 페치 불가

* 구현체에 따라 되기도 하는데 카테시안 곱이 만들어 지므로 주의

3. 페이징 API 사용 부락

* 컬렉션\(일대다\)이 아닌 단일 값 연관필드\(일대일, 다대일은\) 사용불가
* 하이버네이트에서는 경고 로그를 남기면서 메모리에서 페이징. 데이터 많으면 위험

### QueryDSL

SQL과 JQPL을 코드로 작성할 수 있도록 도와주는 빌더 API

SQL, JPQL 문제점 타입 체크가 불가능 함 컴파일 시점에 작동여부 확인 불가

장점 문자가 아닌 코드로 작성 컴파일 시점에 문법 오류 발견 코드 자동완성\(IDE\) 단순하고 쉬움 동적 쿼리


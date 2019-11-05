# 연관관계 매핑
객체의 연관관계를 정의

### 단방향 연관관계
회원은 하나의 팀에만 소속가능
회원과 팀은 다대일 관계

``` java
@Entity
public class Member {
    @Id
    @Column(name ="MEMBER_ID")
    private String id;
    
    @ManyToOne
    @JoinCoumn(name="TEAM_ID")
    private Team team;
}
```

```
@Entity
public class Team {
    @Id
    @Column(name ="TEAM_ID")
    private String id;
    
    private String name;
}
```
@ManyToOne :  다대일 (N:1) 매핑


### 양방향 연관관계
회원 <-> 팀


```
@Entity
public class Team {
    @Id
    @Column(name ="TEAM_ID")
    private String id;
    
    private String name;
    
    @OneToMany(mappedBy = "team")
    private List<Member> member = new ArrayList<Member>();
}
```

mappedBy는 왜 필요 할까?
양방향 연관관계라는건 없음
서로 다른 단방향 연관관계를 2개를 묶어서 양방향처럼 보임
객체 참조는 둘인데 외래키는 하나 (회원 -> 팀, 팀 -> 회원)
둘중 어떤 관계를 사용해서 외래키를 관리해야 하는 차이 때문에 jpa에서는 둘 중 하나를 정해서 테이블의 외래키를 관리함
이것을 연관관계 주인이라함

### 연관관계의 주인
연관관계 주인은 데이터베이스 연관관계와 매핑되고 외래키를 관리(등록, 수정, 삭제)가능
주인이 아니면 읽기만 가능

주인이 아니면 mappedBy 속성을 사용해서 주인을 지정해야함.

### 양방향 연관관계 주의점
주인이 아닌곳에 값을 입력하면 데이터가 누락될 수 있음
객체 관점에서 양쪽 방향 모두 값을 입력해주는게 가장 안전함.,

* 다대일 : 단방향, 양방향
* 일대다 : 단방향, 양방향
* 일대일은 둘중 어느곳이나 외래키를 가질수 있기 때문에 주 테이블에 둘걵지 대상 테이블에 둘건지 결정 해야됨.
* 일대일 : 주 테이블 단방향, 양방향
* 일대일 : 대상테이블 단방향, 양방향
* 다대다 : 단방향, 양방향